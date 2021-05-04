package com.ruoyi.fangyuantcp.timing;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.utils.DateUtilLong;
import com.ruoyi.system.domain.DbTcpType;
import lombok.extern.log4j.Log4j2;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
/*
 * 定时传感数据固化
 * */
@Log4j2
public class TaskTcpOrder {
    private IDbTcpTypeService dbTcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);
    public void HeartbeatRun() {


        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                /*
                 * 查询所有列表检索出需要过期的状态信息
                 * */
                DbTcpType dbTcpType = new DbTcpType();
                List<DbTcpType> list = dbTcpTypeService.selectDbTcpTypeList(dbTcpType);
                list.forEach(itm -> {
                    Long minuteDiff = DateUtilLong.getMinuteDiff(itm.getUpdateTime(), new Date());
                    if (minuteDiff < 10) {

                        try {
                            dbTcpTypeService.curingTypeTiming(itm);
                        } catch (Exception e) {
                            log.error("定时传感数据固化===时间："+new Date()+e);
                        }
                    }
                });
            }
        };
//       心跳定时查询等待1秒后开始查询
        timer.schedule(timerTask, 300000);
    }
}
