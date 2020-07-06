package com.framework;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description
 * @author: liudawei
 * @date: 2020/7/3 14:34
 */
@EnableAdminServer
@SpringBootApplication
public class Actuator {

  public static void main(String[] args) {
    SpringApplication.run(Actuator.class, args);
  }
}
