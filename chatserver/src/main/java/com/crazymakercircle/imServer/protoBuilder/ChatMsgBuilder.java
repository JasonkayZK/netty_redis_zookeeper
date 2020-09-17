package com.crazymakercircle.imServer.protoBuilder;


import com.crazymakercircle.im.common.ProtoInstant;
import com.crazymakercircle.im.common.bean.msg.ProtoMsg;

public class ChatMsgBuilder {

    public static ProtoMsg.Message buildChatResponse(
            long seqId,
            ProtoInstant.ResultCodeEnum en) {
        ProtoMsg.Message.Builder mb = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.MESSAGE_RESPONSE)  //设置消息类型
                .setSequence(seqId);                 //设置应答流水，与请求对应
        ProtoMsg.MessageResponse.Builder rb =
                ProtoMsg.MessageResponse.newBuilder()
                        .setCode(en.getCode())
                        .setInfo(en.getDesc())
                        .setExpose(1);
        mb.setMessageResponse(rb.build());
        return mb.build();
    }


    /**
     * 登录应答 应答消息protobuf
     */
    public static ProtoMsg.Message buildLoginResponce(
            ProtoInstant.ResultCodeEnum en,
            long seqId) {
        ProtoMsg.Message.Builder mb = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.MESSAGE_RESPONSE)  //设置消息类型
                .setSequence(seqId);  //设置应答流水，与请求对应

        ProtoMsg.LoginResponse.Builder rb =
                ProtoMsg.LoginResponse.newBuilder()
                        .setCode(en.getCode())
                        .setInfo(en.getDesc())
                        .setExpose(1);

        mb.setLoginResponse(rb.build());
        return mb.build();
    }


}
