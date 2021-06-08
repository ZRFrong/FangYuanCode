package com.ruoyi.message.controller;

import com.ruoyi.common.enums.DestinaEnum;
import com.ruoyi.common.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 测试
 * @Author zheng
 * @Date 2021/5/14 14:12
 * @Version 1.0
 */
@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitMessagingTemplate;

    @PostMapping("sendMessage")
    public String sendMessage(@RequestBody JSONObject msg){
        rabbitMessagingTemplate.convertAndSend(
                DestinaEnum.ORDER_MESSAGE_DEST.getExchange(),DestinaEnum.ORDER_MESSAGE_DEST.getRouting(),msg.toCompactString());
        return msg.toString();
    }
}
