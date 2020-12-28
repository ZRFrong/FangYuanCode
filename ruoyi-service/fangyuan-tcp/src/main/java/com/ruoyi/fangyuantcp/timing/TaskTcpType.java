package com.ruoyi.fangyuantcp.timing;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import lombok.extern.log4j.Log4j2;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Log4j2
public class TaskTcpType {

    private IDbTcpClientService dbTcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbTcpTypeService dbTcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    dbTcpTypeService.timingType();
                    log.info("状态定时查询执行===时间："+new Date());
                } catch (Exception e) {

                    log.error("状态定时查询执行错误===时间："+new Date());
                }

            }
        };
//       心跳定时执行10分钟
        timer.schedule(timerTask,0, 600000);
    }
}
