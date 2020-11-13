package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class DbEquipmentVo {

    public DbEquipmentVo() {
    }

    public DbEquipment getEquipment() {

        return equipment;
    }

    public void setEquipment(DbEquipment equipment) {
        this.equipment = equipment;
    }

    public DbEquipmentVo(DbEquipment equipment, DbTcpType dbTcpType) {
        this.equipment = equipment;
        this.dbTcpType = dbTcpType;
    }

    public DbTcpType getDbTcpType() {
        return dbTcpType;
    }

    public void setDbTcpType(DbTcpType dbTcpType) {
        this.dbTcpType = dbTcpType;
    }

    /*
     * 设备表

     * */
    @ApiModelProperty(value = "设备表")
    public  DbEquipment  equipment;


    /*
     * 设备状态表
     * */
    @ApiModelProperty(value = "设备状态表")
    public  DbTcpType  dbTcpType;




}
