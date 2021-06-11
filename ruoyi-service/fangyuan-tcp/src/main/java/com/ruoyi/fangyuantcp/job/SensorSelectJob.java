package com.ruoyi.fangyuantcp.job;

import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.system.domain.DbOperationVo;
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

    private static final Long EXPIRE_TIME = 1000L*60L*12L;

    /**
     * 定时查询传感器数据
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/9 13:48
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @Scheduled(fixedRate = 600000)
    public void actuator(){
        List<String> clients = dbTcpClientMapper.selectAllDbTcpClient();
        log.warn("定时采集传感器开始了-----------------------"+new Date() +"--------------------------------");
        if (clients == null || clients.size() <= 0){
            return;
        }
        ArrayList<DbOperationVo> list = new ArrayList<>();
        clients.forEach( c -> {
            list.add(DbOperationVo.builder()
                    .facility("01")
                    .heartName(c)
                    .operationText(TcpOrderTextConf.stateSave)
                    .operationTextType(OpcodeTextConf.OPCODE03)
                    .build());
        });
        SendCodeListUtils.queryIoListNoWait(list );
    }

    /**
     * 清除过期传感器数据，
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/9 13:50
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @Scheduled(fixedRate = 300000)
    public void clearExpireSensorData(){
        List<DbTcpType> list = dbTcpTypeMapper.selectDbTcpTypeList(null);
        ArrayList<Long> idList = new ArrayList<>();
        if (list == null || list.size() <= 0){
            return;
        }

        for (DbTcpType type : list) {
//            if (!HeartbeatUtils.checkHeartbeat(type.getHeartName())){
//                idList.add(type.getTcpTypeId());
//                continue;
//            }
            if (type.getUpdateTime() == null || EXPIRE_TIME < System.currentTimeMillis() - type.getUpdateTime().getTime()){
                idList.add(type.getTcpTypeId());
            }
        }

        if (idList==null || idList.size() <=0){
            return;
        }
        dbTcpTypeMapper.batchDeleteDbTcpTypeById(idList);
    }

}
