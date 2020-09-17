package com.crazymakercircle.netty.bytebuf;

import com.crazymakercircle.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class ReferenceTest {

    @Test
    public  void testRef()
    {

        ByteBuf buffer  = ByteBufAllocator.DEFAULT.buffer();
        Logger.info("after create:"+buffer.refCnt());
        buffer.retain();
        Logger.info("after retain:"+buffer.refCnt());
        buffer.release();
        Logger.info("after release:"+buffer.refCnt());
        buffer.release();
        Logger.info("after release:"+buffer.refCnt());
        //错误:refCnt: 0,不能再retain
        buffer.retain();
        Logger.info("after retain:"+buffer.refCnt());
    }
}
