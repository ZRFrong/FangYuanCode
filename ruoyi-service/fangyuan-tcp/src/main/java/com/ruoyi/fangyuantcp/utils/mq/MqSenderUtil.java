package com.ruoyi.fangyuantcp.utils.mq;

import com.ruoyi.common.enums.DestinaEnum;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.fangyuantcp.utils.LogOrderUtil;
import io.netty.buffer.ByteBuf;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Description: mq消息发送工具
 * @Author zheng
 * @Date 2021/6/18 10:25
 * @Version 1.0
 */
@Component
@Log4j2
public class MqSenderUtil {

    private final RabbitTemplate rabbitTemplate;
    private final LogOrderUtil logOrderUtil;
    public MqSenderUtil(RabbitTemplate rabbitTemplate,LogOrderUtil logOrderUtil){
        this.rabbitTemplate = rabbitTemplate;
        this.logOrderUtil = logOrderUtil;
    }

    /**
     * 设备指令发送
     * @param heartName 心跳名称
     * @param orderByte  指令byte
     */
    public void sendDeviceOrderMsg(String heartName,byte[] orderByte){
        JSONObject msg = new JSONObject();
        msg.put("heartName",heartName);
        msg.put("orderByte",orderByte);
        log.info("MqSenderUtil.sendDeviceOrderMsg msg:{}",msg);
        log.info("MqSenderUtil.sendDeviceOrderMsg 跟踪栈:{}",logOrderUtil.getInvokeMethod());
        rabbitTemplate.convertAndSend(DestinaEnum.ORDER_MESSAGE_DEST.getExchange(),
                DestinaEnum.ORDER_MESSAGE_DEST.getRouting()
                ,msg);
    }

}
