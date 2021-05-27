package com.ruoyi.fangyuanapi.controller;

/*
 * 通风操作，以及状态回写
 * */


import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuanapi.aspect.OperationLog;
import com.ruoyi.fangyuanapi.aspect.OperationLogType;
import com.ruoyi.fangyuanapi.service.IDbEquipmentComponentService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbEquipmentComponent;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("OperateVentilate")
@RequestMapping("OperateVentilate")
public class OperateVentilate {


    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private IDbEquipmentService equipmentService;

    @Autowired
    private IDbEquipmentComponentService dbEquipmentComponentService;

    /*
     * 自动通风操作  更改开始关闭的温度
     * */
    @GetMapping("operateTongFengType")
    @ApiOperation(value = "操作自动通风自动开启关闭的温度", notes = "操作自动通风自动开启关闭的温度")
    public R operateTongFengType(@ApiParam(name = "dbEquipmentId", value = "dbEquipmentID") Long dbEquipmentId,
                                 @ApiParam(name = "i", value = "是否开启0,1") Integer i,
                                 @ApiParam(name = "temp", value = "温度") Integer temp) {
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(dbEquipmentId);
        return remoteTcpService.operateTongFengType(dbEquipment.getHeartbeatText(), dbEquipment.getEquipmentNoString(), i, temp+"");
    }


    /*
     * 自动通风操作  更改开始关闭装态
     * */
    @GetMapping("operateTongFengHand")
    @ApiOperation(value = "操作自动通风是否开启自动", notes = "操作自动通风是否开启自动",httpMethod = "GET")
    public R operateTongFengHand(@ApiParam(name = "dbEquipmentId", value = "dbEquipmentID") Long dbEquipmentId, @ApiParam(name = "i", value = "是否开启0,1") Integer i) {
        DbEquipment equipment = equipmentService.selectDbEquipmentById(dbEquipmentId);
        return remoteTcpService.operateTongFengHand(equipment.getHeartbeatText(), equipment.getEquipmentNoString(), i);
    }

    @PostMapping("setUpVentilate")
    @ApiOperation(value = "2.0操作自动通风开关温度",notes = "2.0操作自动通风开关温度",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "functionId",value = "功能id",required = true),
            @ApiImplicitParam(name = "startTemperature",value = "开启温度",required = true),
            @ApiImplicitParam(name = "stopTemperature",value = "关闭温度",required = true),
    })
    public R setUpVentilate(Long functionId,Integer startTemperature,Integer stopTemperature){
        DbEquipmentComponent component = dbEquipmentComponentService.selectDbEquipmentComponentById(functionId);
        R start = remoteTcpService.operateTongFengType(component.getHeartbeatText(), component.getEquipmentNoString(), 0, startTemperature + "");
        R stop = remoteTcpService.operateTongFengType(component.getHeartbeatText(), component.getEquipmentNoString(), 1, stopTemperature + "");
        if (String.valueOf(start.get("code")).equals("200") && String.valueOf(stop.get("code")).equals("200")  ){
            return start;
        }
        return R.error("操作失败，请重试！");
    }

    @PostMapping("setUpVentilateStatus")
    @ApiOperation(value = "2.0操作自动通风是否开启自动", notes = "2.0操作自动通风是否开启自动",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "functionId",value = "功能id",required = true),
            @ApiImplicitParam(name = "switchStatus",value = "0 开  1关",required = true),
    })
    public R setUpVentilateStatus( Long functionId, Integer switchStatus) {
        DbEquipmentComponent component = dbEquipmentComponentService.selectDbEquipmentComponentById(functionId);
        return remoteTcpService.operateTongFengHand(component.getHeartbeatText(), component.getEquipmentNoString(), switchStatus);
    }


    public static void main(String[] args){
        String bin=Integer.toBinaryString(230);
        String hex= Integer.toHexString(230);
        System.out.println(bin);
        System.out.println(hex);
    }
}
