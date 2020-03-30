package com.framework;

import com.framework.service.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

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
