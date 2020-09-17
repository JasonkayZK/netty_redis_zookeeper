package com.crazymakercircle.imServer.processer;


import com.crazymakercircle.im.common.bean.msg.ProtoMsg;
import com.crazymakercircle.imServer.server.ServerSession;

/**
 * 操作类
 */
public interface ServerProcesser {

    ProtoMsg.HeadType type();

    boolean action(ServerSession ch, ProtoMsg.Message proto);

}
