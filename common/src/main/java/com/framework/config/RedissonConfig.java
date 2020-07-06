package com.framework.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setDatabase(1)
                .setAddress("redis://10.0.0.32:6379");

        RedissonClient redissonClient = Redisson.create(config);

        return  redissonClient;
    }
}
