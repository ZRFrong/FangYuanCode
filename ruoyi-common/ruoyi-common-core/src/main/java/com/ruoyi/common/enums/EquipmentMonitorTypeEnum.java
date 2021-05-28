package com.ruoyi.common.enums;

/**
 * @Description: 视屏监控设备种类枚举类
 * @Author zheng
 * @Date 2021/5/25 14:14
 * @Version 1.0
 */
public enum  EquipmentMonitorTypeEnum {


    //1-未知设备，0-IPC，1-NVR，2-VMS)
    Unknown_device("未知设备",(byte)-1),
    IPC_device("IPC",(byte)0),
    NVR_device("NVR",(byte)1),
    VMS_device("VMS",(byte)2),
            ;


    private Byte typeCode;
    private String typeName;

    EquipmentMonitorTypeEnum(String typeName,Byte typeCode){
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public String getTypeName(){
        return this.typeName;
    }

    public Byte getTypeCode(){
        return this.typeCode;
    }
}
