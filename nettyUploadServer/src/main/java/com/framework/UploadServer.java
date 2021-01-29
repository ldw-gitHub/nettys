package com.framework;

import com.framework.service.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.annotation.Resource;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/20 10:52
 */
@Slf4j
@MapperScan({"com.framework.mapper"})
@SpringBootApplication
public class UploadServer implements CommandLineRunner {
    @Resource
    NettyServer nettyServer;

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(UploadServer.class).web(WebApplicationType.NONE).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("==============================redis=================================");
        nettyServer.start(8081);
    }
}
