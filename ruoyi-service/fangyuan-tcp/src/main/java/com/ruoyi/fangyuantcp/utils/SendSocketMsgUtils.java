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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        log.info("传感状态："+JSONObject.toJSONString(pushMessageVO));
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
    public void switchMsgSend(String heartName,String flag,Integer checkCode){
        heartName = heartName.split("_")[0];
        MessageVo vo = JSON.parseObject(redisUtils.get(RedisKeyConf.SWITCH_ + heartName + "_" + checkCode), MessageVo.class);
        if (vo == null){
            log.warn(heartName +"无这种类型操作："+flag);
            return;
        }
        flag = flag.equals("0") ? "1" : "0";
        if (flag.equals(vo.getStatus())){
            log.warn(heartName +"状态没改变："+flag);
            return ;
        }
        PushMessageVO pushMessageVO = PushMessageVO.builder()
                .messageType(SocketListenerEventConstant.DEVICE_EVENT)
                .messageTarget(listToString(dbEquipmentCilent.getUserIdList(heartName)))
                .messageInfo(new cn.hutool.json.JSONObject(StatusMessageResult.builder()
                        .landId(dbLandClient.getLandIdsByHeartName(heartName))
                        .type(MessageReturnTypeConstant.SWITCH_STATE)
                        .equipmentMsg(vo)
                        .build()))
                .build();
        System.out.println("开关状态："+JSONObject.toJSONString(pushMessageVO));
        amqpTemplate.convertAndSend(MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE,MqRoutingKeyConstant.SOCKET_MESSAGE_ROUTING,JSONObject.toJSONString(pushMessageVO,SerializerFeature.WriteMapNullValue));
    }

    public boolean switchCheck(String heartName,String flag,Integer checkCode){
        MessageVo vo = JSON.parseObject(redisUtils.get(RedisKeyConf.SWITCH_ + heartName + "_" + checkCode), MessageVo.class);
        if (vo == null){
            return true;
        }
        flag = flag.equals("0") ? "1" : "0";
        if (flag.equals(vo.getStatus())){
            log.warn(heartName +"状态没改变："+flag);
            return false;
        }
        return true;
    }

    /**
     * 十六进制字符串变化校验
     * @since: 2.0.0
     * @param redisKey
     * @return: boolean  无变化true 有变化false
     * @author: ZHAOXIAOSI
     * @date: 2021/6/23 15:59
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public boolean hexStrVarietyCheck(String redisKey,String hexStr){
        String s1 = redisUtils.get(redisKey);
        if (!StringUtils.isEmpty(s1) && s1.equals(hexStr)){
            return true;
        }
        redisUtils.set(redisKey,hexStr);
        return false;
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
        if (list == null || list.size() <= 0 ){
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
