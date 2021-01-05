package com.ruoyi.fangyuanapi.controller;

/*
 *
 * 微信小程序操作类
 * */

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.aspect.OperationLog;
import com.ruoyi.fangyuanapi.aspect.OperationLogType;
import com.ruoyi.fangyuanapi.aspect.OperationLogUtils;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Api("operateWeChat")
@RequestMapping("operateWeChat")
@Slf4j
public class OperateControllerWeChat extends BaseController {


    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private IDbLandService dbLandService;

    @Autowired
    private IDbEquipmentService equipmentService;


    /*
     *列表回写    当前用户下边所有土地
     * */
    @GetMapping("getList")
    @ApiOperation(value = "查询当前用户下的操作列表", notes = "查询当前用户下的操作列表")
    public AjaxResult getList() {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(Long.valueOf(userId));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        return AjaxResult.success(getOperateWeChatVos(dbLands));
    }

    /*
    *页面操作（单项）
    * */
    @GetMapping("operate")
    @ApiOperation(value = "页面操作（单项）", notes = "页面操作（单项）")
    @OperationLog(OperationLogNmae=OperationLogType.EQUIPMENT,OperationLogSource = OperationLogType.WEchat)
    public R operate(@ApiParam(name = "id", value = "设备id", required = true)Long id, @ApiParam(name = "text", value = "操作指令", required = true)String text,
                     @ApiParam(name = "name", value = "操作对象", required = true)String name,
                     @ApiParam(name = "type", value = "操作对象类型", required = true)String type,
                     @ApiParam(name = "handleName", value = "开始 ：start，开始暂停：start_stop，结束暂停down_stop，结束down", required = true) String handleName) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());
        dbOperationVo.setFacility(dbEquipment.getEquipmentNoString());
        dbOperationVo.setOperationText(text);
        //                        操作名称
        dbOperationVo.setOperationName(OperationLogUtils.toOperationText(name,handleName));
        R operation = remoteTcpService.operation(dbOperationVo);
        return operation;
    }


    /*
     * 设备页面的操作   具体操作项   ok
     * */
    @GetMapping("oprateEqment")
    @ApiOperation(value = "设备页面操作", notes = "设备页面操作")
    @OperationLog(OperationLogType=true,OperationLogNmae=OperationLogType.EQUIPMENT,OperationLogSource = OperationLogType.WEchat)
    public R oprateEqment(@ApiParam(name = "id", value = "设备id", required = true) Long id, @ApiParam(name = "type"
            , value = "操作单位名称:例如卷帘1", required = true) String type,
                          @ApiParam(name = "handleName", value = "具体操作名称开始 ：start，开始暂停：start_stop，结束暂停down_stop，结束down", required = true) String handleName)
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
                        dbOperationVo.setOperationName(OperationLogUtils.toOperationText(pojo.getCheckCode(),operateSp.getHandleName()));
//        创建时间
                        dbOperationVo.setCreateTime(new Date());
                        dbOperationVo.setOperationText(operateSp.getHandleCode());

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



    private  ArrayList<OperateWeChatVo> getOperateWeChatVos(List<DbLand> dbLands) {
        ArrayList<OperateWeChatVo> operateWeChatVos = new ArrayList<>();
        for (DbLand dbLand : dbLands) {
            OperateWeChatVo operateWeChatVo = new OperateWeChatVo();
            operateWeChatVo.setDbLandId(dbLand.getLandId());
            operateWeChatVo.setNickName(dbLand.getNickName());
            if (StringUtils.isEmpty(dbLand.getEquipmentIds()) ) {
                operateWeChatVo.setIsBound(0);
                continue;
            }
            ArrayList<DbEquipmentVo> dbEquipmentVos = new ArrayList<>();

            for (String s : dbLand.getEquipmentIds().split(",")) {
            DbEquipmentVo dbEquipmentVo = new DbEquipmentVo();

                DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(s));
                dbEquipmentVo.setEquipment(dbEquipment);
                List<OperatePojo> pojos = JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class);
                dbEquipment.setPojos(pojos);
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(dbEquipment.getHeartbeatText() + "_" + dbEquipment.getEquipmentNoString());
                List<DbTcpType> list = remoteTcpService.list(dbTcpType);
                if (list.size() != 0&&list!=null) {
                DbTcpType dbTcpType1 = list.get(0);
                if (Double.parseDouble(dbTcpType1.getTemperatureAir())<30){
                    dbEquipmentVo.setDbTcpType(dbTcpType1);
                }
                }
                /*
                * 剩余时长，到期时长计算
                * */
//                运行时长
                dbEquipmentVo.setRemaining(DateUtils.getDatePoorDay(dbEquipment.getAllottedTime(),new Date()));
//              剩余时长
                dbEquipmentVo.setRuntime(DateUtils.getDatePoorDay(new Date(),dbEquipment.getCreateTime()));



                dbEquipmentVos.add(dbEquipmentVo);
//                到期
                if (dbEquipment.getIsFault() == 1) {
                    operateWeChatVo.setIsUnusual(3);
                    log.info(dbEquipment.getEquipmentId() + "已经过期");
                    operateWeChatVo.setUnusualText(dbLand.getNickName() + "下的" + dbEquipment.getEquipmentName() + "已经过期");
                    break;
                } else if (dbEquipment.getIsPause() == 1) {
                    //                故障
                    operateWeChatVo.setIsUnusual(1);
                    operateWeChatVo.setUnusualText(dbLand.getNickName() + "下的" + dbEquipment.getEquipmentName() + "发生故障");
                    log.info(dbEquipment.getEquipmentId() + "发生故障");
                    break;
                } else if (dbEquipment.getIsOnline() == 1) {
                    //                切换手动
                    operateWeChatVo.setIsUnusual(2);
                    operateWeChatVo.setUnusualText(dbLand.getNickName() + "下的" + dbEquipment.getEquipmentName() + "已经切换手动");
                    log.info(dbEquipment.getEquipmentId() + "已经切换手动");
                    break;
                } else {
                    operateWeChatVo.setIsUnusual(0);
                }

            }
            operateWeChatVo.setDbEquipmentVos(dbEquipmentVos);
            operateWeChatVos.add(operateWeChatVo);
        }
        return operateWeChatVos;

    }


}
