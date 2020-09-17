package com.crazymakercircle.netty.encoder;

import com.crazymakercircle.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class Integer2ByteEncoder extends MessageToByteEncoder<Integer> {
    @Override
    public void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out)
            throws Exception {
        out.writeInt(msg);
        Logger.info("encoder Integer = " + msg);
    }
}
