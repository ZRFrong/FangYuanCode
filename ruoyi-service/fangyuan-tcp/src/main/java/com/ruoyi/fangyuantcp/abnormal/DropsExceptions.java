package com.ruoyi.fangyuantcp.abnormal;


/*
 * 掉线异常
 * */
public class DropsExceptions extends RuntimeException {

    //    操作对象
    private final String code;
    //    掉线说明
    private final String message;

    //    设备id
    private final String equipmentId;

    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLandId() {
        return landId;
    }

    public void setLandId(Long landId) {
        this.landId = landId;
    }

    private Long landId;


    public String getEquipmentId() {
        return equipmentId;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public DropsExceptions(String code, String message, String equipmentId,Long userId,Long landId) {
        this.code = code;
        this.message = message;
        this.equipmentId = equipmentId;
        this.userId = userId;
        this.landId = landId;
    }


}
