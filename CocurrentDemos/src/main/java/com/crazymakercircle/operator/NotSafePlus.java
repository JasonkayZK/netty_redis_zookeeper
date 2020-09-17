/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.crazymakercircle.operator;

import com.crazymakercircle.util.Print;

/**
 * 不安全的自增 运算
 */
public class NotSafePlus {
    public static final int MAX_TURN = 1000000;

    static class NotSafeCounter implements Runnable {
        public int amount = 0;

        public void increase() {
            amount++;
        }

        @Override
        public void run() {
            int turn = 0;
            while (turn < MAX_TURN) {
                ++turn;
                increase();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        NotSafeCounter counter = new NotSafeCounter();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(counter);
            thread.start();
        }
        Thread.sleep(2000);
        Print.tcfo("理论结果：" + MAX_TURN * 10);
        Print.tcfo("实际结果：" + counter.amount);
        Print.tcfo("差距是：" + (MAX_TURN * 10 - counter.amount));
    }
}