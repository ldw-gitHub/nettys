package com.framework.init;

import com.framework.auth.ActionMap;
import com.framework.model.Action;
import com.framework.util.ActionMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description  将自定义ActionMap注解的方法放到map中，匹配拦截器
 * @author: liudawei
 * @date: 2020/1/14 9:24
 */
@Component
@Slf4j
public class ActionBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            ActionMap actionMap = method.getAnnotation(ActionMap.class);
            if (actionMap != null) {
                Action action = new Action();
                action.setMethod(method);
                action.setObject(bean);
                action.setRequestMethod(actionMap.requestMethod());
                log.info("url --- " + actionMap.key() + " , method --- " + actionMap.requestMethod());
                ActionMapUtil.put(actionMap.key(), action);
            }
        }
        return bean;
    }
}
