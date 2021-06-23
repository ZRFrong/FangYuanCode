package com.ruoyi.common.redis.config;

public enum RedisKeyConf {

    /**
     * ZSET key
     */
    REDIS_ZSET_,
    /**
     * 动态list数组
     */
    DYNAMIC_ARRAY_,


    /*
     *  EquipmentList  设备列表key
     * */
    EQUIPMENT_LIST,

    /*
     * 指令发送      handle
     * */
    HANDLE,

    /**
     *
     */
    DYNAMIC_ORDER,
    /**
     * 缓存预热中
     */
    INSERT_FLAG,
    /**
     * 频繁点赞限制
     */
    GIVE_LIKE_FLAG_,
    /**
     * 刷新token
     */
    REFRESH_TOKEN_,
    /**
     *
     */
    ACCESS_TOKEN_,
    /**
     *
     */
    APP_ACCESS_TOKEN_,
    /**
     * 黄历key
     */
    ALMANAC_,
    /**
     * 开关
     * */
    SWITCH_,
    /**
     * 开关 16进制校验
     * */
    SWITCH_HEX_,
    /**
     * 进度
     * */
    PROGRESS_,
    /**
     * 进度 16进制校验
     * */
    PROGRESS_HEX_,
    /**
     * 传感器 16进制校验
     * */
    SENSOR_HEX_,
    /**
     * 自动手动变化校验
     * */
    AUTOMATIC_MANUAL_CHECK
}
