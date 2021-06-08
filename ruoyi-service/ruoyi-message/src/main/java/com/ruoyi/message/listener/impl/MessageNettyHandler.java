package com.ruoyi.message.listener.impl;

import com.ruoyi.message.annotation.ChannelConstraint;
import com.ruoyi.common.constant.MessageConstants;
import com.ruoyi.message.listener.MessageService;
import com.ruoyi.system.domain.tcp.OrderMessagePo;
//import com.ruoyi.system.feign.RemoteNettyService;
import com.ruoyi.system.feign.RemoteNettyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: netty-socket消息下发实现类
 * @Author zheng
 * @Date 2021/5/14 11:18
 * @Version 1.0
 */
@Component
@Log4j2
@ChannelConstraint(distributeChannel = MessageConstants.DistributeChannel.TCP)
public class MessageNettyHandler implements MessageService {

    // netty下发tcp指令feign接口
    private final RemoteNettyService remoteNettyService;
    public MessageNettyHandler( RemoteNettyService remoteNettyService){
        this.remoteNettyService = remoteNettyService;
    }


    @Override
    public void doMessage(OrderMessagePo messagePo) {
        log.info("MessageNettyHandle.doMessage value:{}",messagePo);
        remoteNettyService.receiveAllOrder(messagePo.getTo());
    }
}
