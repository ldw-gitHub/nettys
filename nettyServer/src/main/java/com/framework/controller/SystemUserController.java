package com.framework.controller;

import com.alibaba.fastjson.JSONObject;
import com.framework.auth.ActionMap;
import com.framework.auth.NettyController;
import com.framework.model.BusinessException;
import com.framework.model.ResultInfo;
import com.framework.model.SystemUserModel;
import com.framework.service.base.SystemUserService;
import com.framework.util.IPUtils;
import com.framework.util.ResponseUtils;
import com.framework.util.TokenUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:47
 */
@Slf4j
@NettyController
public class SystemUserController {

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    TokenUtils tokenUtils;

    /**
     * @description:  用户登入接口，账户密码验证，token生成
     * @author: liudawei
     * @date: 2020/4/1 10:54
     * @param: ctx
     * @param: params
     * @return: void
     */
    @ActionMap(key = "/login", requestMethod = "Post")
    public void login(ChannelHandlerContext ctx, JSONObject params) throws Exception {
        String username = params.getString("username");
        String password = params.getString("password");

        if (StringUtils.isBlank(username)) {
            ResponseUtils.responseError(ctx, new BusinessException(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM));
        }

        if (StringUtils.isBlank(password)) {
            ResponseUtils.responseError(ctx, new BusinessException(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM));
        }


        SystemUserModel model = systemUserService.queryModelByUsername(username);
        if(model == null){
            ResponseUtils.responseError(ctx, new BusinessException(ResultInfo.FAILURE, "账户不存在！"));
        }

        if(!model.getPassword().equals(password)){
            ResponseUtils.responseError(ctx, new BusinessException(ResultInfo.FAILURE, "密码错误！"));
        }

        String token = tokenUtils.getToken(username + password);

        //返回头信息
        Map<String,String> header = new HashMap<>();
        header.put(TokenUtils.AUTHORIZATION,token);

        //token关联用户信息
        Map<String,Object> redisUserMap = new HashMap<>();
        redisUserMap.put("token", token);
//        redisUserMap.put("loginIp", IPUtils.getIpAddr(req));
        redisUserMap.put("loginTime", System.currentTimeMillis()+"");
        redisUserMap.put("username",model.getUsername());
        redisUserMap.put("account",model.getAccount());
        redisUserMap.put("roleId",model.getRoleId() == null ? "":model.getRoleId());

        tokenUtils.setTokenAndUserInfo(token,redisUserMap);

        ResultInfo resultInfo = new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, redisUserMap,header);
        ResponseUtils.responseOK(ctx, resultInfo);
    }

    /**
     * @description:  登出，token删除
     * @author: liudawei
     * @date: 2020/4/1 10:55
     * @param: ctx
     * @param: params
     * @return: void
     */
    @ActionMap(key = "/logout", requestMethod = "get")
    public void loginout(ChannelHandlerContext ctx, JSONObject params) {
        tokenUtils.removeToken(params.getString("token"));
        ResponseUtils.responseOK(ctx, new ResultInfo(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS));
    }
}
