package com.crazymakercircle.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;


public class JvmUtil {

    public static final int getProcessID() {
        //  ManagementFactory是一个在运行时管理和监控Java VM的工厂类
        //  它能提供很多管理VM的静态接口的运行时实例，比如RuntimeMXBean
        //  RuntimeMXBean是Java虚拟机的运行时管理接口.
        //  取得VM运行管理实例，到管理接口句柄
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        //  取得VM运行管理实例的名称，也是JVM运行实例的名称
        String jvmInstanceName = runtimeMXBean.getName();
        return Integer.valueOf(jvmInstanceName.split("@")[0]).intValue();
    }
}
