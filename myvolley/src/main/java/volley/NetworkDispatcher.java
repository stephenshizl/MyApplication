package volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;

import java.util.concurrent.BlockingQueue;

/**
 * Provides a thread for performing network dispatch from a queue of requests.
 *
 * Requests added to the specified queue are processed from the network via a
 * specified {@link Network} interface. Responses are committed to cache, if
 * eligible, using a specified {@link Cache} interface. Valid responses and
 * errors are posted back to the caller via a {@link ResponseDelivery}.
 * 
 * 一个线程，用于调度处理走网络的请求。启动后会不断从网络请求队列中取请求处理，
 * 队列为空则等待，请求处理结束则将结果传递给ResponseDelivery去执行后续处理，并判断结果是否要进行缓存。
 */
public class NetworkDispatcher extends Thread {
    /** The queue of requests to service. */
    private final BlockingQueue<Request<?>> mQueue;   // 网络请求队列
    
    /** The network interface for processing requests. */
    private final Network mNetwork;     //网络类，代表了一个可以执行请求的网络
    
    /** The cache to write to. */
    private final Cache mCache;   // 缓存类，代表了一个可以获取请求结果，存储请求结果的缓存
    
    /** For posting responses and errors. */
    private final ResponseDelivery mDelivery;  // 请求结果传递类，可以传递请求的结果或者错误到调用者
    
    /** Used for telling us to die. */
    private volatile boolean mQuit = false; 
    
    /**
     * Creates a new network dispatcher thread.  You must call {@link #start()}
     * in order to begin processing.
     *
     * @param queue Queue of incoming requests for triage
     * @param network Network interface to use for performing requests
     * @param cache Cache interface to use for writing responses to cache
     * @param delivery Delivery interface to use for posting responses
     */
    public NetworkDispatcher(BlockingQueue<Request<?>> queue, Network network, Cache cache, ResponseDelivery delivery) {
        mQueue = queue;
        mNetwork = network;
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
    
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void addTrafficStatsTag(Request<?> request) {
        // Tag the request (if API >= 14)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        	// Set active tag to use when accounting Socket traffic originating from the current thread. 
        	//  Only one active tag per thread is supported
        	// TrafficStats 流量统计类
            TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
        }
    }
    
    /**
     * 1、设置线程的优先级
     * 2、从网络队列中取出一个网络请求request
     * 3、判断取出的request 是否已经取消， 否则结束请求
     * 4、通过mNetwork.performRequest() 获取 networkResponse
     * 5、判断 networkResponse 是否 304 响应 和 已经分发
     * 6、将 parseNetworkResponse 解析成 Response
     * 7、判断请求是否可以缓存，并且请求实体是否为空，如果条件成立，测放入缓存中
     * 8、标记request ,并分发 response.
     * 
     */
    @Override
    public void run() {
    	// 设置线程的优先级
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        
        while (true) {
            long startTimeMs = SystemClock.elapsedRealtime();
            Request<?> request;
            try {
                // Take a request from the queue.
                request = mQueue.take();
            } catch (InterruptedException e) {
                // We may have been interrupted because it was time to quit.
                if (mQuit) {
                    return;
                }
                continue;
            }
            
            try {
                request.addMarker("network-queue-take");  // 仅仅为了log, 无其他用处
                // If the request was cancelled already, do not perform the
                // network request.
                if (request.isCanceled()) {
                    request.finish("network-discard-cancelled");
                    continue;
                }
                
                // ???
                addTrafficStatsTag(request);
                
                // Perform the network request.
                NetworkResponse networkResponse = mNetwork.performRequest(request);
                request.addMarker("network-http-complete");
                
                // If the server returned 304 AND we delivered a response already,
                // we're done -- don't deliver a second identical response.
                if (networkResponse.notModified && request.hasHadResponseDelivered()) {
                    request.finish("not-modified");
                    continue;
                }
                
                // Parse the response here on the worker thread.
                Response<?> response = request.parseNetworkResponse(networkResponse);
                request.addMarker("network-parse-complete");
                
                // Write to cache if applicable.
                // TODO: Only update cache metadata instead of entire record for 304s.
                if (request.shouldCache() && response.cacheEntry != null) {
                    mCache.put(request.getCacheKey(), response.cacheEntry);
                    request.addMarker("network-cache-written");
                }
                
                // Post the response back.
                request.markDelivered();  // 标记request 已经分发
                mDelivery.postResponse(request, response);  // 分发拿到的response
                
            } catch (VolleyError volleyError) {
                volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime() - startTimeMs);
                parseAndDeliverNetworkError(request, volleyError);
                
            } catch (Exception e) {
                VolleyLog.e(e, "Unhandled exception %s", e.toString());
                VolleyError volleyError = new VolleyError(e);
                volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime() - startTimeMs);
                mDelivery.postError(request, volleyError);
                
            }
        }
    }
    
    
    private void parseAndDeliverNetworkError(Request<?> request, VolleyError error) {
        error = request.parseNetworkError(error);
        mDelivery.postError(request, error);
    }
}
