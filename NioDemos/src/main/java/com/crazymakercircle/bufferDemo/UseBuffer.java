package com.crazymakercircle.bufferDemo;

import com.crazymakercircle.util.Logger;

import java.nio.IntBuffer;

public class UseBuffer {
    static IntBuffer intBuffer = null;

    public static void allocatTest() {
        intBuffer = IntBuffer.allocate(20);
        Logger.debug("------------after allocate------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void putTest() {
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);

        }

        Logger.debug("------------after putTest------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());

    }

    public static void flipTest() {

        intBuffer.flip();
        Logger.debug("------------after flipTest ------------------");

        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void getTest() {
        for (int i = 0; i < 2; i++) {
            int j = intBuffer.get();
            Logger.debug("j = " + j);
        }
        Logger.debug("------------after get 2 int ------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
        for (int i = 0; i < 3; i++) {
            int j = intBuffer.get();
            Logger.debug("j = " + j);
        }
        Logger.debug("------------after get 3 int ------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void rewindTest() {

        intBuffer.rewind();
        Logger.debug("------------after rewind ------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }


    /**
     * rewind之后，重复读
     * 并且演示 mark 标记方法
     */
    public static void reRead() {


        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                intBuffer.mark();
            }
            int j = intBuffer.get();
            Logger.debug("j = " + j);

        }
        Logger.debug("------------after reRead------------------");
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());

    }

    public static void afterReset() {
        Logger.debug("------------after reset------------------");
        intBuffer.reset();
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
        for (int i =2; i < 5; i++) {
            int j = intBuffer.get();
            Logger.debug("j = " + j);

        }

    }

    public static void clearDemo() {
        Logger.debug("------------after clear------------------");
        intBuffer.clear();
        Logger.debug("position=" + intBuffer.position());
        Logger.debug("limit=" + intBuffer.limit());
        Logger.debug("capacity=" + intBuffer.capacity());
    }

    public static void main(String[] args) {
        Logger.debug("分配内存");

        allocatTest();

        Logger.debug("写入");
        putTest();

        Logger.debug("翻转");

        flipTest();

        Logger.debug("读取");
        getTest();

        Logger.debug("重复读");
        rewindTest();
        reRead();

        Logger.debug("make&reset写读");
        afterReset();

        Logger.debug("清空");

        clearDemo();

    }
}


