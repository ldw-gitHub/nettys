package com.framework.model;

import java.lang.reflect.Method;

/**
 * @description  路径映射
 * @author: liudawei
 * @date: 2020/1/13 15:37
 */
public class Action {

    private Method method;

    private Object object;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
