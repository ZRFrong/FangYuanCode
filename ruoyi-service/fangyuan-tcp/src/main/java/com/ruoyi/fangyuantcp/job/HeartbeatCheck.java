package com.ruoyi.fangyuantcp.job;

import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.feign.DbEquipmentCilent;
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

    private static final Integer EXPIRE_TIME = 1000 * 60 * 2;

    @Scheduled(fixedRate = 60000)
    public void clearExpireHeartbeat(){
        List<DbTcpClient> list = dbTcpClientMapper.selectIdAndTime();
        if (list.size() == 0){
            return;
        }
        List<Long> idList = null;
        for (DbTcpClient client : list) {
            if (System.currentTimeMillis()-client.getHeartbeatTime().getTime() > EXPIRE_TIME){
                // 修改设备状态
                dbEquipmentCilent.updateEquipmentIsOnline(client.getHeartName(),1);
                if (idList == null){
                    idList = new ArrayList<>();
                }
                idList.add(client.getTcpClientId());
            }
        }
        if (idList == null || idList.size() <= 0){
            return;
        }
        dbTcpClientMapper.deleteExpireHeartbeat(idList);
    }

    public static void main(String[] args){
        Object s = "2021-06-07 15:56:25.387000";
        Date s1 = (Date) s;
        System.out.println(s1.getTime());
    }
}
