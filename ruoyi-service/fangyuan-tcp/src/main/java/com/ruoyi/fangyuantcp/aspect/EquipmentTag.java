package com.ruoyi.fangyuantcp.aspect;

import java.lang.annotation.*;

import static com.ruoyi.fangyuantcp.aspect.EquipmentTagAspect.DEFAULT_TYPE;


/**
 * 设备tag数据
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
@Documented
public @interface EquipmentTag {

    String type() default DEFAULT_TYPE ;
}
