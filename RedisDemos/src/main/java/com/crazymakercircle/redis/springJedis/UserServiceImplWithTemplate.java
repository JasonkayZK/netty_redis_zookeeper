package com.crazymakercircle.redis.springJedis;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.util.Logger;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class UserServiceImplWithTemplate implements UserService {

    public static final String USER_UID_PREFIX = "user:uid:";
    protected CacheOperationService cacheOperationService;
    private static final long CASHE_LONG = 60 * 4;//4分钟

    public void setCacheOperationService(
            CacheOperationService cacheOperationService) {
        this.cacheOperationService = cacheOperationService;
    }

    /**
     * CRUD 之  新增/更新
     *
     * @param user 用户
     */
    @Override
    public User saveUser(final User user) {
        //保存到缓存
        String key = USER_UID_PREFIX + user.getUid();
        Logger.info("user :", user);
        cacheOperationService.set(key, user, CASHE_LONG);
        //保存到数据库
        //...如mysql
        return user;
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
        String key = USER_UID_PREFIX + id;
        User value = (User) cacheOperationService.get(key);
        if (null == value) {
            //如果缓存中没有，从数据库中获取
            //...如mysql
            //并且，保存到缓存
        }

        return value;
    }

    /**
     * CRUD 之 删除
     *
     * @param id id
     */

    @Override
    public void deleteUser(long id) {
        //从缓存删除
        String key = USER_UID_PREFIX + id;
        cacheOperationService.del(key);
        //从数据库删除
        //...如mysql

        Logger.info("delete  User:", id);

    }

    /**
     * 删除全部
     */
    @Override
    public void deleteAll() {

    }
}