package com.crazymakercircle.redis.jedisPool;

import com.crazymakercircle.util.Logger;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class JredisPoolTester {

    public static final int NUM = 200;
    public static final String ZSET_KEY = "zset1";


    //测试删除
    @Test
    public void testDel() {
        Jedis redis =null;
        try  {
            redis = JredisPoolBuilder.getJedis();
            long start = System.currentTimeMillis();
            redis.del(ZSET_KEY);
            long end = System.currentTimeMillis();
            Logger.info("删除 zset1  毫秒数:", end - start);
        } finally {
            //使用后一定关闭，还给连接池
            if (redis != null) {
                redis.close();
            }
        }
    }

    //测试创建zset
    @Test
    public void testSet() {
        testDel();

        try (Jedis redis = JredisPoolBuilder.getJedis()) {
            int loop = 0;
            long start = System.currentTimeMillis();
            while (loop < NUM) {
                redis.zadd(ZSET_KEY, loop, "field-" + loop);
                loop++;
            }
            long end = System.currentTimeMillis();
            Logger.info("设置 zset :", loop, "次, 毫秒数:", end - start);
        }
    }
    //测试查询zset
    @Test
    public void testGet() {

         try (Jedis redis = JredisPoolBuilder.getJedis()) {
            long start = System.currentTimeMillis();
            Set<String>   set = redis.zrange(ZSET_KEY, 0, -1);
            long end = System.currentTimeMillis();
            Logger.info("顺序获取 zset  毫秒数:", end - start);
            Logger.info(set.toString());
        }


        try (Jedis redis = JredisPoolBuilder.getJedis()) {
            long start = System.currentTimeMillis();
            Set<String>   set = redis.zrevrange(ZSET_KEY, 0, -1);
            long end = System.currentTimeMillis();
            Logger.info("逆序获取 zset  毫秒数:", end - start);
            Logger.info(set.toString());
        }


    }

}