package com.crazymakercircle.netty.decoder;

import com.crazymakercircle.util.RandomUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class NettyOpenBoxDecoder {
    public static final int MAGICCODE = 9999;
    public static final int VERSION = 100;
    static String spliter = "\r\n";
    static String spliter2 = "\t";
    static String content = "疯狂创客圈：高性能学习社群!";

    /**
     * LineBasedFrameDecoder 使用实例
     */
    @Test
    public void testLineBasedFrameDecoder() {
        try {
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 0; j < 100; j++) {

                //1-3之间的随机数
                int random = RandomUtil.randInMod(3);
                ByteBuf buf = Unpooled.buffer();
                for (int k = 0; k < random; k++) {
                    buf.writeBytes(content.getBytes("UTF-8"));
                }
                buf.writeBytes(spliter.getBytes("UTF-8"));
                channel.writeInbound(buf);
            }


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * LineBasedFrameDecoder 使用实例
     */
    @Test
    public void testLengthFieldBasedFrameDecoder() {
        try {

            final LengthFieldBasedFrameDecoder spliter =
                    new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4);
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 0; j < 100; j++) {
                //1-3之间的随机数
                int random = RandomUtil.randInMod(3);
                ByteBuf buf = Unpooled.buffer();
                byte[] bytes = content.getBytes("UTF-8");
                buf.writeInt(bytes.length * random);
                for (int k = 0; k < random; k++) {
                    buf.writeBytes(bytes);
                }
                channel.writeInbound(buf);
            }


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * LineBasedFrameDecoder 使用实例
     */
    @Test
    public void testDelimiterBasedFrameDecoder() {
        try {
            final ByteBuf delimiter = Unpooled.copiedBuffer(spliter2.getBytes("UTF-8"));
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(
                            new DelimiterBasedFrameDecoder(1024, true, delimiter));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);
            for (int j = 0; j < 100; j++) {

                //1-3之间的随机数
                int random = RandomUtil.randInMod(3);
                ByteBuf buf = Unpooled.buffer();
                for (int k = 0; k < random; k++) {
                    buf.writeBytes(content.getBytes("UTF-8"));
                }
                buf.writeBytes(spliter2.getBytes("UTF-8"));
                channel.writeInbound(buf);
            }


            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * LengthFieldBasedFrameDecoder 使用实例
     */
    @Test
    public void testLengthFieldBasedFrameDecoder1() {
        try {

            final LengthFieldBasedFrameDecoder spliter =
                    new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4);
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 1; j <= 100; j++) {
                ByteBuf buf = Unpooled.buffer();
                String s = j + "次发送->" + content;
                byte[] bytes = s.getBytes("UTF-8");
                buf.writeInt(bytes.length);
                System.out.println("bytes length = " + bytes.length);
                buf.writeBytes(bytes);
                channel.writeInbound(buf);
            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * LengthFieldBasedFrameDecoder 使用实例
     */
    @Test
    public void testLengthFieldBasedFrameDecoder2() {
        try {

            final LengthFieldBasedFrameDecoder spliter =
                    new LengthFieldBasedFrameDecoder(1024, 0, 4, 2, 6);
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 1; j <= 100; j++) {
                ByteBuf buf = Unpooled.buffer();
                String s = j + "次发送->" + content;
                byte[] bytes = s.getBytes("UTF-8");
                buf.writeInt(bytes.length);
                buf.writeChar(VERSION);
                buf.writeBytes(bytes);
                channel.writeInbound(buf);
            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * LengthFieldBasedFrameDecoder 使用实例 3
     */
    @Test
    public void testLengthFieldBasedFrameDecoder3() {
        try {

            final LengthFieldBasedFrameDecoder spliter =
                    new LengthFieldBasedFrameDecoder(1024, 2, 4, 4, 10);
            ChannelInitializer i = new ChannelInitializer<EmbeddedChannel>() {
                protected void initChannel(EmbeddedChannel ch) {
                    ch.pipeline().addLast(spliter);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };
            EmbeddedChannel channel = new EmbeddedChannel(i);

            for (int j = 1; j <= 100; j++) {
                ByteBuf buf = Unpooled.buffer();
                String s = j + "次发送->" + content;
                byte[] bytes = s.getBytes("UTF-8");
                buf.writeChar(VERSION);
                buf.writeInt(bytes.length);
                buf.writeInt(MAGICCODE);
                buf.writeBytes(bytes);
                channel.writeInbound(buf);
            }

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}
