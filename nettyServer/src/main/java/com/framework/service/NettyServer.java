package com.framework.service;

import com.framework.handler.TextWebSocketFrameHandler;
import com.framework.init.NettyServerInitializer;
import com.framework.util.TokenUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:12
 */
@Component
@Slf4j
public class NettyServer {

    private int port = 8899;

    @Autowired
    TokenUtils tokenUtils;

    public void run() throws Exception {
        TextWebSocketFrameHandler textWebSocketFrameHandler = new TextWebSocketFrameHandler();
        textWebSocketFrameHandler.doSendFramemarkTask();
        //事件循环组
        //获取连接 accept
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理连接 read and send
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //子处理化拦截器
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new NettyServerInitializer(tokenUtils));

            ChannelFuture sync = serverBootstrap.bind(port).sync();
            log.info("netty服务已启动：" + port);
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public void start (int port) throws Exception{
        this.port = port;
        this.run();
    }

}
