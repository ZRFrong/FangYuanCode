package com.ruoyi.fangyuantcp.job;

import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.fangyuantcp.utils.JobUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author Mr.Zhao
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuantcp.job.SelfMottonStateJob.java
 * @Description 自动手动状态检测
 * @createTime 2021年06月21日 13:19:00
 */
@Slf4j
@Component
public class AutomaticStateJob {

    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;

    @Autowired
    private JobUtils jobUtils;

    @Scheduled(fixedRate = 120000)
    public void automaticStateCheck(){
        List<String> clients = dbTcpClientMapper.selectAllDbTcpClient();
        log.warn("定时采集自动手动状态-----------------------"+new Date() +"--------------------------------");
        if (clients == null || clients.size() <= 0){
            return;
        }
        jobUtils.timingSendUtil(clients,TcpOrderTextConf.TaskOnline,OpcodeTextConf.OPCODE03);
    }

}
