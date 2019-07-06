package com.wenchao.wenchaohttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wenchao
 * @date 2019/7/2.
 * @time 22:59
 * description：线程池管理类
 */
public class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }

    /**
     * 创建队列
     */
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();
    /**
     * 创建延迟队列
     */
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public void addDelayTask(HttpTask httpTask) {
        if (null != httpTask) {
            httpTask.setDelayTime(3000);
            mDelayQueue.offer(httpTask);
        }
    }

    /**
     * 将异步任务添加到队列中
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        if (null != runnable) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建线程池
     */
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });
        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    /**
     * 创建核心线程,去队列中获取请求,提交给线程池处理
     */
    public Runnable coreThread = new Runnable() {
        Runnable runnable;

        @Override
        public void run() {
            while (true) {
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runnable);
            }
        }
    };

    public Runnable delayThread = new Runnable() {
        @Override
        public void run() {
            HttpTask httpTask = null;
            while (true) {
                //判断当前网络是否重新连接成功 成功才执行一下代码
                try {
                    httpTask = mDelayQueue.take();
                    if (httpTask.getRetryCount() < 3) {
                        mThreadPoolExecutor.execute(httpTask);
                        httpTask.setRetryCount(httpTask.getRetryCount() + 1);
                        Log.e("===>", "===重试===" + System.currentTimeMillis());
                    } else {
                        Log.e("===>", "已经重试了三次,放弃了这个请求");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
