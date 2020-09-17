package com.crazymakercircle.netty.bytebuf;

import com.crazymakercircle.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

import static com.crazymakercircle.netty.bytebuf.PrintAttribute.print;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class WriteReadTest {

    @Test
    public void testWriteRead() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        print("动作：分配 ByteBuf(9, 100)", buffer);
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("动作：写入4个字节 (1,2,3,4)", buffer);
        Logger.info("start==========:get==========");
        getByteBuf(buffer);
        print("动作：取数据 ByteBuf", buffer);
        Logger.info("start==========:read==========");
        readByteBuf(buffer);
        print("动作：读完 ByteBuf", buffer);
    }

    //读取一个字节
    private void readByteBuf(ByteBuf buffer) {
        while (buffer.isReadable()) {
            Logger.info("读取一个字节:" + buffer.readByte());
        }
    }


    //读取一个字节，不改变指针
    private void getByteBuf(ByteBuf buffer) {
        for (int i = 0; i < buffer.readableBytes(); i++) {
            Logger.info("读取一个字节:" + buffer.getByte(i));
        }
    }

}
