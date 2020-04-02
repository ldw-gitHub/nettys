
package com.framework.model;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Map;

@JsonInclude(value = Include.NON_NULL)
public class ResultInfo<T> {

    public static final int SUCCESS = 200;
    public static final int DATA_NOT_FOUND = 204;
    public static final int FAILURE = 300;
    public static final int FAILURE_OPERATE = 301;//失败，需要弹窗处理，且前端需要其他业务操作
    public static final int ERROR = 500;
    public static final int INVALID_PARAM = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int INVALID_TOKEN = 402;
    public static final int FORBIDOM = 403;
    public static final int NOT_FOUND = 404;
    public static final int REGISTERED = 405;
    public static final int FLUSH = 406;

    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG_FAILURE = "操作失败";
    public static final String MSG_DATA_NOT_FOUND = "查询结果为空";
    public static final String MSG_NOT_FOUND = "找不到请求的资源";
    public static final String MSG_INVALID_PARAM = "请求参数错误";
    public static final String MSG_ERROR = "系统发生异常";
    public static final String MSG_UNAUTHORIZED = "未授权的访问";
    public static final String MSG_INVALID_TOKEN = "身份已过期";
    public static final String MSG_FORBIDOM = "禁止访问";
    public static final String MSG_REGISTERED = "手机号已被注册";
    public static final String KAPTCHA_EXPRRIE = "验证码已过期，请刷新后再试";
    public static final String KAPTCHA_NOT_REAL = "验证码错误";
    public static final String KAPTCHA_IS_NULL = "请填写验证码";

    private Integer code;
    private String message;
    private Long timestamp;
    private T data;
    private Map<String,String> header;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public ResultInfo() {}

    public ResultInfo(Integer code, String message) {
        super();
        this.code = code;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
    }

    public ResultInfo(Integer code, String message, T data) {
        super();
        this.code = code;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.data = data;
    }

    public ResultInfo(Integer code, String message, T data,Map<String,String> header) {
        super();
        this.code = code;
        this.timestamp = System.currentTimeMillis();
        this.message = message;
        this.data = data;
        this.header = header;
    }

    @Override
    public String toString() {
        JSONObject rtJson = new JSONObject();
        rtJson.put("code",code);
        rtJson.put("message",message);
        rtJson.put("timestamp",timestamp);
        rtJson.put("data",data);
        rtJson.put("header",header);
        return rtJson.toString();
    }
}
