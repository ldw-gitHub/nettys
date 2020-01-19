package com.framework.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description  解码器
 * @author: liudawei
 * @date: 2020/1/19 15:00
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("decode invoked");
        System.out.println(in.readableBytes());//可读数

        if(in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
