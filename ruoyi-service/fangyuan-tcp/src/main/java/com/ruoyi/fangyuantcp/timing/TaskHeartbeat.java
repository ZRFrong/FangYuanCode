package com.ruoyi.fangyuantcp.timing;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.utils.DateUtilLong;

import java.util.*;

/*
 * 心跳定时查询
 *
 * */
public class TaskHeartbeat {


    private IDbTcpClientService dbTcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbEquipmentService dbEquipmentService = SpringUtils.getBean(IDbEquipmentService.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                DbTcpClient dbTcpClient = new DbTcpClient();
                dbTcpClient.setIsOnline(0);
                List<DbTcpClient> dbTcpClients = dbTcpClientService.selectDbTcpClientList(dbTcpClient);
                if (!dbTcpClients.isEmpty()){
                    for (DbTcpClient tcpClient : dbTcpClients) {
                        if (tcpClient.getHeartbeatTime() != null) {

                            Long minuteDiff = DateUtilLong.getMinuteDiff(tcpClient.getHeartbeatTime(), new Date());
                            if (minuteDiff >=1) {
                                int i = dbTcpClientService.deleteDbTcpClientById(tcpClient.getTcpClientId());

                                System.out.println(tcpClient.getHeartName()+"超时了");
                                /*
                                 *   设备列表的状态改为异常
                                 * */
                                DbEquipment dbEquipment1 = new DbEquipment();
                                dbEquipment1.setHeartbeatText(tcpClient.getHeartName());
                                List<DbEquipment> dbEquipments = dbEquipmentService.selectDbEquipmentList(dbEquipment1);
                                for (DbEquipment dbEquipment : dbEquipments) {
                                    dbEquipment.setIsFault(1);
                                    dbEquipmentService.updateDbEquipment(dbEquipment);
                                }

                            }
                        }
                    }
                }
            }
        };
//       心跳定时执行1分钟
        timer.schedule(timerTask,0, 60000);
    }












}
