package com.crazymakercircle.netty.decoder;

import com.crazymakercircle.util.RandomUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class StringHeaderDecoderTester {
    static String content = "疯狂创客圈：高性能学习社群!";

    /**
     * 字符串解码器的使用实例
     */
    @Test
    public void testStringReplayDecoder() {
        ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
            protected void initChannel(EmbeddedChannel ch) {
                ch.pipeline().addLast(new StringHeaderDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };
        EmbeddedChannel channel = new EmbeddedChannel(i);
        byte[] bytes = content.getBytes(Charset.forName("utf-8"));
        for (int j = 0; j < 100; j++) {
            //1-3之间的随机数
            int random = RandomUtil.randInMod(3);
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(bytes.length * random);
            for (int k = 0; k < random; k++) {
                buf.writeBytes(bytes);
            }
            channel.writeInbound(buf);
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
