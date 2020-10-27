package com.ruoyi.fangyuantcp.timing;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
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
    private RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                traverse();

            }
        };
//       心跳定时执行1分钟
        timer.schedule(timerTask, 60000);
    }

    /*遍历心跳过期时间为  过期时间为一分钟
     */
    public void traverse() {
        DbTcpClient dbTcpClient = new DbTcpClient();
        dbTcpClient.setIsOnline(0);
        List<DbTcpClient> dbTcpClients = dbTcpClientService.selectDbTcpClientList(dbTcpClient);
        dbTcpClients.forEach(item -> screen(item));
    }





    private void screen(DbTcpClient item) {
        if (item.getHeartbeatTime() != null) {
            Long minuteDiff = DateUtilLong.getMinuteDiff(item.getHeartbeatTime(), new Date());
            if (minuteDiff >= 1) {
                item.setIsOnline(1);
                int i = dbTcpClientService.updateDbTcpClient(item);
                /*
                * redis  中  设备列表的状态改为异常
                * */
                Set<String> keys = redisUtils.keys(RedisKeyConf.EQUIPMENT_LIST + ":" + item.getHeartName());
                for (String key : keys) {
                    DbEquipment dbEquipment = com.alibaba.fastjson.JSON.parseObject(redisUtils.get(key), DbEquipment.class);
                    dbEquipment.setIsFault(1);
                    redisUtils.set(key,JSON.toJSONString(dbEquipment));
                }
            }
        }
    }











}
