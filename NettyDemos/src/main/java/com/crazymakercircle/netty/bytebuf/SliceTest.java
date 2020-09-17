package com.crazymakercircle.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

import static com.crazymakercircle.netty.bytebuf.PrintAttribute.print;


public class SliceTest {
    @Test
    public  void testSlice() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("动作：分配 ByteBuf(9, 100)", buffer);
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("动作：写入4个字节 (1,2,3,4)", buffer);
        ByteBuf slice = buffer.slice();
        print("动作：切片 slice", slice);
    }

}