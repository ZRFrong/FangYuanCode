package com.ruoyi.system.domain.socket;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: socket通道鉴权实体
 * @Author zheng
 * @Date 2021/6/9 13:45
 * @Version 1.0
 */
@Data
public class ReceiveMsgPo implements Serializable {


    /**
     * 认证token
     */
    private String token;

    /**
     * 消息类型 0：设备指令； 1：IM通讯
     */
    private String messageType;

    /**
     * 消息内容
     */
    private Object message;


}
