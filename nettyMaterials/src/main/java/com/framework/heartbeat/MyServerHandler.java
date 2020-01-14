package com.framework.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                case WRITER_IDLE:
                    eventType = "写空闲";
                case ALL_IDLE:
                    eventType = "读写空闲";

            }

            System.out.println(ctx.channel().remoteAddress() + "超时事件 ： " + eventType);
            ctx.channel().close();
        }
    }
}
