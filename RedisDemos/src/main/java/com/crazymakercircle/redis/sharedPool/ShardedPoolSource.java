package com.crazymakercircle.redis.sharedPool;

import redis.clients.jedis.JedisCommands;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public interface ShardedPoolSource<T extends JedisCommands> {
    T getRedisClient();

    void returnResource(T shardedJedis, boolean broken);
}
