package com.framework;

import com.framework.init.NettyServerInitializer;
import com.framework.server.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:12
 */
@SpringBootApplication
public class AppServer implements CommandLineRunner {

    @Resource
    NettyServer nettyServer;

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(AppServer.class).web(false).run(args);
    }


    @Override
    public void run(String... args) throws Exception {
        nettyServer.start(8080);
    }
}
