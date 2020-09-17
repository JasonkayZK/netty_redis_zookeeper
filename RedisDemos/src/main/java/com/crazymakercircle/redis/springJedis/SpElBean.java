package com.crazymakercircle.redis.springJedis;

import com.crazymakercircle.util.Logger;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Component
@Data
public class SpElBean {
    /**
     * 算术运算符
     */
    @Value("#{10+2*3/4-2}")
    private int algDemoValue;


    /**
     * 字符串运算符
     */
    @Value("#{'Hello ' + 'World!'}")
    private String stringConcatValue;

    /**
     * 类型运算符
     */
    @Value("#{ T(java.lang.Math).random() * 100.0 }")
    private int randomInt;


    /**
     * 展示SpEl 上下文变量
     */
    public void showContextVar() {
        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("foo", "bar");
        String foo = parser.parseExpression("#foo").getValue(context, String.class);
        Logger.info(" foo:=", foo);

        context = new StandardEvaluationContext("I am root");
        String root = parser.parseExpression("#root").getValue(context, String.class);
        Logger.info(" root:=", root);

        String result3 = parser.parseExpression("#this").getValue(context, String.class);
        Logger.info(" this:=", root);
    }

}
