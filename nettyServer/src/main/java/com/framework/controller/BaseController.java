package com.framework.controller;

import com.framework.config.RedisUtils;
import com.framework.model.base.UserInfo;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @description 可以集中公用登入或配置信息
 * @author: liudawei
 * @date: 2020/4/2 14:32
 */
@Slf4j
public class BaseController {

    @Autowired
    RedisUtils redisUtils;

    public UserInfo getUserInfo(String token){
        Map<String, Object> map = redisUtils.hgetAll(token);
        UserInfo userInfo = new UserInfo();
        return userInfo;
    }


}
