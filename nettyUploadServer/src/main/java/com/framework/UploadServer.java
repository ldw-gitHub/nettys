package com.framework;

import com.framework.server.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.Resource;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/20 10:52
 */
@SpringBootApplication
public class UploadServer implements CommandLineRunner {
    @Resource
    NettyServer nettyServer;

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(UploadServer.class).web(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start(8081);
    }
}
