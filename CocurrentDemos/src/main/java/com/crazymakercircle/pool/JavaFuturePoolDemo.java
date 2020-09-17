package com.crazymakercircle.pool;

import com.crazymakercircle.util.Print;

import java.util.concurrent.*;

/**
 * Created by 尼恩 at 疯狂创客圈
 */

public class JavaFuturePoolDemo {

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

        Callable<Boolean> hJob = new HotWarterJob();//异步逻辑

        Callable<Boolean> wJob = new WashJob();//异步逻辑

        ExecutorService pool =
                Executors.newFixedThreadPool(10);


//        new ThreadPoolExecutor(
//                corePoolSize, maximumPoolSize,keepAliveTime, milliseconds,runnableTaskQueue, threadFactory,serverHandler);

        Future<Boolean> hTask = pool.submit(hJob);
        Future<Boolean> wTask = pool.submit(wJob);


        try {

            boolean warterOk = hTask.get();
            boolean cupOk = wTask.get();

//            hThread.join();
//            wThread.join();


            Thread.currentThread().setName("主线程");
            if (warterOk && cupOk) {
                Print.tcfo("泡茶喝");
            } else if (!warterOk) {
                Print.tcfo("烧水失败，没有茶喝了");
            } else if (!cupOk) {
                Print.tcfo("杯子洗不了，没有茶喝了");
            }

        } catch (InterruptedException e) {
            Print.tcfo(getCurThreadName() + "发生异常被中断.");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Print.tcfo(getCurThreadName() + " 运行结束.");

        pool.shutdown();
    }
}