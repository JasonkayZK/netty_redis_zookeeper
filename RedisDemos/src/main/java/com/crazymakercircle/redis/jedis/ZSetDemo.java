package com.crazymakercircle.redis.jedis;

import com.crazymakercircle.util.Logger;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class ZSetDemo {

    /**
     * Redis 有序集合和集合一样也是string类型元素的集合,且不允许重复的成员。
     * 不同的是每个元素都会关联一个double类型的分数。
     * redis正是通过分数来为集合中的成员进行从小到大的排序。
     * 有序集合的成员是唯一的,但分数(score)却可以重复。
     * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。
     * 集合中最大的成员数为 2^32 - 1 (4294967295, 每个集合可存储40多亿个成员)。
     */
    @Test
    public void operateZset() {

        Jedis jedis = new Jedis("localhost");
        Logger.info("jedis.get(): " + jedis.ping());

        jedis.del("salary");
        Map<String, Double> members = new HashMap<String, Double>();
        members.put("u01", 1000.0);
        members.put("u02", 2000.0);
        members.put("u03", 3000.0);
        members.put("u04", 13000.0);
        members.put("u05", 23000.0);
        //批量添加元素
        jedis.zadd("salary", members);
        //类型,zset
        Logger.info("jedis.type(): " + jedis.type("salary"));

        //获取集合元素个数
        Logger.info("jedis.zcard(): " + jedis.zcard("salary"));
        //按照下标[起,止]遍历元素
        Logger.info("jedis.zrange(): " + jedis.zrange("salary", 0, -1));
        //按照下标[起,止]倒序遍历元素
        Logger.info("jedis.zrevrange(): " + jedis.zrevrange("salary", 0, -1));

        //按照分数（薪资）[起,止]遍历元素
        Logger.info("jedis.zrangeByScore(): " + jedis.zrangeByScore("salary", 1000, 10000));
        //按照薪资[起,止]遍历元素,带分数返回
        Set<Tuple> res0 = jedis.zrangeByScoreWithScores("salary", 1000, 10000);
        for (Tuple temp : res0) {
            Logger.info("Tuple.get(): " + temp.getElement() + " -> " + temp.getScore());
        }
        //按照分数[起,止]倒序遍历元素
        Logger.info("jedis.zrevrangeByScore(): " + jedis.zrevrangeByScore("salary", 1000, 4000));
        //获取元素[起,止]分数区间的元素数量
        Logger.info("jedis.zcount(): " + jedis.zcount("salary", 1000, 4000));

        //获取元素score值：薪资
        Logger.info("jedis.zscore(): " + jedis.zscore("salary", "u01"));
        //获取元素下标
        Logger.info("jedis.zrank(u01): " + jedis.zrank("salary", "u01"));
        //倒序获取元素下标
        Logger.info("jedis.zrevrank(u01): " + jedis.zrevrank("salary", "u01"));
        //删除元素
        Logger.info("jedis.zrem(): " + jedis.zrem("salary", "u01", "u02"));
        //删除元素,通过下标范围
        Logger.info("jedis.zremrangeByRank(): " + jedis.zremrangeByRank("salary", 0, 1));
        //删除元素,通过分数范围
        Logger.info("jedis.zremrangeByScore(): " + jedis.zremrangeByScore("salary", 20000, 30000));
        //按照下标[起,止]遍历元素
        Logger.info("jedis.zrange(): " + jedis.zrange("salary", 0, -1));

        Map<String, Double> members2 = new HashMap<String, Double>();
        members2.put("u11", 1136.0);
        members2.put("u12", 2212.0);
        members2.put("u13", 3324.0);
        //批量添加元素
        jedis.zadd("salary", members2);
        //增加指定分数
        Logger.info("jedis.zincrby(10000): " + jedis.zincrby("salary", 10000, "u13"));
        //按照下标[起,止]遍历元素
        Logger.info("jedis.zrange(): " + jedis.zrange("salary", 0, -1));

        jedis.close();

    }
}
