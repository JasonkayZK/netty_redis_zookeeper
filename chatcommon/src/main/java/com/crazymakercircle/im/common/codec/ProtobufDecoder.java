package com.crazymakercircle.im.common.codec;

import com.crazymakercircle.im.common.ProtoInstant;
import com.crazymakercircle.im.common.bean.msg.ProtoMsg;
import com.crazymakercircle.im.common.exception.InvalidFrameException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
/**
 * create by 尼恩 @ 疯狂创客圈
 **/


/**
 * create by 尼恩 @ 疯狂创客圈
 * <p>
 * 解码器
 */

@Slf4j
public class ProtobufDecoder extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext ctx,
                          ByteBuf in,
                          List<Object> out) throws Exception {
        // 标记一下当前的readIndex的位置
        in.markReaderIndex();
        // 判断包头长度
        if (in.readableBytes() < 8) {// 不够包头
            return;
        }
        //读取魔数
        short magic = in.readShort();
        if (magic != ProtoInstant.MAGIC_CODE) {
            String error = "客户端口令不对:" + ctx.channel().remoteAddress();
            throw new InvalidFrameException(error);
        }
        //读取版本
        short version = in.readShort();
        // 读取传送过来的消息的长度。
        int length = in.readInt();

        // 长度如果小于0
        if (length < 0) {// 非法数据，关闭连接
            ctx.close();
        }

        if (length > in.readableBytes()) {// 读到的消息体长度如果小于传送过来的消息长度
            // 重置读取位置
            in.resetReaderIndex();
            return;
        }


        byte[] array;
        if (in.hasArray()) {
            //堆缓冲
            ByteBuf slice = in.slice();
            array = slice.array();
        } else {
            //直接缓冲
            array = new byte[length];
            in.readBytes(array, 0, length);
        }

//        if(in.refCnt()>0)
//        {
////            log.debug("释放临时缓冲");
//            in.release();
//        }

        // 字节转成对象
        ProtoMsg.Message outmsg =
                ProtoMsg.Message.parseFrom(array);


        if (outmsg != null) {
            // 获取业务消息
            out.add(outmsg);
        }

    }
}
