package com.framework;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @description
 * @author: liudawei
 * @date: 2020/6/1 18:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AppTestServer {

    @Resource
    RedissonClient redissonClient;

    @Test
    public void testRedisLock(){
        System.out.println("1");
        RLock lock = redissonClient.getLock("1");

        lock.unlock();
    }
}
