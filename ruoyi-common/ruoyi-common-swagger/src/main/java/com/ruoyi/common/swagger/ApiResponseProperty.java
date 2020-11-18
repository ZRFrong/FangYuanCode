package com.ruoyi.common.swagger;


public @interface ApiResponseProperty {

    String name();

    String description() default "";

    String type();

}

