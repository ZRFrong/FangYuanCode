package com.fangyuan.websocket.config.rabbitmp;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.fangyuan.websocket.config.socket.service.SocketIoService;
import com.ruoyi.common.constant.MessageReturnTypeConstant;
import com.ruoyi.common.constant.MqExchangeConstant;
import com.ruoyi.system.domain.socket.PushMessageVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
public class MQConsumer {

    @Autowired
    private SocketIoService socketIoService;


    /**
     * SOCKET消息监听
     * @param message 消息内容
     */
    /*@RabbitListener(queues = "socket_message_queue")
    public void socketListener(String message){
        log.info("成功socketListener.message:【{}】",message);
        final PushMessageVO pushMessageVO = JSON.parseObject(message, PushMessageVO.class);
        socketIoService.pushMessage(pushMessageVO);
    }*/

    @RabbitListener(queues = "socket_message_queue")
    public void socketListener(String message){
        log.info("成功socketListener.message:【{}】",message);
        PushMessageVO pushMessageVO = JSON.parseObject(message, PushMessageVO.class);
        //PushMessageVO pushMessageVO = message.toBean(PushMessageVO.class);
        socketIoService.pushMessage(pushMessageVO);
    }

    /**
     * socket消息死信队列监听
     * @param message 消息内容
     */
    /*@RabbitListener(queues = "socket_message_dlk_queue")
    public void socketDlkListener(String message){
        log.info("MessageConsumer.socketDlkListener.message :[{}]",message);
    }*/

   /* @RabbitListener(            bindings = @QueueBinding(
            value = @Queue(name = "socket_message_queue", durable = "false"),
            exchange = @Exchange(name = MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {"socket_message_routing"}
    ))
    public void socketListenerTest(PushMessageVO message){
        log.info("222222222222socketListener.message:【{}】",message);
    }*/
}
