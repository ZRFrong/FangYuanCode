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
import org.springframework.transaction.annotation.Transactional;

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
        ArrayList<DbOperationVo> dbOperationVos = new ArrayList<>();
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(heartbeatText);
        dbOperationVo.setFacility(equipmentNo);
        dbOperationVo.setOperationText(i == 0 ? TcpOrderTextConf.operateTongFengType + "," +  temp : TcpOrderTextConf.operateTongFengOverType + "," + temp);
        dbOperationVo.setOperationTextType(OpcodeTextConf.OPCODE06);
        dbOperationVo.setOperationName(i == 0 ? "更改开启自动通风温度为" + temp : "更改关闭自动通风温度为" + temp);
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


    @Override
    public int operateLight(String heartbeatText, String equipmentNo, Integer flag, String startTime, String stopTime) {
        DbOperationVo operationVo = new DbOperationVo();
        operationVo.setHeartName(heartbeatText);
        operationVo.setFacility("0"+equipmentNo);
        operationVo.setOperationText(flag == 0 ? TcpOrderTextConf.LIGHT_SCHEDULED+","+startTime+","+stopTime:TcpOrderTextConf.CLEAR_LIGHT_SCHEDULED);
        operationVo.setOperationTextType(flag == 0?OpcodeTextConf.OPCODE16 : OpcodeTextConf.OPCODE05);
        operationVo.setOperationName(flag == 0 ?new Date()+ heartbeatText+": 设置补光定时,"+startTime+"分后开启,开"+stopTime+"分"  :heartbeatText +":取消补光定时" );
        operationVo.setCreateTime(new Date());
        operationVo.setIsTrue("1");
        ArrayList<DbOperationVo> arrayList = new ArrayList<>();
        arrayList.add(operationVo);
        Map<String, List<DbOperationVo>> mps = arrayList.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
        R r = SendCodeListUtils.queryIoList(mps);
        return 1;
    }


    @Override
    public int percentageOperate(String heartbeatText, String equipmentNo, Integer percentage,String operationText) {
        ArrayList<DbOperationVo> arrayList = new ArrayList<>();
        if (percentage == 0){
        }
        arrayList.add(DbOperationVo.builder()
                .heartName(heartbeatText)
                .facility("0" + equipmentNo)
                .operationText(operationText+","+percentage)
                .operationTextType(OpcodeTextConf.OPCODE16)
                .operationName(heartbeatText + ": 开到百分比为" + percentage)
                .createTime(new Date())
                .isTrue("1")
                .build());
        Map<String, List<DbOperationVo>> mps = arrayList.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
        R r = SendCodeListUtils.queryIoList(mps);
        System.out.println(r.toString());
        return  1;
    }
}
