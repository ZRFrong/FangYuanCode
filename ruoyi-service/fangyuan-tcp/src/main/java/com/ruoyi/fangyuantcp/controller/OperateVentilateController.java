package com.ruoyi.fangyuantcp.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.service.OperateVentilateService;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.system.domain.DbOperationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/*
* 通风相关操作
* */
@RestController
@Api("operateVentilate")
@RequestMapping("operateVentilate")
public class OperateVentilateController extends BaseController {

    @Autowired
    private OperateVentilateService OperateVentilateService;


    private SendCodeUtils sendCodeUtils = new SendCodeUtils();

    /*
     * 通风 自动手动状态更改
     * */
    @GetMapping("operateTongFengHand/{heartbeatText}/{equipmentNo}/{i}")
    public R operateTongFengHand(@ApiParam(name = "heartbeatText", value = "string") @PathVariable String heartbeatText,
                                 @ApiParam(name = "equipmentNo", value = "string", required = true) @PathVariable("equipmentNo") String equipmentNo,
                                 @ApiParam(name = "i", value = "inter", required = true) @PathVariable("i") Integer i) throws InterruptedException {
        int operation = OperateVentilateService.operateTongFengHand(heartbeatText, equipmentNo, i);

        Thread.sleep(1000);
        if (operation!=0){
            DbOperationVo dbOperationVo = new DbOperationVo();
            dbOperationVo.setHeartName(heartbeatText);
            dbOperationVo.setFacility(equipmentNo);
            dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFeng);
            dbOperationVo.setOperationName("查询自动通风是否开启");
            dbOperationVo.setIsTrue("1");
            dbOperationVo.setCreateTime(new Date());
            int querystate = sendCodeUtils.query01(dbOperationVo);
        }
        return toAjax(operation);

    }

    /*
     * 自动通风  开启关闭温度修改
     * */
    @GetMapping("operateTongFengType/{heartbeatText}/{equipmentNo}/{i}/{temp}")
    public R operateTongFengType(@ApiParam(name = "heartbeatText", value = "string") @PathVariable("heartbeatText") String heartbeatText,
                                 @ApiParam(name = "equipmentNo", value = "string", required = true) @PathVariable("equipmentNo") String equipmentNo,
                                 @ApiParam(name = "i", value = "inter", required = true) @PathVariable("i") Integer i,
                                 @ApiParam(name = "temp", value = "温度") @PathVariable("temp") String temp) throws InterruptedException {
        String hex= Integer.toHexString(Integer.parseInt(temp));
        int operation = OperateVentilateService.operateTongFengType(heartbeatText, equipmentNo, i, hex);
        /*
         * 查询状态
         * */
        Thread.sleep(1000);
        if (operation!=0){
            DbOperationVo dbOperationVo = new DbOperationVo();
            dbOperationVo.setHeartName(heartbeatText);
            dbOperationVo.setFacility(equipmentNo);
            dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFengType);
            dbOperationVo.setOperationName("查询当前自动通风开始和关闭的温度");
            dbOperationVo.setIsTrue("1");
            dbOperationVo.setCreateTime(new Date());
            int querystate = sendCodeUtils.query03(dbOperationVo);
        }

        return toAjax(operation);

    }




}
