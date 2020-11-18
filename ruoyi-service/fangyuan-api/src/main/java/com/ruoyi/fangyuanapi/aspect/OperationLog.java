package com.ruoyi.fangyuanapi.aspect;

import java.lang.annotation.*;

/*
* 自定义接口操作记录
* */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {


    /**

     * 业务类型，如是否为批量操作  默认为false
     * @return
     */
    boolean OperationLogType() default false;

    /*
    * 具体操作名称   设备操作  还是土地操作
    * */
    String OperationLogNmae() default "0";

    /*
     * 来源  微信，小程序，web
     * */
    String OperationLogSource() default "0";


}
