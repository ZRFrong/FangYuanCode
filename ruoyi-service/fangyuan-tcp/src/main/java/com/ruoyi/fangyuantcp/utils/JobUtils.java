package com.ruoyi.fangyuantcp.utils;

import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.feign.DbEquipmentCilent;
import com.ruoyi.system.feign.DbEquipmentComponentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class JobUtils {

    @Autowired
    private DbEquipmentComponentClient dbEquipmentComponentClient;

    public  void timingSendUtil(List<String> clients,String code,String codeType){
        ArrayList<DbOperationVo> list = new ArrayList<>();
        clients.forEach( c -> {
            list.add(DbOperationVo.builder()
                    .facility(dbEquipmentComponentClient.selectByHeartbeatText(c))
                    .heartName(c)
                    .operationText(code)
                    .operationTextType(codeType)
                    .build());
        });
        SendCodeListUtils.queryIoListNoWait(list );
    }


}
