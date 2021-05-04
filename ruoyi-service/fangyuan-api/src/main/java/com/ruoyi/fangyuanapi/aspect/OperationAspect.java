package com.ruoyi.fangyuanapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.aspect.OperationAspect.java
 * @Description 操作切面 做记录
 * @createTime 2021年05月02日 22:30:00
 */
@Aspect
@Component
@Slf4j
public class OperationAspect {


    @Around("@annotation(operation)")
    public Object processAuthority(ProceedingJoinPoint point, Operation operation){
        //参数
        Map<String, Object> map = getMap(point);
        return null;
    }

    protected static Map<String, Object> getMap(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //参数名称
        String[] parameterNames = methodSignature.getParameterNames();
//        参数值
        Object[] args = point.getArgs();

        Map<String, Object> stringObjectHashMap = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            stringObjectHashMap.put(parameterNames[i], args[i]);
        }

        return stringObjectHashMap;
    }
}
