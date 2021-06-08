package com.ruoyi.common.constant;

/**
 * @Description: 消息类常量
 * @Author zheng
 * @Version 1.0
 */
public final class MessageConstants {

    /**
     * 指令下发状态缓存标识前缀
     */
    public static final String ORDER_STATUS_CACHE_PREFIX = "ORDER_STATUS_CACHE";

    /**
     * 消息下发渠道常量类
     */
    public static final class DistributeChannel{
        public static final String TCP = "netty";
        public static final String MQ = "mq";
    }
}
