package com.framework;

import com.framework.service.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:12
 */
@MapperScan({"com.framework.mapper"})
@SpringBootApplication
public class AppServer implements CommandLineRunner {

    @Autowired
    NettyServer nettyServer;

    public static void main(String[] args) {
        new SpringApplicationBuilder(AppServer.class).web(WebApplicationType.NONE).run(args);
    }
    //同时启用springmvc和netty
/*    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(AppServer.class, args);
        NettyServer nettyServer = (NettyServer)run.getBean("nettyServer");
        nettyServer.start(8090);
    }*/


    @Override
    public void run(String... args) throws Exception {
        nettyServer.start(8080);
    }
}
