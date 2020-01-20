package com.framework.util;

import com.framework.model.Action;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:35
 */
@Slf4j
public class ActionMapUtil {

    private static Map<String, Action> map = new HashMap<String, Action>();

    public static boolean invote(String key,String requestMethod, Object... args) throws Exception {
        Action action = map.get(key);
        if (action != null && action.getRequestMethod().equalsIgnoreCase(requestMethod)) {
            Method method = action.getMethod();
            try {
                //返回反射方法的返回值
                method.invoke(action.getObject(), args);
                return false;
            } catch (Exception e) {
                log.error(e.getMessage());
                throw e;
            }
        }

        return true;
    }

    public static void put(String key, Action action) {
        map.put(key, action);
    }


}
