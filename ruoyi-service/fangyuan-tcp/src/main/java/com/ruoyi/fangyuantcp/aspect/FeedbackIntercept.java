package com.ruoyi.fangyuantcp.aspect;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FeedbackIntercept {


    /*
     * 设备表变动，状态表比对
     * */
    String ChangesState() default "dbEquipment";

}
