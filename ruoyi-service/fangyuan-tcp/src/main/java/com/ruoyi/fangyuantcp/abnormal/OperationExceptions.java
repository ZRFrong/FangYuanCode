package com.ruoyi.fangyuantcp.abnormal;

/*
* 操作返回异常   ---暂无{操作完成之后是否返回}
* */
public class OperationExceptions extends RuntimeException {
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

    public OperationExceptions(String code, String message) {
        this.code = code;

        this.message = message;
    }
}
