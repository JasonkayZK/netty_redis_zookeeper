package com.crazymakercircle.netty.protocol;

import com.crazymakercircle.util.Logger;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class ProtobufDemo {


    public static MsgProtos.Msg buildMsg() {
        MsgProtos.Msg.Builder personBuilder = MsgProtos.Msg.newBuilder();
        personBuilder.setId(1000);
        personBuilder.setContent("疯狂创客圈:高性能学习社群");
        MsgProtos.Msg message = personBuilder.build();
        return message;
    }

    //第1种方式:序列化 serialization & 反序列化 Deserialization
    @Test
    public void serAndDesr1() throws IOException {
        MsgProtos.Msg message = buildMsg();
        //将Protobuf对象，序列化成二进制字节数组
        byte[] data = message.toByteArray();
        //可以用于网络传输,保存到内存或外存
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(data);
        data = outputStream.toByteArray();
        //二进制字节数组,反序列化成Protobuf 对象
        MsgProtos.Msg inMsg = MsgProtos.Msg.parseFrom(data);
        Logger.info("id:=" + inMsg.getId());
        Logger.info("content:=" + inMsg.getContent());
    }

    //第2种方式:序列化 serialization & 反序列化 Deserialization
    @Test
    public void serAndDesr2() throws IOException {
        MsgProtos.Msg message = buildMsg();
        //序列化到二进制流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        //从二进流,反序列化成Protobuf 对象
        MsgProtos.Msg inMsg = MsgProtos.Msg.parseFrom(inputStream);
        Logger.info("id:=" + inMsg.getId());
        Logger.info("content:=" + inMsg.getContent());
    }


    //第3种方式:序列化 serialization & 反序列化 Deserialization
    //带字节长度：[字节长度][字节数据],解决粘包问题
    @Test
    public void serAndDesr3() throws IOException {
        MsgProtos.Msg message = buildMsg();
        //序列化到二进制流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeDelimitedTo(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        //从二进流,反序列化成Protobuf 对象
        MsgProtos.Msg inMsg = MsgProtos.Msg.parseDelimitedFrom(inputStream);
        Logger.info("id:=" + inMsg.getId());
        Logger.info("content:=" + inMsg.getContent());


    }


}
