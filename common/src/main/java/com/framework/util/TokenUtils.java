package com.framework.util;

import com.framework.config.ProjectConfig;
import com.framework.config.RedisUtils;
import com.framework.constant.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: liudawei
 * @date: 2020/4/2 10:08
 */
@Component
public class TokenUtils {

    @Autowired
    ProjectConfig projectConfig;
    @Autowired
    RedisUtils redisUtils;

    /**
     * 请求信息头部(身份验证信息)
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * Authorization头部
     */
    public static final String BEARER = "Bearer ";

    /**
     * 生成token,该token长度不一致,如需一致,可自行MD5或者其它方式加密一下
     * 该方式的token只存在磁盘上,如果项目是分布式,最好用redis存储
     *
     * @param str: 该字符串可自定义,在校验token时要保持一致
     * @return
     */
    public String getToken(String str) {
        str = DigestUtils.md5DigestAsHex(str.getBytes());
        String token = TokenEncryptUtils.encoded(getCurrentTime() + "," + str, projectConfig.getTokenSecurt());
        return BEARER + token;
    }

    /**
     * @description:  对key时间设置
     * @author: liudawei
     * @date: 2020/4/2 11:00
     * @param: token
     * @return: void
     */
    public void refreshToken(String token){
        redisUtils.setTTL(RedisKey.ADMIN_TOKEN + token.replace(BEARER,""),projectConfig.getTokenTtl());
    }

    /**
     * @description: 判断token是否存在
     * @author: liudawei
     * @date: 2020/4/2 14:25
     * @param: token
     * @return: boolean
     */
    public boolean existToken(String token){
        return redisUtils.exists(RedisKey.ADMIN_TOKEN +token.replace(BEARER,""));
    }

    /**
     * @description: token删除
     * @author: liudawei
     * @date: 2020/4/2 14:26
     * @param: token
     * @return: void
     */
    public void removeToken(String token){
        redisUtils.remove(RedisKey.ADMIN_TOKEN + token.replace(BEARER,""));
    }

    /**
     * @description: 存储token和用户信息
     * @author: liudawei
     * @date: 2020/4/2 11:04
     * @param: token
     * @param: userInfo
     * @return: void
     */
    public void setTokenAndUserInfo(String token,Map<String,Object> userInfo){
        redisUtils.hset(RedisKey.ADMIN_TOKEN + token.replace(BEARER,""),userInfo,projectConfig.getTokenTtl());
    }


    /**
     * 获取当前时间戳（10位整数）
     */
    public static int getCurrentTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }


}
