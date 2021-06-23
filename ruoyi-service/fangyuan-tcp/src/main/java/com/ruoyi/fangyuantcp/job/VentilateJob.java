package com.ruoyi.fangyuantcp.job;

import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.fangyuantcp.utils.JobUtils;
import com.ruoyi.system.domain.DbTcpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Mr.Zhao
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuantcp.job.VentilateJob.java
 * @Description 自动通风温度读取
 * @createTime 2021年06月16日 20:36:00
 */
@Component
@Slf4j
public class VentilateJob {

    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;

    @Autowired
    private JobUtils jobUtils;

    /**
     * 定时获取自动通风的温度区间
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/16 20:38
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @Scheduled(fixedRate = 300000)
    public void getAutoVentilateData(){
        List<String> clients = dbTcpClientMapper.selectAllDbTcpClient();
        log.warn("定时采集自动通风状态及开关开始了-----------------------"+new Date() +"--------------------------------");
        if (clients == null || clients.size() <= 0){
            return;
        }
        jobUtils.timingSendUtil(clients,TcpOrderTextConf.SinceOrhandTongFengType,OpcodeTextConf.OPCODE03);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jobUtils.timingSendUtil(clients,TcpOrderTextConf.SinceOrhandTongFeng,OpcodeTextConf.OPCODE01);
    }


}
