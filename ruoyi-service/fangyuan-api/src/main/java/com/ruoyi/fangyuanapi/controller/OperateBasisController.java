package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuanapi.aspect.OperationLog;
import com.ruoyi.fangyuanapi.aspect.OperationLogType;
import com.ruoyi.fangyuanapi.aspect.OperationLogUtils;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLandEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.OperatePojo;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 基础操作控制类
 * @Author zheng
 * @Date 2021/4/12 11:06
 * @Version 1.0
 */

@RestController
@RequestMapping("OperateBasis")
public class OperateBasisController {


    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private IDbLandService dbLandService;

    @Autowired
    private IDbEquipmentService equipmentService;

    @Autowired
    private OperationLogUtils operationLogUtils;

    /*
    * 单操作
    * */

    @GetMapping("oprateEqment")
    @ApiOperation(value = "单操作", notes = "单操作")
    @OperationLog(OperationLogType = true, OperationLogNmae = OperationLogType.EQUIPMENT, OperationLogSource = OperationLogType.APP)
    public R oprateEqment(@ApiParam(name = "id", value = "设备id", required = true) Long id, @ApiParam(name = "type"
            , value = "操作单位名称:例如卷帘1", required = true) String type,
                          @ApiParam(name = "handleName", value = "具体操作名称开始 ：start，开始暂停：start_stop，结束down,结束暂停down_stop", required = true) String handleName)
            throws Exception {
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        DbLandEquipment dbLandEquipment = new DbLandEquipment();
        dbLandEquipment.setDbEquipmentId(dbEquipment.getEquipmentId());

        List<OperatePojo> pojos = JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class);
        List<DbOperationVo> vos = new ArrayList<>();

        for (OperatePojo pojo : pojos) {
            if (type.equals(pojo.getCheckCode())) {
                for (OperatePojo.OperateSp operateSp : pojo.getSpList()) {
                    if (operateSp.getHandleName().equals(handleName)) {
                        DbOperationVo dbOperationVo = new DbOperationVo();
//        心跳名称
                        dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());

//        设备号
                        dbOperationVo.setFacility(dbEquipment.getEquipmentNoString());
//        是否完成
                        dbOperationVo.setIsTrue("1");
//                        操作名称
                        dbOperationVo.setOperationName(operationLogUtils.toOperationText(pojo.getCheckCode(), operateSp.getHandleName()));
//        创建时间
                        dbOperationVo.setCreateTime(new Date());
                        dbOperationVo.setOperationText(operateSp.getHandleCode());
                        dbOperationVo.setOperationTextType("05");
                        vos.add(dbOperationVo);
                    }
                }
            }
        }


//        发送接口

//        调用发送模块
        R operation = remoteTcpService.operationList(vos);
        return operation;


    }

    /*
    * 批量操作
    * */


    /*
    * 定时操作
    * */




}
