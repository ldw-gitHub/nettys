package com.framework.util;

import com.framework.model.Action;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: liudawei
 * @date: 2020/1/13 15:35
 */
public class ActionMapUtil {

    private static Map<String, Action> map = new HashMap<String, Action>();

    public static Object invote(String key,String requestMethod, Object... args) throws Exception {
        Action action = map.get(key);
        if (action != null && action.getRequestMethod().equalsIgnoreCase(requestMethod)) {
            Method method = action.getMethod();
            try {
                return method.invoke(action.getObject(), args);
            } catch (Exception e) {
                throw e;
            }
        }

        return null;
    }

    public static void put(String key, Action action) {
        map.put(key, action);
    }


}
