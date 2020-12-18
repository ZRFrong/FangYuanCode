package com.ruoyi.fangyuantcp.timing;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;


import java.util.Timer;
import java.util.TimerTask;

public class TaskTcpType {

    private IDbTcpClientService dbTcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbTcpTypeService dbTcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                    dbTcpTypeService.timingType();

            }
        };
//       心跳定时执行10分钟
        timer.schedule(timerTask,0, 600000);
    }
}
