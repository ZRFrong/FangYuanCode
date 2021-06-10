package com.fangyuan.websocket.config.rabbitmp;

import com.alibaba.fastjson.JSON;
import com.fangyuan.websocket.config.socket.service.SocketIoService;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.system.domain.socket.PushMessageVO;
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
public class MQConsumer {

    @Autowired
    private SocketIoService socketIoService;


    /**
     * SOCKET消息监听
     * @param message 消息内容
     */
    @RabbitListener(queues = "socket_message_queue")
    public void socketListener(String message){
        log.info("socketListener.message:【{}】",message);
        final PushMessageVO pushMessageVO = JSON.parseObject(message, PushMessageVO.class);
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
}
