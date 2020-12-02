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

    public DropsExceptions(int code, String code1, String message) {
        this.code = code1;
        this.message = message;
    }





}
