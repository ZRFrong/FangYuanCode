package com.ruoyi.fangyuantcp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ruoyi.common.constant.*;
import com.ruoyi.common.core.domain.SensorDeviceDto;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.domain.socket.MessageVo;
import com.ruoyi.system.domain.socket.PushMessageVO;
import com.ruoyi.system.domain.socket.StatusMessageResult;
import com.ruoyi.system.feign.DbEquipmentCilent;
import com.ruoyi.system.feign.DbLandClient;
import com.ruoyi.system.util.DbTcpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Mr.Zhao
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuantcp.utils.SendSocketMsgUtils.java
 * @Description 发送socket消息工具类
 * @createTime 2021年06月18日 17:59:00
 */
@Component
@Slf4j
public class SendSocketMsgUtils {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DbLandClient dbLandClient;

    @Autowired
    private DbEquipmentCilent dbEquipmentCilent;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 在线离线状态
     * @since: 2.0.0
     * @param list
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/18 19:36
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public void onlineState(List<String> list,Integer isOnline){
        for (String heartName : list) {
            sendMsg(heartName,isOnline,MessageReturnTypeConstant.ONLINE_STATE);
            log.info("在线离线切换消息推送："+heartName + "=" +isOnline);
        }
    }

    /**
     * 自动手动状态
     * @since: 2.0.0
     * @param heartName
     * @param isFault
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/22 15:57
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public void autoState(String heartName,Integer isFault){
        sendMsg(heartName,isFault,MessageReturnTypeConstant.AUTO_MATIC_STATE);
        log.info("手自动状态切换消息推送："+heartName + "=" +isFault);
    }

    private void sendMsg(String heartName,Integer data,Integer type){
        List<Long> ids = dbEquipmentCilent.getComponentIds(heartName);
        ArrayList<MessageVo> messageVos = new ArrayList<>();
        for (Long id : ids) {
            messageVos.add(MessageVo.builder()
                    .functionId(id)
                    .status(data)
                    .build());
        }
        PushMessageVO pushMessageVO = PushMessageVO.builder()
                .messageType(SocketListenerEventConstant.DEVICE_EVENT)
                .messageTarget(listToString(dbEquipmentCilent.getUserIdList(heartName)))
                .messageInfo(new cn.hutool.json.JSONObject(StatusMessageResult.builder()
                        .landId(dbLandClient.getLandIdsByHeartName(heartName))
                        .type(type)
                        .equipmentMsg(messageVos)
                        .build()))
                .build();
        amqpTemplate.convertAndSend(MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE,MqRoutingKeyConstant.SOCKET_MESSAGE_ROUTING,JSONObject.toJSONString(pushMessageVO,SerializerFeature.WriteMapNullValue));
    }

    /**
     * 进度状态
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/21 11:21
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public void progressState(String heartName){
        heartName = heartName.split("_")[0];
        PushMessageVO pushMessageVO = PushMessageVO.builder()
                .messageType(SocketListenerEventConstant.DEVICE_EVENT)
                .messageTarget(listToString(dbEquipmentCilent.getUserIdList(heartName)))
                .messageInfo(new cn.hutool.json.JSONObject(StatusMessageResult.builder()
                        .landId(dbLandClient.getLandIdsByHeartName(heartName))
                        .type(MessageReturnTypeConstant.PROGRESS_STATE)
                        .equipmentMsg(JSON.parse(redisUtils.get(RedisKeyConf.PROGRESS_ + heartName)))
                        .build()))
                .build();
        System.out.println("进度消息："+JSONObject.toJSONString(pushMessageVO));
        amqpTemplate.convertAndSend(MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE,MqRoutingKeyConstant.SOCKET_MESSAGE_ROUTING,JSONObject.toJSONString(pushMessageVO,SerializerFeature.WriteMapNullValue));
    }

    /**
     * 传感器状态回传
     * @since: 2.0.0
     * @param dbTcpType
     * @param sensorState
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/21 10:50
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public void sensorMsgSend(DbTcpType dbTcpType, Integer sensorState) {
        List<SensorDeviceDto> result = DbTcpUtils.getSensorResult(dbTcpType);
        if (result == null || result.size() <= 0){
            return;
        }
        String heartName = dbTcpType.getHeartName().split("_")[0];
        PushMessageVO pushMessageVO = PushMessageVO.builder()
                .messageType(SocketListenerEventConstant.DEVICE_EVENT)
                .messageTarget(listToString(dbEquipmentCilent.getUserIdList(heartName)))
                .messageInfo(new cn.hutool.json.JSONObject(StatusMessageResult.builder()
                        .landId(dbLandClient.getLandIdsByHeartName(heartName))
                        .type(MessageReturnTypeConstant.SENSOR_STATE)
                        .equipmentMsg(result)
                        .build()))
                .build();
        System.out.println("传感状态："+JSONObject.toJSONString(pushMessageVO));
        rabbitTemplate.convertAndSend(MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE,MqRoutingKeyConstant.SOCKET_MESSAGE_ROUTING,JSONObject.toJSONString(pushMessageVO,SerializerFeature.WriteMapNullValue));
    }

    /***
     * 各功能开关状态
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/21 13:59
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public void switchMsgSend(String heartName,Integer flag){
        heartName = heartName.split("_")[0];
        String s = redisUtils.get(RedisKeyConf.SWITCH_ + heartName + "_" + flag);
        log.warn("开关消息------------："+s);
        if (StringUtils.isEmpty(s)){
            log.warn(heartName +"无这种类型操作："+flag);
            return;
        }
        PushMessageVO pushMessageVO = PushMessageVO.builder()
                .messageType(SocketListenerEventConstant.DEVICE_EVENT)
                .messageTarget(listToString(dbEquipmentCilent.getUserIdList(heartName)))
                .messageInfo(new cn.hutool.json.JSONObject(StatusMessageResult.builder()
                        .landId(dbLandClient.getLandIdsByHeartName(heartName))
                        .type(MessageReturnTypeConstant.SWITCH_STATE)
                        .equipmentMsg(JSON.parse(redisUtils.get(RedisKeyConf.SWITCH_ + heartName + "_" + flag)))
                        .build()))
                .build();
        System.out.println("开关状态："+JSONObject.toJSONString(pushMessageVO));
        amqpTemplate.convertAndSend(MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE,MqRoutingKeyConstant.SOCKET_MESSAGE_ROUTING,JSONObject.toJSONString(pushMessageVO,SerializerFeature.WriteMapNullValue));
    }

    /**
     * list转string
     * @since: 2.0.0
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/6/18 20:02
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private String listToString(List<String> list){
        StringBuilder builder = null;
        if (list == null || list.size() < 0 ){
            return "";
        }
        for (String id : list) {
            if (builder == null){
                builder = new StringBuilder();
                builder.append(id);
            }else {
                builder.append(","+id);
            }
        }
        return builder.toString();
    }



}
