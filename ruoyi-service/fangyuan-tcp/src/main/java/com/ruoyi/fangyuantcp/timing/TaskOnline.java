package com.ruoyi.fangyuantcp.timing;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.system.domain.DbTcpClient;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TaskOnline {



    private IDbTcpClientService dbTcpClientService = SpringUtils.getBean(IDbTcpClientService.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                /*
                * 远程，本地检测
                * */
                DbTcpClient dbTcpClient = new DbTcpClient();
                List<DbTcpClient> dbTcpClients = dbTcpClientService.selectDbTcpClientList(dbTcpClient);
                for (DbTcpClient tcpClient : dbTcpClients) {
                    dbTcpClientService.TaskOnline(tcpClient);

                }

            }
        };
//       心跳定时执行10分钟
        timer.schedule(timerTask,0, 120000);
    }
}
