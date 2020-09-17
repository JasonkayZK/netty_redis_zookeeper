package com.crazymakercircle.redis.sharedPool;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.springUtil.StartUpApplication;
import com.crazymakercircle.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartUpApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@ContextConfiguration("classpath:spring-redis-sharedPool.xml")
@Slf4j
public class ShardedPoolSourceTemplateTester {

    @Autowired
    private ShardedPoolSourceTemplate shardedPoolTemplate;

    public static final String USER_UID_PREFIX = "user:uid:";

    private static final long CASHE_LONG = 60 * 4;//4小时


    @Test
    public void testSave() {
        User user = new User();
        String key = USER_UID_PREFIX + user.getUid();
        String value = JsonUtil.pojoToJson(user);
        String r = shardedPoolTemplate.save(
                key, value, CASHE_LONG * 60);
        log.info(r);
    }


}
