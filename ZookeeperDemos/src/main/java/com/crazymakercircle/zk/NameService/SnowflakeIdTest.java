package com.crazymakercircle.zk.NameService;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Slf4j
public class SnowflakeIdTest {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws InterruptedException {
        SnowflakeIdGenerator.instance.init(SnowflakeIdWorker.instance.getId());
        ExecutorService es = Executors.newFixedThreadPool(10);
        final HashSet idSet = new HashSet();
        Collections.synchronizedCollection(idSet);
        long start = System.currentTimeMillis();
        log.info(" start generate id *");
        for (int i = 0; i < 10; i++)
            es.execute(() -> {
                for (long j = 0; j < 5000000; j++) {
                    long id = SnowflakeIdGenerator.instance.nextId();
                    synchronized (idSet) {
                        idSet.add(id);
                    }
                }
            });
        es.shutdown();
        es.awaitTermination(10, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        log.info(" end generate id ");
        log.info("* cost " + (end - start) + " ms!");
    }
}
