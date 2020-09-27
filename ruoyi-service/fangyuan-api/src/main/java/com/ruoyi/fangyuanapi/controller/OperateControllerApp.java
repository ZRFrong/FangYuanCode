package com.ruoyi.fangyuanapi.controller;

/*
 * app操作controller
 * */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.OperatePojo;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Api("operateApp")
@RequestMapping("operateApp")
public class OperateControllerApp {

    @Autowired
    private IDbLandService landService;

    @Autowired
    private IDbEquipmentService equipmentService;



    @Autowired
    private RemoteTcpService remoteTcpService;




    private  List<DbOperationVo> lists = new ArrayList<>();

    /*
     * 土地页面操作
     *  土地集合    操作项 {卷帘，通风，浇水，补光}
     *              {key：value}   key：{卷帘，通风，浇水，补光}    value{卷起，卷起暂停，放下，放下暂停，开始，暂停}
     *                      {1,2,3,4}
     * */
    @GetMapping("oprateLand")
    @ApiOperation(value = "土地页面操作", notes = "土地页面操作")
    public AjaxResult oprateLand(@ApiParam(name = "ids", value = "土地的ids", required = true) String[] ids, @ApiParam(name = "type", value = "卷帘:1，通风:2，浇水:3，补光:4", required = true) String type, @ApiParam(name = "handleName", value = "开始 ：start，开始暂停：start_stop，结束暂停down_stop，结束down", required = true) String handleName) {
        List<String> strings = Arrays.asList(ids);
        strings.forEach(ite -> send(landService.selectDbLandById(Long.valueOf(ite)), type, handleName));
        return AjaxResult.success( remoteTcpService.operationList(lists));
    }


    private void send(DbLand dbLand, String type, String handleName) {
        Arrays.asList(dbLand.getNoteText().split(",")).forEach(
                ite -> sendTcp(equipmentService.selectDbEquipmentById(Long.valueOf(ite)), type, handleName));

    }

    /*
     *handlerText  操作集json {key：key:{key:value}}
     * type:{1:卷帘卷起 ， 2：卷帘放下，3通风开启， 4通风关闭，5补光开启，6补光结束，7浇水开启，8浇水关闭}
     * */
    private void sendTcp(DbEquipment equipment, String type, String handleName) {
        List<OperatePojo> objects = JSON.parseArray(equipment.getHandlerText(), OperatePojo.class);
        //        操作集

        DbOperationVo dbOperationVo = new DbOperationVo();
//        心跳名称
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
//        设备号
        dbOperationVo.setFacility(equipment.getHeartbeatText());
//        是否完成
        dbOperationVo.setIsTrue("0");
//        创建时间
        dbOperationVo.setCreateTime(new Date());


        for (OperatePojo object : objects) {
            if (object.getCheckCode() == type) {
                for (OperatePojo.OperateSp operateSp : object.getSpList()) {
                    if (operateSp.getHandleName() == handleName) {
//                        循环调用发送接口
                        dbOperationVo.setOperationText(operateSp.getHandleCode());
                        lists.add(dbOperationVo);
                    }
                }
            }
        }


    }




}
