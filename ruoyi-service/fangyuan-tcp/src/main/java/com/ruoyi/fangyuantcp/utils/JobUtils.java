package com.ruoyi.fangyuantcp.utils;

import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.feign.DbEquipmentCilent;
import com.ruoyi.system.feign.DbEquipmentComponentClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Zhao
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuantcp.utils.JobUtils.java
 * @Description
 * @createTime 2021年06月16日 21:03:00
 */
@Component
@Slf4j
public class JobUtils {

    @Autowired
    private DbEquipmentComponentClient dbEquipmentComponentClient;

    public  void timingSendUtil(List<String> clients,String code,String codeType){
        ArrayList<DbOperationVo> list = new ArrayList<>();

        clients.forEach( c -> {
            try {
                list.add(DbOperationVo.builder()
                        .facility(dbEquipmentComponentClient.selectByHeartbeatText(c))
                        .heartName(c)
                        .operationText(code)
                        .operationTextType(codeType)
                        .build());
            }catch (Exception e){
                log.error(e.getMessage());
            }
        });
        SendCodeListUtils.queryIoListNoWait(list );
    }


}
