package com.framework;

import com.framework.config.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/1 18:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisSentinalServer {

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void testRedis(){
        redisUtils.set("age","11");

        Object o = redisUtils.get("age");
        System.out.println(o.toString());
    }

}
