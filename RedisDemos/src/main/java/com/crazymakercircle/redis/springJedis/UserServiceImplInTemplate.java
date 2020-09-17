package com.crazymakercircle.redis.springJedis;

import com.crazymakercircle.im.common.bean.User;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/

public class UserServiceImplInTemplate implements UserService {

    public static final String USER_UID_PREFIX = "user:uid:";

    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final long CASHE_LONG = 60 * 4;//4分钟

    /**
     * CRUD 之  新增/更新
     *
     * @param user 用户
     */
    @Override
    public User saveUser(final User user) {
        //保存到缓存
        redisTemplate.execute(new RedisCallback<User>() {

            @Override
            public User doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = serializeKey(USER_UID_PREFIX + user.getUid());
                connection.set(key, serializeValue(user));
                connection.expire(key, CASHE_LONG);
                return user;
            }
        });
        //保存到数据库
        //...如mysql
        return user;
    }

    private byte[] serializeValue(User s) {
        return redisTemplate
                .getValueSerializer().serialize(s);
    }

    private byte[] serializeKey(String s) {
        return redisTemplate
                .getKeySerializer().serialize(s);
    }

    private User deSerializeValue(byte[] b) {
        return (User) redisTemplate
                .getValueSerializer().deserialize(b);
    }

    /**
     * CRUD 之   查询
     *
     * @param id id
     * @return 用户
     */
    @Override
    public User getUser(final long id) {
        //首先从缓存中获取
        User value =  (User) redisTemplate.execute(new RedisCallback<User>() {
            @Override
            public User doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = serializeKey(USER_UID_PREFIX + id);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    return deSerializeValue(value);
                }

                return null;
            }
        });
        if (null == value) {
            //如果缓存中没有，从数据库中获取
            //...如mysql
            //并且，保存到缓存
        }
        return value;
    }

    /**
     * CRUD 之 删除
     * @param id id
     */
    @Override
    public void deleteUser(long id) {
        //从缓存删除
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] key = serializeKey(USER_UID_PREFIX + id);
                if (connection.exists(key)) {
                    connection.del(key);
                }
                return true;
            }
        });
        //从数据库删除
        //...如mysql
    }

    /**
     * 删除全部
     */
    @Override
    public void deleteAll() {

    }


}