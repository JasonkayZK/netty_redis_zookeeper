/**
 * Created by nien woo
 * Date: 17-12-30
 * Time: 下午4:50
 */
package com.crazymakercircle.util;


public class Logger
{

    /**
     * 带着方法名输出，方法名称放在前面
     *
     * @param s 待输出的字符串形参
     */
    public static void debug(Object s)
    {
        String content = null;
        if (null != s)
        {
            content = s.toString().trim();
        }
        else
        {
            content = "";
        }

        String out = String.format("%20s |>  %s ", ReflectionUtil.getCallMethod(), content);
        System.out.println(out);
    }


    /**
     * 带着线程名+类名+方法名称输出
     *
     * @param s 待输出的字符串形参
     */
    synchronized public static void info(Object s)
    {
        String content = null;
        if (null != s)
        {
            content = s.toString().trim();
        }
        else
        {
            content = "";
        }
        String cft = "[" + Thread.currentThread().getName() + "|" + ReflectionUtil.getNakeCallClassMethod() + "]";

        String out = String.format("%20s |>  %s ", cft, content);
        System.out.println(out);

    }

    /**
     * 带着线程名+类名+方法名称输出
     *
     * @param args 待输出的字符串形参
     */
    synchronized public static void info(Object... args)
    {
        StringBuilder content = new StringBuilder();
        ;
        for (int i = 0; i < args.length; i++) {
            content.append(args[i]!=null?args[i].toString():"null");
            content.append(" ");
        }

        String cft = "[" + Thread.currentThread().getName() + "|" + ReflectionUtil.getNakeCallClassMethod() + "]";

        String out = String.format("%20s |>  %s ", cft, content.toString());
        System.out.println(out);

    }

    /**
     * 带着线程名+类名+方法名称输出
     *
     * @param args 待输出的字符串形参
     */
    synchronized public static void error(Object... args)
    {
        StringBuilder content = new StringBuilder();
        ;
        for (int i = 0; i < args.length; i++) {
            content.append(args[i]!=null?args[i].toString():"null");
            content.append(" ");
        }

        String cft = "[" + Thread.currentThread().getName() + "|" + ReflectionUtil.getNakeCallClassMethod() + "]";

        String out = String.format("%20s |>  %s ", cft, content.toString());
        System.err.println(out);

    }

    /**
     * 编程过程中的提示说明
     *
     * @param s 提示的字符串形参
     */
    public static void hint(Object s)
    {
        System.out.println("/--" + s + "--/");
    }
}
