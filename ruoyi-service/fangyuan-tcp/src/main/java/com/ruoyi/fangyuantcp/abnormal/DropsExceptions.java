package com.ruoyi.fangyuantcp.abnormal;


/*
* 掉线异常
* */
public class DropsExceptions extends RuntimeException {

//    操作对象
    private final String code;
//    掉线说明
    private final String message ;

    public DropsExceptions(int code, String code1, String message) {
        this.code = code1;
        this.message = message;
    }



    public String getMessage() {
        return this.toString();
    }

    public String toString() {
        return "系统异常，异常编码：" + this.code;
    }

}
