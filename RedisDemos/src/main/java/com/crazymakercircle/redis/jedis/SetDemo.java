package com.crazymakercircle.redis.jedis;

import com.crazymakercircle.util.Logger;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class SetDemo {


    /**
     * Redis 的 Set 是 String 类型的无序集合。
     * 集合成员是唯一的，这就意味着集合中不能出现重复的数据。
     * Redis 中集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。
     * 集合中最大的成员数为 2^32 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */
    @Test
    public void operateSet() {
        Jedis jedis = new Jedis("localhost");
        jedis.del("set1");
        Logger.info("jedis.ping(): " + jedis.ping());
        Logger.info("jedis.type(): " + jedis.type("set1"));

        //sadd函数: 向集合添加元素
        jedis.sadd("set1", "user01", "user02", "user03");
        //smembers函数: 遍历所有元素
        Logger.info("jedis.smembers(): " + jedis.smembers("set1"));
        //scard函数: 获取集合元素个数
        Logger.info("jedis.scard(): " + jedis.scard("set1"));
        //sismember 判断是否是集合元素
        Logger.info("jedis.sismember(user04): " + jedis.sismember("set1", "user04"));
        //srem函数：移除元素
        Logger.info("jedis.srem(): " + jedis.srem("set1", "user02", "user01"));
        //smembers函数: 遍历所有元素
        Logger.info("jedis.smembers(): " + jedis.smembers("set1"));

        jedis.close();
    }


}
