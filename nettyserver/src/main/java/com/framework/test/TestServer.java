package com.framework.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {

        public static void main(String[] args) throws Exception {
            //事件循环组
            EventLoopGroup bossGroup = new NioEventLoopGroup(); //获取连接
            EventLoopGroup workerGroup = new NioEventLoopGroup();//处理连接
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                        childHandler(new TestServerInitializer());//子处理化拦截器

                ChannelFuture sync = serverBootstrap.bind(8899).sync();
                sync.channel().closeFuture().sync();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

        }
}
