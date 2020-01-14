package com.framework.auth;

import java.lang.annotation.*;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ActionMap {
    String key();
}
