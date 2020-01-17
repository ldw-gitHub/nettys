package com.framework.util;

import com.alibaba.fastjson.JSONObject;
import com.framework.model.BusinessException;
import com.framework.model.ResultInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.netty.buffer.Unpooled.copiedBuffer;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/14 10:14
 */
public class ResponseUtils {

    /**
     * @description:返回正常信息
     * @author: liudawei
     * @date: 2020/1/17 17:56
     * @param: ctx
     * @param: resultInfo
     * @return: void
     */
    public static void responseOK(ChannelHandlerContext ctx, ResultInfo resultInfo) {
        ByteBuf content = copiedBuffer(resultInfo.toString(), CharsetUtil.UTF_8);

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);

        //TODO 请求头还需要完善
        if (content != null) {
            response.headers().set("Content-Type", "text/plain;charset=UTF-8");
            response.headers().set("Content_Length", response.content().readableBytes());
            response.headers().set("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
            response.headers().set("Pragma", "no-cache"); // HTTP 1.0
            response.headers().set("Access-Control-Allow-Origin", "*");
            response.headers().set("Access-Control-Expose-Headers","Authorization");
            response.headers().set("Access-Control-Allow-Headers", "Authorization");
            response.headers().set("Access-Control-Allow-Methods", "*");
            response.headers().set("X-Frame-Options", "SAMEORIGIN");// 解决IFrame拒绝的问题
        }

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * @description:返回异常信息
     * @author: liudawei
     * @date: 2020/1/17 17:55
     * @param: ctx
     * @param: ex
     * @return: void
     */
    public static void responseError(ChannelHandlerContext ctx, Throwable ex) {
        Integer code = 400;
        String msg = "";
        if (ex instanceof BusinessException) {
            code = ((BusinessException) ex).getCode();
            msg = ex.getMessage();
        } else if (ex instanceof AuthenticationException) {
            code = 401;
            msg = "错误的账号或者密码";
        } else if (ex instanceof NullPointerException) {
            msg = "NullPoint Error";
        } else if (ex instanceof IOException) {
            msg = "文件读写异常";
        } else {
            msg = "Internal Server Error";
        }

        ResponseUtils.responseOK(ctx, new ResultInfo(code, msg));
    }



    // /*      * 获取GET方式传递的参数      */
    public static Map<String, Object> getGetParamsFromChannel(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (fullHttpRequest.method() == HttpMethod.GET) {             // 处理get请求
            QueryStringDecoder decoder = new QueryStringDecoder(fullHttpRequest.uri());
            Map<String, List<String>> paramList = decoder.parameters();
            for (Map.Entry<String, List<String>> entry : paramList.entrySet()) {
                params.put(entry.getKey(), entry.getValue().get(0));
            }
            return params;
        } else {
            return null;
        }
    }

    /*      * 获取POST方式传递的参数      */
    public static Map<String, Object> getPostParamsFromChannel(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<String, Object>();
        if (fullHttpRequest.method() == HttpMethod.POST) {             // 处理POST请求
            String strContentType = fullHttpRequest.headers().get("Content-Type").trim();
            if (strContentType.contains("x-www-form-urlencoded")) {
                params = getFormParams(fullHttpRequest);
            } else if (strContentType.contains("application/json")) {
                try {
                    params = getJSONParams(fullHttpRequest);
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            } else {
                return null;
            }
            return params;
        } else {
            return null;
        }
    }

    /*      * 解析from表单数据（Content-Type = x-www-form-urlencoded）      */
    public static Map<String, Object> getFormParams(FullHttpRequest fullHttpRequest) {
        Map<String, Object> params = new HashMap<String, Object>();
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), fullHttpRequest);
        List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();
        for (InterfaceHttpData data : postData) {
            if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                MemoryAttribute attribute = (MemoryAttribute) data;
                params.put(attribute.getName(), attribute.getValue());
            }
        }
        return params;
    }

    /*      * 解析json数据（Content-Type = application/json）      */
    public static Map<String, Object> getJSONParams(FullHttpRequest fullHttpRequest) throws UnsupportedEncodingException {
        Map<String, Object> params = new HashMap<String, Object>();
        ByteBuf content = fullHttpRequest.content();
        byte[] reqContent = new byte[content.readableBytes()];
        content.readBytes(reqContent);
        String strContent = new String(reqContent, "UTF-8");
        JSONObject jsonParams = JSONObject.parseObject(strContent);
        for (Object key : jsonParams.keySet()) {
            params.put(key.toString(), jsonParams.get(key));
        }
        return params;
    }

}
