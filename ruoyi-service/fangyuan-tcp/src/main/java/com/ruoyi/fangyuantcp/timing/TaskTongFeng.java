package com.ruoyi.fangyuantcp.timing;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

@Log4j2
public class TaskTongFeng {

    private IDbTcpClientService dbTcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbTcpTypeService dbTcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                     dbTcpTypeService.timingTongFengType();

                     Thread.sleep(1000);
                     dbTcpTypeService.timingTongFengHand();

                    log.info("通风定时查询执行===时间："+new Date());
                } catch (Exception e) {

                    log.error("通风定时查询执行===时间："+new Date());
                }

            }
        };
//       心跳定时执行10分钟
        timer.schedule(timerTask,0, 660000);
    }
}
