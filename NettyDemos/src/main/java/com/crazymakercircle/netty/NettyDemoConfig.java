package com.crazymakercircle.netty;

import com.crazymakercircle.util.ConfigProperties;

public class NettyDemoConfig extends ConfigProperties
{


    static ConfigProperties singleton
            = new NettyDemoConfig("/system.properties");

    private NettyDemoConfig(String fileName)
    {
        super(fileName);
        super.loadFromFile();
    }


    public static final String FILE_SRC_PATH
            = singleton.getValue("file.src.path");

    public static final String FILE_DEST_PATH
            = singleton.getValue("file.dest.path");

    public static final String FILE_RESOURCE_SRC_PATH
            = singleton.getValue("file.resource.src.path");

    public static final String FILE_RESOURCE_DEST_PATH
            = singleton.getValue("file.resource.dest.path");


    //发送文件路径

    public static final String SOCKET_SEND_FILE
            = singleton.getValue("socket.send.file");
    public static final String SOCKET_RECEIVE_FILE
            = singleton.getValue("socket.receive.file");
    public static final String SOCKET_RECEIVE_PATH
            = singleton.getValue("socket.receive.path");


    public static final int SEND_BUFFER_SIZE
            = singleton.getIntValue("send.buffer.size");

    public static final int SERVER_BUFFER_SIZE
            = singleton.getIntValue("server.buffer.size");

    public static final String SOCKET_SERVER_IP
            = singleton.getValue("socket.server.ip");

    public static final int SOCKET_SERVER_PORT
            = singleton.getIntValue("socket.server.port");


}
