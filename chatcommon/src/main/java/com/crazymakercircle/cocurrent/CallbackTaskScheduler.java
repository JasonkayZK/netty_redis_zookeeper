/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.crazymakercircle.cocurrent;


import com.google.common.util.concurrent.*;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CallbackTaskScheduler extends Thread
{
    private final Logger logger = Logger.getLogger(this.getClass());
    private ConcurrentLinkedQueue<CallbackTask> executeTaskQueue =
            new ConcurrentLinkedQueue<CallbackTask>();// 任务队列
    private long sleepTime = 200;// 线程休眠时间
    private ExecutorService jPool =
            Executors.newFixedThreadPool(10);

    ListeningExecutorService gPool =
            MoreExecutors.listeningDecorator(jPool);


    private static CallbackTaskScheduler inst = new CallbackTaskScheduler();

    private CallbackTaskScheduler()
    {
        this.start();
    }

    /**
     * 添加任务
     *
     * @param executeTask
     */


    public static <R> void add(CallbackTask<R> executeTask)
    {
        inst.executeTaskQueue.add(executeTask);
    }

    @Override
    public void run()
    {
        while (true)
        {
            handleTask();// 处理任务
            threadSleep(sleepTime);
        }
    }

    private void threadSleep(long time)
    {
        try
        {
            sleep(time);
        } catch (InterruptedException e)
        {
            logger.error(e);
        }
    }

    /**
     * 处理任务队列，检查其中是否有任务
     */
    private void handleTask()
    {
        try
        {
            CallbackTask executeTask = null;
            while (executeTaskQueue.peek() != null)
            {
                executeTask = executeTaskQueue.poll();
                handleTask(executeTask);
            }
        } catch (Exception e)
        {
            logger.error(e);
        }
    }

    /**
     * 执行任务操作
     *
     * @param executeTask
     */
    private <R> void handleTask(CallbackTask<R> executeTask)
    {

        ListenableFuture<R> future = gPool.submit(new Callable<R>()
        {
            public R call() throws Exception {

             R r= executeTask.execute();
             return r;
            }

        });

        Futures.addCallback(future, new FutureCallback<R>() {
            public void onSuccess(R r) {
                executeTask.onSuccess(r);
            }

            public void onFailure(Throwable t) {
                executeTask.onFailure(t);
            }
        });


    }

}
