/*

package com.example.administrator.other;

import android.content.Intent;
import android.os.*;
import android.os.Process;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


*/
/**
 *  用于说明 AsyncTask 的原理
 *    从 excute 方法为入口分析
 * Created by Administrator on 2016/1/31.
 *//*


public class MyAsyncTaskTest {

    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        return executeOnExecutor(sDefaultExecutor, params);
    }
    public final AsyncTask<Params, Progress, Result> executeOnExecutor(Executor exec, Params... params) {
        if (mStatus != Status.PENDING) {
            switch (mStatus) {
                case RUNNING:
                    throw new IllegalStateException("Cannot execute task:" + " the task is already running.");
                case FINISHED:
                    throw new IllegalStateException("Cannot execute task:" + " the task has already been executed "
                            + "(a task can be executed only once)");
            }
        }

        mStatus = Status.RUNNING;

        //  这里调用了 AsyncTask 的 onPreExecute 方法， onPreExecute 方法 是一个空方法，需要我们自己在
        // 实现自己的 AsyncTask 中重写改方法，做一些准备工作
        //  在主线程中执行
        onPreExecute();

        mWorker.mParams = params;

        // 把任务交给线程池 sDefaultExecutor 执行
        //  mFuture 是 FutureTask 对象，FutureTask 是一个并发类， 在这里充当 Runnable 作用
        exec.execute(mFuture);

        return this;
    }

    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
    private static volatile Executor sDefaultExecutor = SERIAL_EXECUTOR;

    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        public synchronized void execute(final Runnable r) {
            // 将mFuture 即 r  插入到任务队列 mTasks 中
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            // 如果没有没有正在活动的任务，则会执行下一个任务, 这样保证了所有的任务时一个一个执行
            // 所以，在默认情况下，AsyncTask 是串行执行的
            if (mActive == null) {
                scheduleNext();
            }
        }

        // 从任务队列中取出一个任务，如果这个任务不为空，则交给线程池执行
        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
             // 真正执行任务
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    // mFuture 和 mWoker 是在 AsyncTask 创建时就已经创建了
    mFuture = new FutureTask<Result>(mWorker);

    public void run() {
      ...
        try {
            Callable<V> c = callable;
            if (c != null && state == NEW) {
                V result;
                boolean ran;
                try {

                  // 执行传进来的 Callable 对象的 call 方法
                    //  这里我们传进去的是 mWorker
                    result = c.call();
                    ran = true;
                } catch (Throwable ex) {
                    result = null;
                    ran = false;
                    setException(ex);
                }
                if (ran)
                    set(result);
            }
    ...
    }

    mWorker = new WorkerRunnable<Params, Result>() {
        public Result call() throws Exception {
            mTaskInvoked.set(true);

            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            //noinspection unchecked
            //  这里执行 AsyncTask 的 doInBackground 方法， 返回执行的结果；
            // 这是一个空方法，需要我们在实现自己的 AsyncTask 对象时重新的；
            // 注意，因为我们把 mWoker 传进了 mFuture 中，而 mFuture 是在线程池中执行的，所以，
            //  这里是在线程中，而不是在主线程中。
            Result result = doInBackground(mParams);
            Binder.flushPendingCommands();
            // 把 doInBackground 执行后返回的结果，传进 postResult() 方法中
            return postResult(result);
        }
    };

        // postResult 方法通过 Handler 携带结果 result ，发送一个 MESSAG_POST_RESULT 消息
        private Result postResult(Result result) {
            @SuppressWarnings("unchecked")
            Message message = getHandler().obtainMessage(MESSAGE_POST_RESULT,  new AsyncTaskResult<Result>(this, result));
            message.sendToTarget();
            return result;
        }


        //  InternalHandler 是一个静态内部类 ,因为静态成员会在加载类的时候会进行初始化话，  为了能够将执行环境切换到主线程中，
        //  这就要求AsyncTask 的对象必须在主线程中加载。
        private static class InternalHandler extends Handler {

            public void handleMessage(Message msg) {
                AsyncTaskResult<?> result = (AsyncTaskResult<?>) msg.obj;
                switch (msg.what) {
                    case MESSAGE_POST_RESULT:
                        // There is only one result
                        result.mTask.finish(result.mData[0]);
                        break;
                    case MESSAGE_POST_PROGRESS:
                        // 会调用 AsyncTask 的 onProgressUpdate(...) 方法，主线程中调用
                        result.mTask.onProgressUpdate(result.mData);
                        break;
                }
            }
        }

        //  如果任务已经被取消，则执行 onCancelled(result) 方法，这亦是一个空方法，需要自己重写实现
       //  没有取消，则调用 onPostExecute(result) 方法， 整个过程结束。
        private void finish(Result result) {
            if (isCancelled()) {
                onCancelled(result);
            } else {
               // 调用 AsyncTask 的 onPostExecute 方法，主线程中调用
                onPostExecute(result);
            }
            mStatus = Status.FINISHED;
        }


    public void run() {
        mTid = Process.myTid();
        // 创建消息队列
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Process.setThreadPriority(mPriority);
        onLooperPrepared();
        // 开启消息循环

        Looper.loop();
        mTid = -1;
    }


    */
/**
     *  使用 HandlerThread
     *//*

    public void onCreate() {
        // TODO: It would be nice to have an option to hold a partial wakelock
        // during processing, and to have a static startService(Context, Intent)
        // method that would launch the service & hand off a wakelock.

        super.onCreate();
        HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }


    */
/**
     *   使用 mServiceHandler 发送消息，Handler 中 Looper 是顺序处理消息的。
     *   这里显示了为什么 IntentService 会顺序执行后台任务
     * @param intent  和 外界 startService(intent) 中的 intent 是完全一致的。
     * @param startId
     *//*

    public void onStart(Intent intent, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        mServiceHandler.sendMessage(msg);
    }

    */
/**
     *  处理发送过来的消息，然后停止 service
     *   onHandleIntent 是个抽象方法，子类自己实现。
     *//*

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            onHandleIntent((Intent)msg.obj);
            stopSelf(msg.arg1);
        }
    }

    */
/**
     *   线程池的真正实现
     * @param corePoolSize  线程池的核心线程数
     *                      默认情况下，核心线程会在线程池中一直存活，即使它们处于闲置状态。如果将 ThreadPoolExecutor 的 allowCoreThreadTimeOut
     *                      属性设置为 true, 那么闲置的核心线程在等待任务时会有超时策略，这个时间间隔由 keepAliveTime 所指定，等待时间超过 keepAliveTime
     *                      所指定的时长后，核心线程就会被终止。
     * @param maximumPoolSize   线程池所能容纳的最大线程数，当这个活动线程数达到这个数值后，后续的新任务将会被阻塞。
     * @param keepAliveTime 非核心线程闲置时的超时时长，超过这个时长，费核心线程就会被回收。
     *                      当 ThreadPoolExecutor 的 allowCoreThreadTimeOut 属性设置为 true 时， keepAliveTime 同样会作用于核心线程。
     * @param unit  指定 keepAliveTime 参数的时间单位
     *              常用有 TimeUnit.MILLISECONDS , TimeUnit.SECONDS 以及 TimeUnit.MINUTES
     * @param workQueue 线程池中的任务队列，通过线程池的 execute 方法提交的 Runnable 对象会存储在这个参数中。
     * @param threadFactory     线程工厂，为线程提供创建新线程的功能。 ThreadFactory 是一个接口，它只有一个方法 Thread newThread(Runnable r).
     *//*

    public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory){
        Executors.newFixedThreadPool();
    }
}


}

*/
