package com.ruoyi.common.enums;

/**
 * @Description: 视屏监控设备种类枚举类
 * @Author zheng
 * @Date 2021/5/25 14:14
 * @Version 1.0
 */
public enum  EquipmentMonitorTypeEnum {


    VIDEO_RECORDER_DEVICE("录像机",(byte)0),
    CAMERA_DEVICE("网络摄像头",(byte)1),
    CHANNEL_CAMERAS_DEVICE("通道摄像头",(byte)2),
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
