package com.ruoyi.fangyuanapi.aspect;

import java.lang.annotation.*;

/**
 * @author ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.aspect.Operation.java
 * @Description
 * @createTime 2021年05月02日 22:27:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operation {

    /** 是否为单项 还是批量 */
    boolean OperationLogType() default false;


}
