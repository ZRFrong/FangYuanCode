package com.ruoyi.system.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class OperateWeChatVo {

    public OperateWeChatVo() {
    }

    public OperateWeChatVo(Long dbLandId, String nickName, int isBound, int isUnusual, String unusualText, List<DbEquipmentVo> dbEquipmentVos) {
        this.dbLandId = dbLandId;
        this.nickName = nickName;
        this.isBound = isBound;
        this.isUnusual = isUnusual;
        this.unusualText = unusualText;
        this.dbEquipmentVos = dbEquipmentVos;
    }

    public Long getDbLandId() {

        return dbLandId;
    }

    public void setDbLandId(Long dbLandId) {
        this.dbLandId = dbLandId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getIsBound() {
        return isBound;
    }

    public void setIsBound(int isBound) {
        this.isBound = isBound;
    }

    public int getIsUnusual() {
        return isUnusual;
    }

    public void setIsUnusual(int isUnusual) {
        this.isUnusual = isUnusual;
    }

    public String getUnusualText() {
        return unusualText;
    }

    public void setUnusualText(String unusualText) {
        this.unusualText = unusualText;
    }

    public List<DbEquipmentVo> getDbEquipmentVos() {
        return dbEquipmentVos;
    }

    public void setDbEquipmentVos(List<DbEquipmentVo> dbEquipmentVos) {
        this.dbEquipmentVos = dbEquipmentVos;
    }

    /*
     * 土地id
     * */
    @ApiModelProperty(value = "土地id")
    public Long dbLandId;

    /*
     *  土地昵称
     * */
    @ApiModelProperty(value = "土地昵称")
    public String nickName;

    /*
     * 是否绑定设备
     * */
    @ApiModelProperty(value = "是否绑定设备")
    public int isBound;

    /*
     * 是否异常
     * */
    @ApiModelProperty(value = "是否异常(0正常，1切换手动，2设备故障，3设备到期)")
    public int isUnusual;

    /*
     * 异常信息
     * */
    @ApiModelProperty(value = "异常信息")
    public String unusualText;


    /*
     * 设备列表
     * */
    @ApiModelProperty(value = "设备列表")
    public List<DbEquipmentVo> dbEquipmentVos;


}
