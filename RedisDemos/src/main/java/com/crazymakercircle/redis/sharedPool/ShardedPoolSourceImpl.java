package com.crazymakercircle.redis.sharedPool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Slf4j
@Repository("shardedPoolSourceImpl")
public class ShardedPoolSourceImpl implements ShardedPoolSource<ShardedJedis> {

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public ShardedJedis getRedisClient() {
        ShardedJedis shardJedis = null;
        try {
            shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            log.error("[JedisDS] getRedisClent error:" + e.getMessage());
            if (null != shardJedis)
                shardJedis.close();
        }
        return null;
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            log.error("redis 连接池出现异常");
        }
        ((ShardedJedis) shardedJedis).close();
    }

}