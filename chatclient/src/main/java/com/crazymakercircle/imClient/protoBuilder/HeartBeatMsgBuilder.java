/**
 * Created by 尼恩 at 疯狂创客圈
 */

package com.crazymakercircle.imClient.protoBuilder;

import com.crazymakercircle.im.common.bean.User;
import com.crazymakercircle.im.common.bean.msg.ProtoMsg;
import com.crazymakercircle.imClient.client.ClientSession;


/**
 * 心跳消息Builder
 */
public class HeartBeatMsgBuilder extends BaseBuilder {
    private final User user;

    public HeartBeatMsgBuilder(User user, ClientSession session) {
        super(ProtoMsg.HeadType.HEART_BEAT, session);
        this.user = user;
    }

    public ProtoMsg.Message buildMsg() {
        ProtoMsg.Message message = buildCommon(-1);
        ProtoMsg.MessageHeartBeat.Builder lb =
                ProtoMsg.MessageHeartBeat.newBuilder()
                        .setSeq(0)
                        .setJson("{\"from\":\"client\"}")
                        .setUid(user.getUid());
        return message.toBuilder().setHeartBeat(lb).build();
    }


}


