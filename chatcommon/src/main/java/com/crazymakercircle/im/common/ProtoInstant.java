package com.crazymakercircle.im.common;

public class ProtoInstant
{

    /**
     * 消息类型
     */
    public class MsgType
    {

        //登陆请求
        public static final int OP_Login_REQUEST = 1;

        //登陆应答
        public static final int OP_Login_RESPONSE = 2;


        //心跳请求
        public static final int OP_HEARTBEAT_REQUEST = 3;
        //心跳应答
        public static final int OP_HEARTBEAT_RESPONSE = 4;
        //IM消息请求
        public static final int OP_MESSAGE_REQUEST = 5;

        //IM消息应答
        public static final int OP_MESSAGE_RESPONSE = 6;


        //通知应答
        public static final int OP_NOTIFICATION = 7;

    }

    /**
     * 客户端平台
     */
    public interface Platform
    {
        /**
         * windwos
         */
        public static final int WINDOWS = 1;

        /**
         * mac
         */
        public static final int MAC = 2;
        /**
         * android端
         */
        public static final int ANDROID = 3;
        /**
         * IOS端
         */
        public static final int IOS = 4;
        /**
         * WEB端
         */
        public static final int WEB = 5;
        /**
         * 未知
         */
        public static final int UNKNOWN = 6;


    }

    /**
     * 协议细节
     */
    public class Protocal
    {

        /**
         * 报文头长度
         */
        public static final int HEADER_LENGTH = 16;
        public static final int FIELD_PACKAGE_SIZE_LENGTH = 4;
        public static final int FIELD_VERSION_LENGTH = 2;
        public static final int FIELD_ID_LENGTH = 4;
        public static final int FIELD_TYPE_LENGTH = 2;
        public static final int FIELD_COMMON_LENGTH = 4;
    }

    /**
     * 返回码枚举类
     */
    public enum ResultCodeEnum
    {

        SUCCESS(0, "Success"),  // 成功
        AUTH_FAILED(1, "登录失败"),
        NO_TOKEN(2, "没有授权码"),
        UNKNOW_ERROR(3, "未知错误"),;

        private Integer code;
        private String desc;

        ResultCodeEnum(Integer code, String desc)
        {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode()
        {
            return code;
        }

        public String getDesc()
        {
            return desc;
        }

    }

}
