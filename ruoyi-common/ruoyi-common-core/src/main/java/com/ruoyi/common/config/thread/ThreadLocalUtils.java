package com.ruoyi.common.config.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 本地线程变量工具类
 * @Author zheng
 * @Date 2021/6/7 15:26
 * @Version 1.0
 */
public class ThreadLocalUtils {

    private final static ThreadLocal<Map<String,Object>> threadContext = new ThreadLocal<Map<String,Object>>();

    public static void put(String key,Object value){
        getContextMap().put(key,value);
    }

    public static Object remove(String key){
        return getContextMap().remove(key);
    }

    public static Object get(String key){
        return getContextMap().get(key);
    }

    public static Map<String,Object> getContextMap() {
        Map<String,Object> threadContextMap = threadContext.get();
        if(threadContextMap==null){
            threadContextMap = new ConcurrentHashMap<>();
            threadContext.set(threadContextMap);
        }
        return threadContextMap;
    }

    public static void clearContext(){
        getContextMap().clear();
    }
}
