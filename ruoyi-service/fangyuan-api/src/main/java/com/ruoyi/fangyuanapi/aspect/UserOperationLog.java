package com.ruoyi.fangyuanapi.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserOperationLog {
    /**

     * 业务类型，如是否为批量操作  默认为false
     * @return
     */
    boolean OperationLogType() default false;
}
