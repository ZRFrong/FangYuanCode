package com.ruoyi.fangyuantcp.service.impl;

import com.ruoyi.fangyuantcp.service.OperateVentilateService;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.system.domain.DbOperationVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Log4j2
public class OperateVentilateServiceImpl implements OperateVentilateService {


    private SendCodeUtils sendCodeUtils = new SendCodeUtils();

    @Override
    public int operateTongFengHand(String heartbeatText, String equipmentNo, Integer i) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(heartbeatText);
        dbOperationVo.setFacility(equipmentNo);
        dbOperationVo.setOperationText(i == 0 ? TcpOrderTextConf.operateTongFeng : TcpOrderTextConf.operateTongFengOver);
        dbOperationVo.setOperationName(i == 0 ? "开启自动通风" : "关闭自动通风");
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = sendCodeUtils.query05(dbOperationVo);
        return querystate;
    }

    @Override
    public int operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String temp) {
        int i2 = Integer.parseInt(temp, 16);
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(heartbeatText);
        dbOperationVo.setFacility(equipmentNo);
        dbOperationVo.setOperationText(i == 0 ? TcpOrderTextConf.operateTongFengType + "," + "00," + i2 : TcpOrderTextConf.operateTongFengOverType + "," + "00," + i2);
        dbOperationVo.setOperationName(i == 0 ? "更改开启自动通风温度为" + i2 : "更改关闭自动通风温度为" + i2);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = sendCodeUtils.query06(dbOperationVo);
        return querystate;
    }
}
