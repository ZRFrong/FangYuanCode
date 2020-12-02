package com.ruoyi.fangyuantcp.abnormal;


/*
* 掉线异常
* */
public class DropsExceptions extends RuntimeException {

//    操作对象
    private final String code;
//    掉线说明
    private final String message ;

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public DropsExceptions( String code, String message) {
        this.code = code;
        this.message = message;
    }





}
