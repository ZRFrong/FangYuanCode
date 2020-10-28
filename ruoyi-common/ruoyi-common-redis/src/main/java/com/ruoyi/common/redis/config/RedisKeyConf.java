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
    REFRESH_TOKEN_

}
