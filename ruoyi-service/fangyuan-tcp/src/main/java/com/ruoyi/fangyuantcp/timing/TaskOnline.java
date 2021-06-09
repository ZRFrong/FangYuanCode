package com.ruoyi.fangyuantcp.timing;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
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
                try {
                    DbTcpClient dbTcpClient = new DbTcpClient();
                    List<DbTcpClient> dbTcpClients = dbTcpClientService.selectDbTcpClientList(dbTcpClient);
                    if (dbTcpClients.size()>0&&dbTcpClients!=null){
                        for (DbTcpClient tcpClient : dbTcpClients) {
                            dbTcpClientService.TaskOnline(tcpClient);

                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        };
//       心跳定时执行10分钟
        timer.schedule(timerTask,0, 120000);
    }
}
