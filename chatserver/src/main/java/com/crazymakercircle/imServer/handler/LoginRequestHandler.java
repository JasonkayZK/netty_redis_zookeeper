package com.crazymakercircle.imServer.handler;

import com.crazymakercircle.cocurrent.CallbackTask;
import com.crazymakercircle.cocurrent.CallbackTaskScheduler;
import com.crazymakercircle.im.common.bean.msg.ProtoMsg;
import com.crazymakercircle.imServer.processer.LoginProcesser;
import com.crazymakercircle.imServer.server.ServerSession;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LoginRequestHandler")
@ChannelHandler.Sharable
public class LoginRequestHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    LoginProcesser loginProcesser;

    /**
     * 收到消息
     */
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (null == msg
                || !(msg instanceof ProtoMsg.Message)) {
            super.channelRead(ctx, msg);
            return;
        }

        ProtoMsg.Message pkg = (ProtoMsg.Message) msg;

        //取得请求类型
        ProtoMsg.HeadType headType = pkg.getType();

        if (!headType.equals(loginProcesser.type())) {
            super.channelRead(ctx, msg);
            return;
        }


        ServerSession session = new ServerSession(ctx.channel());

        //异步任务，处理登录的逻辑
        CallbackTaskScheduler.add(new CallbackTask<Boolean>() {
            @Override
            public Boolean execute() throws Exception {
                boolean r = loginProcesser.action(session, pkg);
                return r;
            }

            //异步任务返回
            @Override
            public void onBack(Boolean r) {
                if (r) {
                    ctx.pipeline().remove(LoginRequestHandler.this);
                    log.info("登录成功:" + session.getUser());

                } else {
                    ServerSession.closeSession(ctx);
                    log.info("登录失败:" + session.getUser());

                }

            }
            //异步任务异常

            @Override
            public void onException(Throwable t) {
                ServerSession.closeSession(ctx);
                log.info("登录失败:" + session.getUser());

            }
        });

    }


}
