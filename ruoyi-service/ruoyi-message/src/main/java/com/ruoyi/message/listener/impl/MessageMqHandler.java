package com.ruoyi.message.listener.impl;

import com.ruoyi.message.annotation.ChannelConstraint;
import com.ruoyi.common.constant.MessageConstants;
import com.ruoyi.message.listener.MessageService;
import com.ruoyi.system.domain.tcp.OrderMessagePo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @Description: MQ消息下发实现类
 * @Author zheng
 * @Date 2021/5/14 11:18
 * @Version 1.0
 */
@Component
@Log4j2
@ChannelConstraint(distributeChannel = MessageConstants.DistributeChannel.MQ)
public class MessageMqHandler implements MessageService {
    @Override
    public void doMessage(OrderMessagePo messagePo) {
        log.info("MessageMqHandle.doMessage value:{}",messagePo);
    }
}
