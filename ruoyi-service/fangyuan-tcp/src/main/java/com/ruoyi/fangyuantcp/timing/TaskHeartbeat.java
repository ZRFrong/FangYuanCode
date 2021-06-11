package com.ruoyi.fangyuantcp.timing;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.utils.DateUtilLong;
import com.ruoyi.system.domain.DbTcpType;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/*
 * 心跳定时查询
 *
 * */
@Log4j2
public class TaskHeartbeat {


    private IDbTcpClientService dbTcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbEquipmentService dbEquipmentService = SpringUtils.getBean(IDbEquipmentService.class);
    private IDbTcpTypeService dbTcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
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
                                     * 设备列表的状态改为异常
                                     * */
                                    DbEquipment dbEquipment1 = new DbEquipment();
                                    dbEquipment1.setHeartbeatText(tcpClient.getHeartName());
                                    List<DbEquipment> dbEquipments = dbEquipmentService.selectDbEquipmentList(dbEquipment1);
                                    for (DbEquipment dbEquipment : dbEquipments) {
                                        dbEquipment.setIsFault(1);
                                        dbEquipmentService.updateDbEquipmentFeedback(dbEquipment);
                                    }
                                    /*
                                    * 删除对应的状态
                                    * */
                                    DbTcpType dbTcpType = new DbTcpType();
                                    dbTcpType.setHeartName(dbEquipment1.getHeartbeatText());
                                    List<DbTcpType> list = dbTcpTypeService.selectDbTcpTypeList(dbTcpType);
                                    if (list.size()>0&&list!=null){
                                    dbTcpTypeService.updateByHeartbeat(dbEquipment1.getHeartbeatText());

                                    }

                                }
                            }
                        }

                    }
                    log.info("心跳定时查询===时间："+new Date());
                }
                catch (Exception e) {
                    log.error("心跳定时查询错误===时间："+new Date());
                }
            }
        };
//       心跳定时执行1分钟
        timer.schedule(timerTask,0, 60000);
    }












}
