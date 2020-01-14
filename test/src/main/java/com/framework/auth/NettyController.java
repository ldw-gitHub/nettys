package com.framework.auth;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface NettyController {
}
