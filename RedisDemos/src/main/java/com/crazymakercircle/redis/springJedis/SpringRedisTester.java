package com.crazymakercircle.redis.springJedis;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.util.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class SpringRedisTester {


    /**
     * 测试 直接使用redisTemplate
     */
    @Test
    public void testServiceImplWithTemplate() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        UserService userService =
                (UserService) ac.getBean("serviceImplWithTemplate");
        long userId = 1L;
        userService.deleteUser(userId);
        User userInredis = userService.getUser(userId);
        Logger.info("delete user", userInredis);
        User user = new User();
        user.setUid("1");
        user.setNickName("foo");
        userService.saveUser(user);
        Logger.info("save user:", user);
        userInredis = userService.getUser(userId);
        Logger.info("get user", userInredis);
    }

    /**
     * 测试 回调使用redisTemplate
     */

    @Test
    public void testServiceImplInTemplate() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        UserService userService =
                (UserService) ac.getBean("serviceImplInTemplate");
        long userId = 1L;
        userService.deleteUser(userId);
        User userInredis = userService.getUser(userId);
        Logger.info("delete user", userInredis);
        User user = new User();
        user.setUid("1");
        user.setNickName("foo");
        userService.saveUser(user);
        Logger.info("save user:", user);
        userInredis = userService.getUser(userId);
        Logger.info("get user", userInredis);
    }

    /**
     * 测试 注解使用redisTemplate
     */
    @Test
    public void testServiceImplWithAnno() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        UserService userService =
                (UserService) ac.getBean("userServiceImplWithAnno");
        long userId = 1L;
        userService.deleteUser(userId);
        User userInredis = userService.getUser(userId);
        Logger.info("delete user", userInredis);
        User user = new User();
        user.setUid("1");
        user.setNickName("foo");
        userService.saveUser(user);
        Logger.info("save user:", user);
        userInredis = userService.getUser(userId);
        Logger.info("get user", userInredis);

    }

    /**
     * 使用注解 删除全部
     */
    @Test
    public void testServiceDeleteAll() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        UserService userService =
                (UserService) ac.getBean("userServiceImplWithAnno");

        /**
         * 清空缓存
         */
        userService.deleteAll();
    }

    /**
     * 测试   SpEl 表达式
     */
    @Test
    public void testSpElBean() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring-redis.xml");
        SpElBean spElBean =
                (SpElBean) ac.getBean("spElBean");

        /**
         * 演示算术运算符
         */
        Logger.info(" spElBean.getAlgDemoValue():="
                , spElBean.getAlgDemoValue());

        /**
         * 演示 字符串运算符
         */
        Logger.info(" spElBean.getStringConcatValue():="
                , spElBean.getStringConcatValue());


        /**
         * 演示 类型运算符
         */
        Logger.info(" spElBean.getRandomInt():="
                , spElBean.getRandomInt());

        /**
         * 展示SpEl 上下文变量
         */
        spElBean.showContextVar();

    }



}
