/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.crazymakercircle.util;


public class Print {

    /**
     * 信息输出
     *
     * @param s 输出的字符串形参
     */
    public static void o(Object s) {
        System.out.println(s);
    }

    /**
     * 带着方法名输出，方法名称放在前面
     *
     * @param s 待输出的字符串形参
     */
    public static void fo(Object s) {
        System.out.println(ReflectionUtil.getCallMethod() + ":" + s);
    }

    /**
     * 带着类名+方法名输出
     *
     * @param s 待输出的字符串形参
     */
    synchronized public static void cfo(Object s) {
        System.out.println(ReflectionUtil.getCallClassMethod() + ":" + s);
    }

    /**
     * 带着线程名+类名+方法名称输出
     *
     * @param s 待输出的字符串形参
     */
    synchronized public static void tcfo(Object s) {
        String cft = "[" + Thread.currentThread().getName() + "|" + ReflectionUtil.getNakeCallClassMethod() + "]";
        System.out.println(cft + "：" + s);
    }

    /**
     * 编程过程中的提示说明
     *
     * @param s 提示的字符串形参
     */
    public static void hint(Object s) {
        System.out.println("/--" + s + "--/");
    }
}
