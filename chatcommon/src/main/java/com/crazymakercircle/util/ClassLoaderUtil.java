package com.crazymakercircle.util;

public class ClassLoaderUtil {

    /**
     * 显示classLoaderTree
     * <p>
     * 当前加载器和所有的父 加载器
     *
     * @param aClass
     */
    public static void showLoader4Class(Class aClass) {
        ClassLoader loader = aClass.getClassLoader();
        showLoaderTree(loader);
    }

    /**
     * 迭代，显示class loader 和 父加载器
     */
    public static void showLoaderTree(ClassLoader loader) {
        while (loader != null) {
            Logger.info(loader.toString());
            loader = loader.getParent();
        }
    }
}
