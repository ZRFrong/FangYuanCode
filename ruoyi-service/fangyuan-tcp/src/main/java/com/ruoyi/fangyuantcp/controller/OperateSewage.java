package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.service.OperateSewageService;
import com.ruoyi.fangyuantcp.utils.TcpOrderTextConf;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbSewage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/*
 *
 * 水肥相关操作类
 * */
@RestController
@Api("OperateSewage")
@RequestMapping("OperateSewage")
public class OperateSewage extends BaseController {


    @Autowired
    private OperateSewageService operateSewageService;


    /*
     * 更改 施肥量
     * */
    @GetMapping("updateFertilization")
    @ApiOperation(value = "设置施肥量", notes = "施肥量")
    public R updateFertilization(@ApiParam("施肥量") String fertilization, @ApiParam("水肥类") DbSewage dbSewage) {

        int i = operateSewageService.updateDilutability(fertilization, dbSewage, TcpOrderTextConf.operateSewageFertilization);

        return toAjax(i);
    }

    /*
     * 更改稀释度
     * */
    @GetMapping("updateDilutability")
    @ApiOperation(value = "设置稀释度", notes = "稀释度")
    public R updateDilutability(@ApiParam("稀释度") String dilutability, @ApiParam("水肥类") DbSewage dbSewage) {

        int i = operateSewageService.updateDilutability(dilutability, dbSewage, TcpOrderTextConf.operateSewageDilutability);

        return toAjax(i);
    }


    /*
     * 开启，关闭水肥  依次发送操作指令
     * */
    @GetMapping("turnOperateSewage")
    @ApiOperation(value = "开启，关闭水肥", notes = "开启，关闭水肥")
    public R turnOperateSewage(@ApiParam("开关量") Boolean isOn, @ApiParam("设备列表") List<DbEquipment> dbEquipments) {
        int i=0;
        if (isOn) {
               i=  operateSewageService.turnOperateSewage(TcpOrderTextConf.operateSewageOn,dbEquipments);
        } else {
               i=  operateSewageService.turnOperateSewage(TcpOrderTextConf.operateSewageOff,dbEquipments);

        }

        return toAjax(i);
    }


}
