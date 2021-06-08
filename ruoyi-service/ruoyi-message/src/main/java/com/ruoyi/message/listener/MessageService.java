package com.ruoyi.message.listener;

import com.ruoyi.system.domain.tcp.OrderMessagePo;

/**
 * 消息处理接口类
 */
public interface MessageService {

    /**
     * 消息处理接口
     * @param messagePo 消息内容
     */
    void doMessage(OrderMessagePo messagePo);
}
