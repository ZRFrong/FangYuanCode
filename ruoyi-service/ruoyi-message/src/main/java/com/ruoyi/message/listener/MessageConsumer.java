package com.ruoyi.message.listener;

import com.ruoyi.common.json.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description: MQ消息消费者
 * @Author zheng
 * @Date 2021/5/14 11:46
 * @Version 1.0
 */
@Component
@Log4j2
public class MessageConsumer {

    @Autowired
    private MessageHandler messageHandler;

    /**
     * 指令消息监听
     * @param message 指令内容
     */
    @RabbitListener(queues = "order_message_queue")
    public void orderListener(String message){
        log.info("orderListener============{}",message);
        messageHandler.doProcessor(message);
    }

    /**
     * 指令消息死信队列监听
     * @param message 指令内容
     */
    /*@RabbitListener(queues = "order_message_dlk_queue")
    public void orderDlkListener(String message){
        log.info("MessageConsumer.orderDlkListener.message :[{}]",message);
    }*/
}
