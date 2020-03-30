package com.framework.client;

import com.framework.model.FileUploadEntity;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.InetAddress;

/**
 * @description
 * @author: liudawei
 * @date: 2020/3/30 9:48
 */
@Slf4j
public class ClientServer {

    public static void main(String[] args) throws Exception {
        //客户端只创建一个事件循环处理器
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();

            FileUploadEntity file = new FileUploadEntity();
            File files = new File("D:\\chomeDown\\apache-tomcat-7.0.94.zip");
            file.setFile(files);
            file.setFile_md5(files.getName());

            //注意这里和服务端的区别，服务端：NioServerSocketChannel,客户端：NioSocketChannel
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(
                                    new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                            ch.pipeline().addLast(new FileUploadClientHandler(file));
                        }
                    });
            ChannelFuture f = b.connect("localhost", 8081).sync(); //连接服务端
            f.channel().closeFuture().sync();//关闭
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            group.shutdownGracefully();
        }
    }
}
