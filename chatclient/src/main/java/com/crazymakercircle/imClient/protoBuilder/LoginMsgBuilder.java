/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.crazymakercircle.imClient.protoBuilder;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.im.common.bean.msg.ProtoMsg;
import com.crazymakercircle.imClient.client.ClientSession;


/**
 * 登陆消息Builder
 */
public class LoginMsgBuilder extends BaseBuilder {
    private final User user;

    public LoginMsgBuilder(User user, ClientSession session) {
        super(ProtoMsg.HeadType.LOGIN_REQUEST, session);
        this.user = user;
    }

    public ProtoMsg.Message build() {
        ProtoMsg.Message message = buildCommon(-1);
        ProtoMsg.LoginRequest.Builder lb =
                ProtoMsg.LoginRequest.newBuilder()
                        .setDeviceId(user.getDevId())
                        .setPlatform(user.getPlatform().ordinal())
                        .setToken(user.getToken())
                        .setUid(user.getUid());
        return message.toBuilder().setLoginRequest(lb).build();
    }

    public static ProtoMsg.Message buildLoginMsg(
            User user, ClientSession session) {
        LoginMsgBuilder builder =
                new LoginMsgBuilder(user, session);
        return builder.build();

    }
}


