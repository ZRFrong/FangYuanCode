package com.fangyuan.netty.service;

import com.ruoyi.system.domain.tcp.DbOperationByteOrderVo;

/**
 * @Description: 消息指令接收逻辑处理接层
 * @Author zheng
 * @Date 2021/6/7 15:26
 * @Version 1.0
 */
public interface IReceiveOrderService {

    /**
     * 处理消息下发
     * @param to 消息体
     */
    void sendMessage(DbOperationByteOrderVo to);
}
