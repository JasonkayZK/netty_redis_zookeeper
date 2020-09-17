package com.crazymakercircle.netty.codec;

import com.crazymakercircle.netty.decoder.Byte2IntegerDecoder;
import com.crazymakercircle.netty.encoder.Integer2ByteEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class IntegerDuplexHandler extends CombinedChannelDuplexHandler<
        Byte2IntegerDecoder,
        Integer2ByteEncoder>
{
    public IntegerDuplexHandler() {
        super(new Byte2IntegerDecoder(), new Integer2ByteEncoder());
    }
}
