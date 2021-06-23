package com.ruoyi.fangyuantcp.job;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.MessageReturnTypeConstant;
import com.ruoyi.common.constant.MqExchangeConstant;
import com.ruoyi.common.constant.MqMessageConstant;
import com.ruoyi.common.constant.MqRoutingKeyConstant;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.fangyuantcp.utils.SendSocketMsgUtils;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.domain.socket.MessageVo;
import com.ruoyi.system.domain.socket.PushMessageVO;
import com.ruoyi.system.domain.socket.StatusMessageResult;
import com.ruoyi.system.feign.DbEquipmentCilent;
import com.ruoyi.system.feign.DbLandClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuantcp.job.HeartbeatCheck.java
 * @Description 定时清除过期心跳
 * @createTime 2021年06月07日 16:57:00
 */
@Component
public class HeartbeatCheck {

    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;

    @Autowired
    private DbEquipmentCilent dbEquipmentCilent;

    @Autowired
    private DbTcpTypeMapper dbTcpTypeMapper;

    @Autowired
    private SendSocketMsgUtils sendSocketMsgUtils;

    @Autowired
    private DbEquipmentMapper1 dbEquipmentMapper;

    /**
     * 超时时间一分半 三次
     * */
    private static final Integer EXPIRE_TIME = 90000;

    @Scheduled(fixedRate = 60000)
    public void clearExpireHeartbeat(){
        List<DbTcpClient> list = dbTcpClientMapper.selectIdAndTime();
        if (list.size() == 0){
            return;
        }
        List<Long> idList = null;
        ArrayList<String> heartNames = null;
        for (DbTcpClient client : list) {
            if (System.currentTimeMillis()-client.getHeartbeatTime().getTime() > EXPIRE_TIME){
                // 修改设备状态
                if (idList == null){
                    idList = new ArrayList<>();
                    heartNames = new ArrayList<>();
                }
                idList.add(client.getTcpClientId());
                heartNames.add(client.getHeartName());
                /**
                 * 删除传感器信息
                 * */
                dbTcpTypeMapper.deleteByHeartName(client.getHeartName());
            }
        }
        if (idList == null || idList.size() <= 0){
            return;
        }
        updateEquipmentState(heartNames);
        dbTcpClientMapper.deleteExpireHeartbeat(idList);
        sendSocketMsgUtils.onlineState(heartNames,MessageReturnTypeConstant.OFFONLINE);
    }

    private void  updateEquipmentState(List<String> heartNames){
        for (String name : heartNames) {
            DbEquipment equipment = new DbEquipment();
            equipment.setHandlerText(name);
            List<DbEquipment> dbEquipments = dbEquipmentMapper.selectDbEquipmentList(equipment);
            for (DbEquipment e : dbEquipments) {
                e.setIsFault(1);
                e.setIsOnline(1);
                dbEquipmentMapper.updateDbEquipment(e);
            }
        }
    }

    public static void main(String[] args){
        Object s = "2021-06-07 15:56:25.387000";
        Date s1 = (Date) s;
        System.out.println(s1.getTime());
    }

}
