package com.ruoyi.fangyuantcp.job;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.HeartbeatUtils;
import com.ruoyi.fangyuantcp.mapper.DbStateRecordsMapper;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.fangyuantcp.utils.JobUtils;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbStateRecords;
import com.ruoyi.system.domain.DbTcpType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuantcp.job.SensorSelectJob.java
 * @Description 对于旧柜子需要主动去查询传感器状态
 * @createTime 2021年06月09日 13:26:00
 */
@Component
@Log4j2
public class SensorSelectJob {

    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;

    @Autowired
    private DbTcpTypeMapper dbTcpTypeMapper;

    @Autowired
    private DbStateRecordsMapper dbStateRecordsMapper;

    @Autowired
    private JobUtils jobUtils;

    private static final Long EXPIRE_TIME = 1000L*60L*60L*24L*7L;

    /**
     * 定时查询传感器数据
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/9 13:48
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    //@Scheduled(fixedRate = 600000)
    public void actuator(){
        List<String> clients = dbTcpClientMapper.selectAllDbTcpClient();
        log.warn("定时采集传感器开始了-----------------------"+new Date() +"--------------------------------");
        if (clients == null || clients.size() <= 0){
            return;
        }
        jobUtils.timingSendUtil(clients,TcpOrderTextConf.stateSave,OpcodeTextConf.OPCODE03);
    }

    /**
     * 清除过期传感器数据，
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/9 13:50
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    //@Scheduled(fixedRate = 900000)
    public void clearExpireSensorData(){
        List<DbTcpType> list = dbTcpTypeMapper.selectDbTcpTypeList(null);
        ArrayList<Long> idList = new ArrayList<>();
        if (list == null || list.size() <= 0){
            return;
        }

        for (DbTcpType type : list) {
            if (!HeartbeatUtils.checkHeartbeat(type.getHeartName())){
                idList.add(type.getTcpTypeId());
                continue;
            }
            if (type.getUpdateTime() == null || EXPIRE_TIME < System.currentTimeMillis() - type.getUpdateTime().getTime()){
                idList.add(type.getTcpTypeId());
            }
        }

        if (idList==null || idList.size() <=0){
            return;
        }
        log.warn("定时清除不符合规则得传感器数据：" + idList.toString());
        dbTcpTypeMapper.batchDeleteDbTcpTypeById(idList);
    }

    /**
     * @Author Mr.ZHAO
     * @Description
     * @Date 0:00 2021/6/15
     * @return void
     * @sign 他日若遂凌云志,敢笑黄巢不丈夫!
     **/
    //@Scheduled(fixedRate = 3600000)
    public void sensorDataPersistence(){
        List<DbTcpType> tcpTypes = dbTcpTypeMapper.selectDbTcpTypeList(null);
        for (DbTcpType type : tcpTypes) {
            if (!HeartbeatUtils.checkHeartbeat(type.getHeartName())){
                continue;
            }
            dbStateRecordsMapper.insertDbStateRecords(DbStateRecords.builder()
                    .demandTime(new Date())
                    .codeOnly(type.getHeartName())
                    .stateJson(JSON.toJSONString(type))
                    .build());
        }
        log.warn("传感器数据定时存储"+ new Date());
    }

}
