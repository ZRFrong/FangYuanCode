package com.ruoyi.fangyuantcp.utils;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.MqExchangeConstant;
import com.ruoyi.common.constant.MqMessageConstant;
import com.ruoyi.common.constant.MqRoutingKeyConstant;
import com.ruoyi.system.domain.socket.MessageVo;
import com.ruoyi.system.domain.socket.PushMessageVO;
import com.ruoyi.system.domain.socket.StatusMessageResult;
import com.ruoyi.system.feign.DbEquipmentCilent;
import com.ruoyi.system.feign.DbLandClient;
import org.springframework.amqp.core.AmqpTemplate;
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
public class SendSocketMsgUtils {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private DbLandClient dbLandClient;

    @Autowired
    private DbEquipmentCilent dbEquipmentCilent;


    /**
     * 在线离线状态
     * @since: 2.0.0
     * @param list
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/18 19:36
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    public void onlineState(List<String> list){
        for (String heartName : list) {
            List<Long> componentIds = dbEquipmentCilent.getComponentIds(heartName);
            ArrayList<MessageVo> messageVos = new ArrayList<>();
            for (Long id : componentIds) {
                messageVos.add(MessageVo.builder()
                        .functionId(id)
                        .status(1)
                        .build());
            }
            PushMessageVO pushMessageVO = PushMessageVO.builder()
                    .messageType(MqMessageConstant.EQUIPMENT_MESSAGE_TYPE)
                    .messageTarget(listToString(dbEquipmentCilent.getUserIdList(heartName)))
                    .messageInfo(StatusMessageResult.builder()
                            .landId(dbLandClient.getLandIdsByHeartName(heartName))
                            .type(1)
                            .equipmentMsg(messageVos)
                            .build())
                    .build();
            String s = JSONObject.toJSONString(pushMessageVO);
            amqpTemplate.convertAndSend(MqExchangeConstant.SOCKET_MESSAGE_EXCHANGE,MqRoutingKeyConstant.SOCKET_MESSAGE_ROUTING,JSONObject.toJSONString(pushMessageVO));
        }
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
