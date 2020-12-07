package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DbQrCodeVo {

    /*是否第一次绑定*/
    @ApiModelProperty(value = "是否第一次绑定")
    public  boolean isFirstBind;

   /*二维码表*/
   @ApiModelProperty(value = "二维码表")
    public  DbQrCode dbQrCode;

   /*操作集类*/
   @ApiModelProperty(value = "操作集类")
   public List<OperatePojo> operatePojo;

    public DbQrCodeVo() {
    }

    @Override
    public String toString() {
        return "DbQrCodeVo{" +
                "isFirstBind=" + isFirstBind +
                ", dbQrCode=" + dbQrCode +
                ", operatePojo=" + operatePojo +
                '}';
    }

    public boolean isFirstBind() {
        return isFirstBind;
    }

    public void setFirstBind(boolean firstBind) {
        isFirstBind = firstBind;
    }

    public DbQrCode getDbQrCode() {
        return dbQrCode;
    }

    public void setDbQrCode(DbQrCode dbQrCode) {
        this.dbQrCode = dbQrCode;
    }


    public List<OperatePojo> getOperatePojo() {
        return operatePojo;
    }

    public void setOperatePojo(List<OperatePojo> operatePojo) {
        this.operatePojo = operatePojo;
    }
}
