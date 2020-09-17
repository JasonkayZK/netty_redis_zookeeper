package com.crazymakercircle.imServer.server;

import com.crazymakercircle.im.common.codec.ProtobufDecoder;
import com.crazymakercircle.im.common.codec.ProtobufEncoder;
import com.crazymakercircle.imServer.handler.ChatRedirectHandler;
import com.crazymakercircle.imServer.handler.ServerExceptionHandler;
import com.crazymakercircle.imServer.handler.HeartBeatServerHandler;
import com.crazymakercircle.imServer.handler.LoginRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

@Data
@Slf4j
@Service("ChatServer")
public class ChatServer {
    // 服务器端口
    @Value("${server.port}")
    private int port;
    // 通过nio方式来接收连接和处理连接
    private EventLoopGroup bg =
            new NioEventLoopGroup();
    private EventLoopGroup wg =
            new NioEventLoopGroup();

    // 启动引导器
    private ServerBootstrap b =
            new ServerBootstrap();
    @Autowired
    private LoginRequestHandler loginRequestHandler;

    @Autowired
    private ServerExceptionHandler serverExceptionHandler;

    @Autowired
    private ChatRedirectHandler chatRedirectHandler;

    public void run() {
        try {   //1 设置reactor 线程
            b.group(bg, wg);
            //2 设置nio类型的channel
            b.channel(NioServerSocketChannel.class);
            //3 设置监听端口
            b.localAddress(new InetSocketAddress(port));
            //4 设置通道选项
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.ALLOCATOR,
                    PooledByteBufAllocator.DEFAULT);

            //5 装配流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时会创建一个channel
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 管理pipeline中的Handler
                    ch.pipeline().addLast(new ProtobufDecoder());
                    ch.pipeline().addLast(new ProtobufEncoder());
                    ch.pipeline().addLast(new HeartBeatServerHandler());
                    // 在流水线中添加handler来处理登录,登录后删除
                    ch.pipeline().addLast(loginRequestHandler);
                    ch.pipeline().addLast(chatRedirectHandler);
                    ch.pipeline().addLast(serverExceptionHandler);
                }
            });
            // 6 开始绑定server
            // 通过调用sync同步方法阻塞直到绑定成功

            ChannelFuture channelFuture = b.bind().sync();
            log.info(
                    "疯狂创客圈 CrazyIM 服务启动, 端口 " +
                            channelFuture.channel().localAddress());
            // 7 监听通道关闭事件
            // 应用程序会一直等待，直到channel关闭
            ChannelFuture closeFuture =
                    channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8 优雅关闭EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            wg.shutdownGracefully();
            bg.shutdownGracefully();
        }

    }

}
