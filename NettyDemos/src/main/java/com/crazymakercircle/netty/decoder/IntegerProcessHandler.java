package com.crazymakercircle.netty.decoder;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/

import com.crazymakercircle.util.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class IntegerProcessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Integer integer = (Integer) msg;
        Logger.info("打印出一个整数: " + integer);

    }
}