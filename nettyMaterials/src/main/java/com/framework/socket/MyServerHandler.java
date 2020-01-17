package com.framework.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " , " + msg);
        //channel writeAndFlush 会从channelPipeline的末尾开始流动，历经每个处理器
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());
        //ChannelHandlerContext writeAndFlush 会从channelPipeline中的下一个ChannelHandler开始流动
//        ctx.writeAndFlush("from server:" + UUID.randomUUID());

        /**
         * 1、channelHandlerContext与ChannelHandler之间的绑定关系是永远不会发生改变的，因此对其进行缓存是没有任何问题的
         * 2、对于与Channel的同名方法来说，ChannelHandlerContext的方法将会产生更短的事件流，所以我们应该在可能的情况下利用这个特性来提升应用性能
         * 3、OIO与NIO
         */
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
