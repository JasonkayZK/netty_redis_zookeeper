package com.crazymakercircle.config;


import com.crazymakercircle.anno.ConfigFieldAnno;
import com.crazymakercircle.anno.ConfigFileAnno;
import com.crazymakercircle.util.ConfigProperties;
import com.crazymakercircle.util.Logger;

@ConfigFileAnno(file = "/system.properties")
public class SystemConfig extends ConfigProperties
{


    static
    {
        Logger.debug("开始加载配置文件到SystemConfig");
        //依照注解装载配置项
        loadAnnotations(SystemConfig.class);
    }

    //服务器ip
    @ConfigFieldAnno(proterty = "socket.server.ip")
    public static String SOCKET_SERVER_IP;

    //服务器地址
    @ConfigFieldAnno(proterty = "socket.server.port")
    public static int SOCKET_SERVER_PORT;


    //发送文件路径

    @ConfigFieldAnno(proterty = "socket.send.file")
    public static String SOCKET_SEND_FILE;

    @ConfigFieldAnno(proterty = "socket.recieve.path")
    public static String SOCKET_RECIEVE_PATH;

    //第三方的类路径
    @ConfigFieldAnno(proterty = "class.server.path")
    public static String CLASS_SERVER_PATH;

    //宠物狗的类型
    @ConfigFieldAnno(proterty = "pet.dog.class")
    public static String PET_DOG_CLASS;

    @ConfigFieldAnno(proterty = "send.buffer.size")
    public static int SEND_SIZE;

    @ConfigFieldAnno(proterty = "server.buffer.size")
    public static int INPUT_SIZE;

    @ConfigFieldAnno(proterty = "file.src.path")
    public static String FILE_SRC_PATH;


    @ConfigFieldAnno(proterty = "debug")
    public static boolean debug;

    /**
     * 宠物工厂类的名称
     */
    @ConfigFieldAnno(proterty = "pet.factory.class")
    public static String PET_FACTORY_CLASS;

    /**
     * 宠物模块的类路径
     */
    @ConfigFieldAnno(proterty = "pet.lib.path")
    public static String PET_LIB_PATH;

}
