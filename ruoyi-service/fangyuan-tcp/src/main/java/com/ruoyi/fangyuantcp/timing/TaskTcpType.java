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

    private IDbTcpTypeService dbTcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);


    public void HeartbeatRun() {

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    dbTcpTypeService.timingType();
                    /*
                    * 删除长期未更新的状态信息
                    * */
                    dbTcpTypeService.deleteTimingType();
                    log.info("状态定时查询执行===时间："+new Date());
                } catch (Exception e) {
                    System.out.println(e);
                   log.error("状态定时查询执行错误===时间："+new Date()+e);
                }

            }
        };
//       心跳定时执行10分钟
        timer.schedule(timerTask,0, 600000);
    }
}
