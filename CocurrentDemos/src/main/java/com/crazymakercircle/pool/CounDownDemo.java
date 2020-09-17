package com.crazymakercircle.pool;

import com.crazymakercircle.util.Print;
import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 尼恩 at 疯狂创客圈
 */

public class CounDownDemo {

    public static final int SLEEP_GAP = 500;


    public static String getCurThreadName() {
        return Thread.currentThread().getName();
    }

    static class HotWarterJob implements Callable<Boolean> //①
    {

        @Override
        public Boolean call() throws Exception //②
        {

            try {
                Print.tcfo("洗好水壶");
                Print.tcfo("灌上凉水");
                Print.tcfo("放在火上");

                //线程睡眠一段时间，代表烧水中
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("水开了");

            } catch (InterruptedException e) {
                Print.tcfo(" 发生异常被中断.");
                return false;
            }
            Print.tcfo(" 运行结束.");

            return true;
        }
    }

    static class WashJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {


            try {
                Print.tcfo("洗茶壶");
                Print.tcfo("洗茶杯");
                Print.tcfo("拿茶叶");
                //线程睡眠一段时间，代表清洗中
                Thread.sleep(SLEEP_GAP);
                Print.tcfo("洗完了");

            } catch (InterruptedException e) {
                Print.tcfo(" 清洗工作 发生异常被中断.");
                return false;
            }
            Print.tcfo(" 清洗工作  运行结束.");
            return true;
        }

    }


    public static void main(String args[]) {

        Callable<Boolean> hJob = new HotWarterJob();//③

        Callable<Boolean> wJob = new WashJob();//③

        ExecutorService jPool =
                Executors.newFixedThreadPool(10);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        ListeningExecutorService gPool =
                MoreExecutors.listeningDecorator(jPool);

        ListenableFuture<Boolean> hFuture = gPool.submit(hJob);

        Futures.addCallback(hFuture, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean r) {
                if (!r) {
                    Print.tcfo("烧水失败，没有茶喝了");

                } else {

                    countDownLatch.countDown();
                }

            }

            public void onFailure(Throwable t) {
                Print.tcfo("烧水失败，没有茶喝了");
            }
        });


        ListenableFuture<Boolean> wFuture = gPool.submit(wJob);

        Futures.addCallback(wFuture, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean r) {
                if (!r) {
                    Print.tcfo("杯子洗不了，没有茶喝了");
                } else {

                    countDownLatch.countDown();

                }
            }

            public void onFailure(Throwable t) {
                Print.tcfo("杯子洗不了，没有茶喝了");
            }
        });

        try {
            synchronized (countDownLatch) {
                countDownLatch.wait(5000);
            }
            Thread.currentThread().setName("主线程");


            Print.tcfo("泡茶喝");


        } catch (InterruptedException e) {
            Print.tcfo(getCurThreadName() + "发生异常被中断.");
        }
        Print.tcfo(getCurThreadName() + " 运行结束.");

        gPool.shutdown();

    }


}