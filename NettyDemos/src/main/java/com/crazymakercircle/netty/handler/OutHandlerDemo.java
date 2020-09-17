package com.crazymakercircle.netty.handler;

import com.crazymakercircle.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class OutHandlerDemo extends ChannelOutboundHandlerAdapter {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Logger.info("被调用：handlerAdded()");
        super.handlerAdded(ctx);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Logger.info("被调用： handlerRemoved()");
        super.handlerRemoved(ctx);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        Logger.info("被调用： bind()");
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        Logger.info("被调用： connect()");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        Logger.info("被调用： disconnect()");
        super.disconnect(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        Logger.info("被调用： read()");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Logger.info("被调用： write()");
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        Logger.info("被调用： flush()");
        super.flush(ctx);
    }
}