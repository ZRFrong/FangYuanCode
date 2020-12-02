package com.ruoyi.fangyuantcp.timing;


import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.abnormal.OperationExceptions;
import com.ruoyi.system.domain.DbOperationVo;

import java.util.Timer;
import java.util.TimerTask;

/*
 * 定时查询操作时候成功返回
 * */
public class TaskTcpOrder {
    private static RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);

    public void HeartbeatRun(String text, DbOperationVo dbOperationVo) {


        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    String s = redisUtils.get(text);
                    if (s.isEmpty()){
                        throw  new OperationExceptions(dbOperationVo.getHeartName()+"_"+dbOperationVo.getFacility(),dbOperationVo.getOperationName());
                    }
                } catch (Exception e) {
                    throw  new OperationExceptions(dbOperationVo.getHeartName()+"_"+dbOperationVo.getFacility(),dbOperationVo.getOperationName());
                }
            }
        };
//       心跳定时查询等待1秒后开始查询
        timer.schedule(timerTask, 1000);
    }
}
