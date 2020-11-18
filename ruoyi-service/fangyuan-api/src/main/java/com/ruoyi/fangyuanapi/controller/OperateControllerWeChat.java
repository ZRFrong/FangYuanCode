package com.ruoyi.fangyuanapi.controller;

/*
 *
 * 微信小程序操作类
 * */

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
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
//        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(Long.valueOf("1"));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        return AjaxResult.success(getOperateWeChatVos(dbLands));
    }

    /*
    *页面操作（单项）
    * */
    @GetMapping("operate")
    @ApiOperation(value = "查询当前用户下的操作列表", notes = "查询当前用户下的操作列表")
    public AjaxResult operate(@ApiParam(name = "id", value = "设备id", required = true)Long equipmentId, @ApiParam(name = "text", value = "操作指令", required = true)String text) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(equipmentId);
        dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());
        dbOperationVo.setFacility(dbEquipment.getEquipmentNo());
        dbOperationVo.setOperationText(text);
          return AjaxResult.success(remoteTcpService.operation(dbOperationVo));
    }
    /*
     *页面操作（单项）
     * */
    @GetMapping("operateVentilate")
    @ApiOperation(value = "查询当前用户下的操作列表", notes = "查询当前用户下的操作列表")
    public AjaxResult operateVentilate(@ApiParam(name = "id", value = "设备id", required = true)Long equipmentId) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(equipmentId);
        String handlerText = dbEquipment.getHandlerText();

        return AjaxResult.success(remoteTcpService.operation(dbOperationVo));
    }




    private  ArrayList<OperateWeChatVo> getOperateWeChatVos(List<DbLand> dbLands) {
        ArrayList<OperateWeChatVo> operateWeChatVos = new ArrayList<>();
        for (DbLand dbLand : dbLands) {
            OperateWeChatVo operateWeChatVo = new OperateWeChatVo();
            operateWeChatVo.setDbLandId(dbLand.getLandId());
            operateWeChatVo.setNickName(dbLand.getNickName());
            if (dbLand.getEquipmentIds().equals("") && dbLand == null) {
                operateWeChatVo.setIsBound(0);
                break;
            }
            ArrayList<DbEquipmentVo> dbEquipmentVos = new ArrayList<>();
            for (String s : dbLand.getEquipmentIds().split(",")) {
            DbEquipmentVo dbEquipmentVo = new DbEquipmentVo();
                DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(s));
                dbEquipmentVo.setEquipment(dbEquipment);
                List<OperatePojo> pojos = JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class);
                dbEquipment.setPojos(pojos);
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(dbEquipment.getHeartbeatText() + "_" + dbEquipment.getEquipmentNo());

                List<DbTcpType> list = remoteTcpService.list(dbTcpType);
                if (list.size() != 0&&list!=null) {
                DbTcpType dbTcpType1 = list.get(0);
                    dbEquipmentVo.setDbTcpType(dbTcpType1);
                }
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
