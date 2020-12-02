package com.ruoyi.fangyuantcp.abnormal;


/*
* 故障     {心跳包掉线，发送失败}
* */
public class FaultExceptions extends  RuntimeException {
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

    public FaultExceptions(String code, String message) {
        this.code = code;

        this.message = message;
    }
}
