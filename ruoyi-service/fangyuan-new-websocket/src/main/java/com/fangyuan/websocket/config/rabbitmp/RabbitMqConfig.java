package com.fangyuan.websocket.config.rabbitmp;

import com.ruoyi.common.enums.DestinaEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: mq消息配置
 * @Author zheng
 * @Date 2021/5/14 10:15
 * @Version 1.0
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 指令消息交换机
     */
    @Bean
    public DirectExchange SOCKETExchange(){
        return new DirectExchange(DestinaEnum.SOCKET_MESSAGE_DEST.getExchange(),true,false);
    }
    /**
     * 指令消息队列
     */
    @Bean
    public Queue SOCKETQueue(){
        Map<String,Object> arguments = new HashMap<>(3);
        // 绑定死信队列
        arguments.put("x-dead-letter-exchange",DestinaEnum.SOCKET_MESSAGE_DLK_DEST.getExchange());
        arguments.put("x-dead-letter-routing-key",DestinaEnum.SOCKET_MESSAGE_DLK_DEST.getRouting());
        // 绑定消息过期时间
        arguments.put("x-message-ttl",5000 << 1);
        return new Queue(DestinaEnum.SOCKET_MESSAGE_DEST.getQueue(),true,false,false,arguments);
    }
    /**
     * 指令消息交换机-队列-路由键绑定
     */
    @Bean
    public Binding bindingSocketQueue(DirectExchange SOCKETExchange,Queue SOCKETQueue){
        return BindingBuilder.bind(SOCKETQueue)
                .to(SOCKETExchange)
                .with(DestinaEnum.SOCKET_MESSAGE_DEST.getRouting());
    }


    /**
     * 指令消息死信队列交换机
     */
    @Bean
    public DirectExchange socketDlkExchange(){
        return new DirectExchange(DestinaEnum.SOCKET_MESSAGE_DLK_DEST.getExchange(),true,false);
    }
    /**
     * 指令消息死信队列
     */
    @Bean
    public Queue socketDlkQueue(){
        return new Queue(DestinaEnum.SOCKET_MESSAGE_DLK_DEST.getQueue(),true);
    }
    /**
     * 指令消息死信队列交换机-队列-路由键绑定
     */
    @Bean
    public Binding bindingSOCKETDLKQueue(DirectExchange socketDlkExchange,Queue socketDlkQueue){
        return BindingBuilder.bind(socketDlkQueue)
                .to(socketDlkExchange)
                .with(DestinaEnum.SOCKET_MESSAGE_DLK_DEST.getRouting());
    }


}
