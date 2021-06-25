package com.ruoyi.fangyuantcp.utils.mq;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.fangyuantcp.processingCode.SocketSendUtil;
import com.ruoyi.system.domain.socket.PushMessageVO;
import io.netty.buffer.ByteBuf;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description: mq消息消费者工具
 * @Author zheng
 * @Date 2021/6/18 10:26
 * @Version 1.0
 */
@Component
@Log4j2
public class MqConsumerUtil {


    private final SocketSendUtil socketSendUtil;
    public MqConsumerUtil(SocketSendUtil socketSendUtil){
        this.socketSendUtil = socketSendUtil;
    }

    /**
     * 接收mq设备指令消息
     * @param msgData 消息内容
     */
    @RabbitListener(queues = "order_message_queue")
    public void receiveDeviceOrder(JSONObject msgData){
        log.info("MqConsumerUtil.receiveDeviceOrder.message:【{}】",msgData);
        //final JSONObject msgData = JSON.parseObject(msg);
        if(ObjectUtil.isNotNull(msgData)){
            String heartName = msgData.getString("heartName");
            byte[] orderByte = msgData.getBytes("orderByte");
            socketSendUtil.sendMsgToDevice(heartName,orderByte);
        }
    }


}
