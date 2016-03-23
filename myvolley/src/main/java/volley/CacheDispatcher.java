package volley;

import android.os.Process;
import java.util.concurrent.BlockingQueue;
/**
 * Provides a thread for performing cache triage on a queue of requests.
 *
 * Requests added to the specified cache queue are resolved from cache.
 * Any deliverable response is posted back to the caller via a
 * {@link ResponseDelivery}.  Cache misses and responses that require
 * refresh are enqueued on the specified network queue for processing
 * by a {@link NetworkDispatcher}.
 * 一个线程，用于调度处理走缓存的请求。启动后会不断从缓存请求队列中取请求处理，
 * 队列为空则等待，请求处理结束则将结果传递给ResponseDelivery去执行后续处理。当结果未缓存过、
 * 缓存失效或缓存需要刷新的情况下，该请求都需要重新进入NetworkDispatcher去调度处理。
 */
public class CacheDispatcher extends Thread {
    private static final boolean DEBUG = VolleyLog.DEBUG;
    
    /** The queue of requests coming in for triage. */
    private final BlockingQueue<Request<?>> mCacheQueue;   // 缓存请求队列
    
    /** The queue of requests going out to the network. */
    private final BlockingQueue<Request<?>> mNetworkQueue;    // 网络请求队列
    
    /** The cache to read from. */
    private final Cache mCache;     // 缓存类，代表了一个可以获取请求结果，存储请求结果的缓存
     
    /** For posting responses. */
    private final ResponseDelivery mDelivery;  // 分发结果
    
    /** Used for telling us to die. */
    private volatile boolean mQuit = false;
    
    /**
     * Creates a new cache triage dispatcher thread.  You must call {@link #start()}
     * in order to begin processing.
     *
     * @param cacheQueue Queue of incoming requests for triage
     * @param networkQueue Queue to post requests that require network to
     * @param cache Cache interface to use for resolution
     * @param delivery Delivery interface to use for posting responses
     */
    public CacheDispatcher(BlockingQueue<Request<?>> cacheQueue, BlockingQueue<Request<?>> networkQueue,  Cache cache, ResponseDelivery delivery) {
        mCacheQueue = cacheQueue;
        mNetworkQueue = networkQueue;
        mCache = cache;
        mDelivery = delivery;
    }
    /**
     * Forces this dispatcher to quit immediately.  If any requests are still in
     * the queue, they are not guaranteed to be processed.
     */
    public void quit() {
        mQuit = true;
        interrupt();
    }
    
    @Override
    public void run() {
        if (DEBUG) VolleyLog.v("start new dispatcher");
        
        // 设置线程的优先级, 将线程设为后台线程
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        
        // Make a blocking call to initialize the cache.
        //  Cache 只是一个接口，定义自己的Cache时，需要在initialize() 方法里实现一些初始化工作
        mCache.initialize();   
        
        while (true) {
            try {
            	
                // Get a request from the cache triage queue, blocking until
                // at least one is available.
                final Request<?> request = mCacheQueue.take();   // 从缓存队列里取出一个request,并删除队列里取出的request, 如果为空，则阻塞等待
                request.addMarker("cache-queue-take");
                
                // If the request has been canceled, don't bother dispatching it.
                if (request.isCanceled()) {        // 请求是否被取消
                    request.finish("cache-discard-canceled");
                    continue;
                }
                
                // 根据URL 取缓存结果
                // Attempt to retrieve this item from cache.
                Cache.Entry entry = mCache.get(request.getCacheKey());
                if (entry == null) {
                    request.addMarker("cache-miss");
                    // Cache miss; send off to the network dispatcher.
                    mNetworkQueue.put(request);
                    continue;
                }
                
                // 取到的缓存结果是否已过期
                // If it is completely expired（过期）, just send it to the network.
                if (entry.isExpired()) {
                    request.addMarker("cache-hit-expired");
                    request.setCacheEntry(entry);
                    mNetworkQueue.put(request);
                    continue;
                }
                
                // We have a cache hit; parse its data for delivery back to the request.
                request.addMarker("cache-hit");
                Response<?> response = request.parseNetworkResponse( new NetworkResponse(entry.data, entry.responseHeaders));
                request.addMarker("cache-hit-parsed");
                // 不需要刷新
                if (!entry.refreshNeeded()) {
                    // Completely unexpired cache hit. Just deliver the response.
                    mDelivery.postResponse(request, response);
                } else {
                	// 需要刷新
                    // Soft-expired cache hit. We can deliver the cached response,
                    // but we need to also send the request to the network for
                    // refreshing.
                    request.addMarker("cache-hit-refresh-needed");
                    request.setCacheEntry(entry);
                    // Mark the response as intermediate.
                    response.intermediate = true;
                    // Post the intermediate response back to the user and have
                    // the delivery then forward the request along to the network.
                    mDelivery.postResponse(request, response, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mNetworkQueue.put(request);
                            } catch (InterruptedException e) {
                                // Not much we can do about this.
                            }
                        }
                    });
                }
                
            } catch (InterruptedException e) {
                // We may have been interrupted because it was time to quit.
                if (mQuit) {
                    return;
                }
                continue;
            }
        }
    }
}
