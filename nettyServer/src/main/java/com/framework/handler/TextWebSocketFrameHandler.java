package com.framework.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 20190322
 */
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 保存channel对象
     */
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("收到消息 : " + textWebSocketFrame.text());

        //将消息推送到rabbitmq

        Channel channel = channelHandlerContext.channel();
        //推送消息
        channelGroup.forEach(ch -> {
            if (channel != ch) {
//                channelHandlerContext.fireChannelRead(textWebSocketFrame);
                ch.writeAndFlush(new TextWebSocketFrame("【" + channel.remoteAddress() + "】" + textWebSocketFrame.text() + "\n"));
            } else {
                ch.writeAndFlush(new TextWebSocketFrame("【自己】" + textWebSocketFrame.text() + "\n"));
            }
        });

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("handlerAdded : " + channel.id().asLongText());

        channelGroup.add(channel);
        channelGroup.forEach(ch -> {
            ch.writeAndFlush(new TextWebSocketFrame("欢迎【" + channel.remoteAddress() + "加入群聊】" + "\n"));
        });
        System.out.println("channelGroup.size() : " + channelGroup.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved : " + ctx.channel().id().asLongText());

        System.out.println("channelGroup.size() : " + channelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }

    public void doSendFramemarkTask() {
        // 计算执行周期为1分钟
        long period = 60 * 1000;
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        // 从现在开始delay毫秒之后，每隔一天执行一次，转换为毫秒
        service.scheduleAtFixedRate(() -> {
            System.out.println("----------------------定时推送----------------------");
            channelGroup.forEach(ch -> {
                ch.writeAndFlush(new TextWebSocketFrame("【定时推送】服务器时间： " + LocalDateTime.now()));
            });
        }, 0, period, TimeUnit.MILLISECONDS);
    }

}
