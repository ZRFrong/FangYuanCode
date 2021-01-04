package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class DbQrCodeVo {

    /*是否第一次绑定*/
    @ApiModelProperty(value = "是否第一次绑定")
    public  boolean FirstBind;

   /*二维码表*/
   @ApiModelProperty(value = "二维码表")
    public  DbQrCode dbQrCode;

   /*操作集类*/
   @ApiModelProperty(value = "操作集类所有")
   public List<OperatePojo> operatePojo;
    /*操作集类*/
    @ApiModelProperty(value = "操作集类选中")
    public List<OperatePojo> operatePojoSelected;

    @Override
    public String toString() {
        return "DbQrCodeVo{" +
                "FirstBind=" + FirstBind +
                ", dbQrCode=" + dbQrCode +
                ", operatePojo=" + operatePojo +
                ", operatePojoSelected=" + operatePojoSelected +
                '}';
    }

    public boolean isFirstBind() {
        return FirstBind;
    }

    public DbQrCode getDbQrCode() {
        return dbQrCode;
    }

    public List<OperatePojo> getOperatePojoSelected() {
        return operatePojoSelected;
    }

    public void setOperatePojoSelected(List<OperatePojo> operatePojoSelected) {
        this.operatePojoSelected = operatePojoSelected;
    }

    public DbQrCodeVo() {
    }


    public void setFirstBind(boolean firstBind) {
        FirstBind = firstBind;
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
