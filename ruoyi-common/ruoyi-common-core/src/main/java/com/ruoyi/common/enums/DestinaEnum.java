package com.ruoyi.common.enums;

public enum DestinaEnum {

    /**
     * 设备指令消息队列
     */
    ORDER_MESSAGE_DEST("order_message_exchange","order_message_queue","order_message_routing"),
    /**
     * 设置指令消息死信队列
     */
    ORDER_MESSAGE_DLK_DEST("order_message_dlk_exchange","order_message_dlk_queue","order_message_dlk_routing"),

    ;

    // 交换机
    private String exchange;
    // 队列
    private String queue;
    // 路由键
    private String routing;

    public final String getExchange(){
        return this.exchange;
    }
    public String getQueue(){
        return this.queue;
    }
    public String getRouting(){
        return this.routing;
    }

    DestinaEnum(String exchange,String queue,String routing){
        this.exchange = exchange;
        this.queue = queue;
        this.routing = routing;
    }




}
