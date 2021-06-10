package com.fangyuan.websocket.enums;

/**
 * @Description: socket通道监听事件枚举
 * @Author zheng
 * @Date 2021/6/9 14:05
 * @Version 1.0
 */
public enum SocketListenerEventEnum {

    AUTH("AUTH_TOKEN","鉴权监听"),
    ;
    /**
     * 监听key
     */
    private final String eventKey;
    /**
     * 监听key名称
     */
    private String eventName;

    SocketListenerEventEnum(String eventKey,String eventName){
        this.eventKey = eventKey;
        this.eventName = eventName;
    }

    public final String getEventKey(){
        return this.eventKey;
    }

    public String getEventName(){
        return this.eventName;
    }

}
