package com.framework.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/9 16:00
 */
@Slf4j
public class BinaryWebSocketFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        log.info("服务器接收到二进制消息,消息长度:[{}]", msg.content().capacity());

        //推送消息
        int capacity = msg.content().capacity();
        ByteBuf byteBuf = Unpooled.directBuffer(capacity);
        byteBuf.writeBytes(msg.content());
        TextWebSocketFrameHandler.channelGroup.forEach(ch -> {
            //io.netty.util.IllegalReferenceCountException: refCnt: 0, decrement: 1
            byteBuf.retain();
            log.info("写出文件");
            ch.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
