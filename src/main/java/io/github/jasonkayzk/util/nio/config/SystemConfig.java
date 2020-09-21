package io.github.jasonkayzk.util.nio.config;

import io.github.jasonkayzk.util.nio.annotations.ConfigField;
import io.github.jasonkayzk.util.nio.annotations.ConfigFile;
import io.github.jasonkayzk.util.nio.util.ConfigProperties;
import io.github.jasonkayzk.util.nio.util.Logger;

@ConfigFile(file = "/system.properties")
public class SystemConfig extends ConfigProperties {
    static
    {
        Logger.debug("开始加载配置文件到SystemConfig");
        //依照注解装载配置项
        loadAnnotations(SystemConfig.class);
    }

    //服务器ip
    @ConfigField(property = "socket.server.ip")
    public static String SOCKET_SERVER_IP;

    //服务器地址
    @ConfigField(property = "socket.server.port")
    public static int SOCKET_SERVER_PORT;

    //发送文件路径
    @ConfigField(property = "socket.send.file")
    public static String SOCKET_SEND_FILE;

    @ConfigField(property = "socket.recieve.path")
    public static String SOCKET_RECIEVE_PATH;

    //第三方的类路径
    @ConfigField(property = "class.server.path")
    public static String CLASS_SERVER_PATH;

    //宠物狗的类型
    @ConfigField(property = "pet.dog.class")
    public static String PET_DOG_CLASS;

    @ConfigField(property = "send.buffer.size")
    public static int SEND_SIZE;

    @ConfigField(property = "server.buffer.size")
    public static int INPUT_SIZE;

    @ConfigField(property = "file.src.path")
    public static String FILE_SRC_PATH;

    @ConfigField(property = "debug")
    public static boolean debug;

    /**
     * 宠物工厂类的名称
     */
    @ConfigField(property = "pet.factory.class")
    public static String PET_FACTORY_CLASS;

    /**
     * 宠物模块的类路径
     */
    @ConfigField(property = "pet.lib.path")
    public static String PET_LIB_PATH;
}
