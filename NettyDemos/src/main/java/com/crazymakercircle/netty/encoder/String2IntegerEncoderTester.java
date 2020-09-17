package com.crazymakercircle.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class String2IntegerEncoderTester {

    /**
     * 测试字符串到整数编码器
     */
    @Test
    public void testStringToIntergerDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new Integer2ByteEncoder());
                ch.pipeline().addLast(new String2IntegerEncoder());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(i);

        for (int j = 0; j < 100; j++) {
            String s = "i am " + j;
            channel.write(s);
        }
        channel.flush();
        ByteBuf buf = (ByteBuf) channel.readOutbound();
        while (null != buf) {
            System.out.println("o = " + buf.readInt());
            buf = (ByteBuf) channel.readOutbound();
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
