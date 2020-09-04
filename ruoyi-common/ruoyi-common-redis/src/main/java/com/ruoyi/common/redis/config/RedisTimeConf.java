package com.ruoyi.common.redis.config;

import lombok.Getter;


public class RedisTimeConf {
    /**
     * 过期时间一个小时
     */
    public static final Long ONE_HOUR =60*60L;
    /**
     * 过期时间一天
     */
    public static final Long ONE_DAY = 60*60*24L;
    /**
     *过期时间一周
     */
    public static final Long ONE_WEEK = 60*60*24*7L;
    /**
     * 过期时间一月
     */
    public static final Long ONE_MONTH = 60*60*24*30L;
    /**
     * 过期时间一年
     */
    public static final Long ONE_YEAR = 60*60*24*365L;

}
