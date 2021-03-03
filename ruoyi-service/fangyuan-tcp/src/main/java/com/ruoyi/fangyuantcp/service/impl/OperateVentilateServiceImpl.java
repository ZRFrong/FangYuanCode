package com.ruoyi.fangyuantcp.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.service.OperateVentilateService;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.system.domain.DbOperationVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
@Log4j2
public class OperateVentilateServiceImpl implements OperateVentilateService {


    private SendCodeListUtils sendCodeUtils = new SendCodeListUtils();

    @Override
    public R operateTongFengHand(String heartbeatText, String equipmentNo, Integer i) {
        ArrayList<DbOperationVo> dbOperationVos = new ArrayList<>();
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(heartbeatText);
        dbOperationVo.setFacility(equipmentNo);
        dbOperationVo.setOperationText(i == 0 ? TcpOrderTextConf.operateTongFeng : TcpOrderTextConf.operateTongFengOver);
        dbOperationVo.setOperationName(i == 0 ? "开启自动通风" : "关闭自动通风");
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setOperationTextType(OpcodeTextConf.OPCODE05);
        dbOperationVo.setCreateTime(new Date());


        dbOperationVos.add(dbOperationVo);
        DbOperationVo dbOperationVo2 = new DbOperationVo();
        dbOperationVo2.setHeartName(heartbeatText);
        dbOperationVo2.setFacility(equipmentNo);
        dbOperationVo2.setOperationText(TcpOrderTextConf.SinceOrhandTongFeng);
        dbOperationVo2.setOperationTextType(OpcodeTextConf.OPCODE01);
        dbOperationVo2.setOperationName("查询自动通风是否开启");
        dbOperationVo2.setIsTrue("1");
        dbOperationVo2.setCreateTime(new Date());
        dbOperationVos.add(dbOperationVo2);
        Map<String, List<DbOperationVo>> mps = dbOperationVos.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
        return sendCodeUtils.queryIoList(mps);
    }

    @Override
    public R operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String temp) {
        int i2 = Integer.parseInt(temp, 16);
        ArrayList<DbOperationVo> dbOperationVos = new ArrayList<>();
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(heartbeatText);
        dbOperationVo.setFacility(equipmentNo);
        dbOperationVo.setOperationText(i == 0 ? TcpOrderTextConf.operateTongFengType + "," + "00," + i2 : TcpOrderTextConf.operateTongFengOverType + "," + "00," + i2);
        dbOperationVo.setOperationTextType(OpcodeTextConf.OPCODE06);
        dbOperationVo.setOperationName(i == 0 ? "更改开启自动通风温度为" + i2 : "更改关闭自动通风温度为" + i2);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        dbOperationVos.add(dbOperationVo);

        DbOperationVo dbOperationVo2 = new DbOperationVo();
        dbOperationVo2.setHeartName(heartbeatText);
        dbOperationVo2.setFacility(equipmentNo);
        dbOperationVo2.setOperationText(TcpOrderTextConf.SinceOrhandTongFengType);
        dbOperationVo2.setOperationTextType(OpcodeTextConf.OPCODE03);
        dbOperationVo2.setOperationName("查询当前自动通风开始和关闭的温度");
        dbOperationVo2.setIsTrue("1");
        dbOperationVo2.setCreateTime(new Date());
        dbOperationVos.add(dbOperationVo2);
        Map<String, List<DbOperationVo>> mps = dbOperationVos.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));

           return sendCodeUtils.queryIoList(mps);




    }
}
