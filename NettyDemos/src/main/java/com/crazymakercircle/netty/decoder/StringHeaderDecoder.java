package com.crazymakercircle.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class StringHeaderDecoder extends ByteToMessageDecoder {    //头是一个整数
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext,
                          ByteBuf buf,
                          List<Object> out) throws Exception {

        //可读大小小于int，头还没读满，return

        if (buf.readableBytes() < 4) {
            return;
        }

        //头已经完整
        //在真正开始从buffer读取数据之前，调用markReaderIndex()设置回滚点
        // 回滚点为 header的readIndex位置
        buf.markReaderIndex();
        int length = buf.readInt();
        //从buffer中读出头的大小，这会使得readIndex前移
        //剩余长度不够body体，reset 读指针
        if (buf.readableBytes() < length) {
            //读指针回滚到header的readIndex位置处，没进行状态的保存
            buf.resetReaderIndex();
            return;
        }
        // 读取数据，编码成字符串
        byte[] inBytes = new byte[length];
        buf.readBytes(inBytes, 0, length);
        out.add(new String(inBytes, "UTF-8"));
    }
}