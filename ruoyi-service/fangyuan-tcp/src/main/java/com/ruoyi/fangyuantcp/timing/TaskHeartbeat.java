package com.ruoyi.fangyuantcp.timing;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.utils.DateUtilLong;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/*
 * 心跳定时查询
 *
 * */
public class TaskHeartbeat {


    private IDbTcpClientService dbTcpClientService = SpringUtils.getBean(IDbTcpClientService.class);


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
                int i = dbTcpClientService.deleteDbTcpClientById(item.getTcpClientId());
            }
        }
    }


}
