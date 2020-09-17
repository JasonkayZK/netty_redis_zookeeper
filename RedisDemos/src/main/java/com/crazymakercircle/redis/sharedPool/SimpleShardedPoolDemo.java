package com.crazymakercircle.redis.sharedPool;

import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Arrays;
import java.util.List;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class SimpleShardedPoolDemo {

    private static ShardedJedisPool shardedPool = null;

    public static ShardedJedisPool getShardedPool() {
        if (shardedPool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(500);
            config.setMaxIdle(5);
            config.setMaxWaitMillis(1000 * 10);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);


            //设置Redis信息
            String host = "127.0.0.1";
            JedisShardInfo shardInfo1 = new JedisShardInfo(host, 6379, 500);
            shardInfo1.setPassword("test123");
            JedisShardInfo shardInfo2 = new JedisShardInfo(host, 6380, 500);
            shardInfo2.setPassword("test123");
            JedisShardInfo shardInfo3 = new JedisShardInfo(host, 6381, 500);
            shardInfo3.setPassword("test123");

            //初始化ShardedJedisPool
            List<JedisShardInfo> infoList = Arrays.asList(shardInfo1, shardInfo2, shardInfo3);
            ShardedJedisPool pool = new ShardedJedisPool(config, infoList);

        }
        return shardedPool;
    }

    public synchronized static ShardedJedis getResource() {
        if (shardedPool == null) {
            shardedPool = getShardedPool();
        }


        return shardedPool.getResource();
    }


    @Test
    public void testCRUD() {
        ShardedJedis redis = null;
        int loop = 1;
        while (loop < 20) {
            try {
                long start = System.currentTimeMillis();
                redis = getResource();
                redis.set("k1", "v1");
                String ret = redis.get("k1");
                long end = System.currentTimeMillis();
                System.out.printf("Get ret from redis: %s with %d millis\n", ret, end - start);
            } finally {
                //使用后一定关闭，还给连接池

                if (redis != null) {
                    redis.close();
                }
            }
            loop++;
        }
    }

}