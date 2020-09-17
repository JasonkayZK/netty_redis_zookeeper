package com.crazymakercircle.redis.jedis;

import com.crazymakercircle.util.Logger;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class ListDemo {


    /**
     * Redis列表是简单的字符串列表，按照插入顺序排序。
     * 可以添加一个元素到列表的头部（左边）或者尾部（右边）
     */
    @Test
    public void operateList() {
        Jedis jedis = new Jedis("localhost");
        Logger.info("jedis.ping(): " +jedis.ping());
        jedis.del("list1");

        //从list尾部添加3个元素
        jedis.rpush("list1", "zhangsan", "lisi", "wangwu");

        //取得类型, list
        Logger.info("jedis.type(): " +jedis.type("list1"));

        //遍历区间[0,-1]，取得全部的元素
        Logger.info("jedis.lrange(0,-1): " +jedis.lrange("list1", 0, -1));
        //遍历区间[1,2]，取得区间的元素
        Logger.info("jedis.lrange(1,2): " +jedis.lrange("list1", 1, 2));

        //获取list长度
        Logger.info("jedis.llen(list1): " +jedis.llen("list1"));
        //获取下标为 1 的元素
        Logger.info("jedis.lindex(list1,1): " +jedis.lindex("list1", 1));
        //左侧弹出元素
        Logger.info("jedis.lpop(): " +jedis.lpop("list1"));
        //右侧弹出元素
        Logger.info("jedis.rpop(): " +jedis.rpop("list1"));
        //设置下标为0的元素val
        jedis.lset("list1", 0, "lisi2");
        //最后，遍历区间[0,-1]，取得全部的元素
        Logger.info("jedis.lrange(0,-1): " +jedis.lrange("list1", 0, -1));

        jedis.close();
    }



}
