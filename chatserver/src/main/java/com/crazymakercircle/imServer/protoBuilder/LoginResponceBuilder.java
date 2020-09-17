package com.crazymakercircle.imServer.protoBuilder;

import com.crazymakercircle.im.common.ProtoInstant;
import com.crazymakercircle.im.common.bean.msg.ProtoMsg;
import org.springframework.stereotype.Service;

@Service("LoginResponceBuilder")
public class LoginResponceBuilder {

    /**
     * 登录应答 应答消息protobuf
     */
    public ProtoMsg.Message loginResponce(
            ProtoInstant.ResultCodeEnum en, long seqId, String sessionId) {
        ProtoMsg.Message.Builder mb = ProtoMsg.Message.newBuilder()
                .setType(ProtoMsg.HeadType.LOGIN_RESPONSE)  //设置消息类型
                .setSequence(seqId)
                .setSessionId(sessionId);  //设置应答流水，与请求对应

        ProtoMsg.LoginResponse.Builder b = ProtoMsg.LoginResponse.newBuilder()
                .setCode(en.getCode())
                .setInfo(en.getDesc())
                .setExpose(1);

        mb.setLoginResponse(b.build());
        return mb.build();
    }


}
