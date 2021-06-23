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
     * 过期时间三天
     */
    public static final Long THREE_DAY = 60*60*24L*3;
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
    /**
     * 验证码过期时间五分钟
     */
    public static final Long FIVE_MINUTE = 60*5L;
    /**
     * 进度数据过期1分钟
     */
    public static final Long ONE_MINUTE = 60*1L;
    /**
     *验证成功 30 分钟THERE_MONTH
     */
    public static final Long THIRTY_MINUTE = 60*30L;
    /**
     * 三个月
     */
    public static final Long THERE_MONTH = 60*60*24*90L;
}
