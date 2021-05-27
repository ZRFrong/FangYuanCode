package com.ruoyi.fangyuantcp.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.service.OperateVentilateService;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.system.domain.DbOperationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Date;

/*
 * 通风相关操作
 * */
@RestController
@Api("operateVentilate")
@RequestMapping("operateVentilate")
public class OperateVentilateController extends BaseController {

    @Autowired
    private OperateVentilateService operateVentilateService;


    private SendCodeUtils sendCodeUtils = new SendCodeUtils();

    /*
     * 通风 自动手动状态更改
     * */
    @GetMapping("operateTongFengHand/{heartbeatText}/{equipmentNo}/{i}")
    public R operateTongFengHand(@ApiParam(name = "heartbeatText", value = "string") @PathVariable String heartbeatText,
                                 @ApiParam(name = "equipmentNo", value = "string", required = true) @PathVariable("equipmentNo") String equipmentNo,
                                 @ApiParam(name = "i", value = "inter", required = true) @PathVariable("i") Integer i) throws InterruptedException {
        return operateVentilateService.operateTongFengHand(heartbeatText, equipmentNo, i);



    }

    /*
     * 自动通风  开启关闭温度修改
     * */
    @GetMapping("operateTongFengType/{heartbeatText}/{equipmentNo}/{i}/{temp}")
    public R operateTongFengType(@ApiParam(name = "heartbeatText", value = "string") @PathVariable("heartbeatText") String heartbeatText,
                                 @ApiParam(name = "equipmentNo", value = "string", required = true) @PathVariable("equipmentNo") String equipmentNo,
                                 @ApiParam(name = "i", value = "inter", required = true) @PathVariable("i") Integer i,
            @ApiParam(name = "temp", value = "温度") @PathVariable("temp") String temp) throws InterruptedException {
        int i1 = Integer.parseInt(temp);
        int i2=i1/256;
        int i3=i1%256;
        String hex = Integer.toHexString(i2);
        String hex2 = Integer.toHexString(i3);
        String hex1="";
        if (i2<10){

            hex1 ="0"+i2+","+i3+"";
        }else {
            hex1 =i2+","+i3+"";

        }
        return operateVentilateService.operateTongFengType(heartbeatText, equipmentNo, i, hex1);


    }

    /**
     * 补光定时操作
     * @since: 2.0.0
     * @param heartbeatText 心跳名
     * @param equipmentNo 唯一码
     * @param flag  开0 关1
     * @param startTime 开始时间; 单位为分
     * @param stopTime 停止时间：单位为分
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/11 16:28
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("operateLight")
    public R operateLight(String heartbeatText,String equipmentNo,Integer flag,Long startTime,Long stopTime){
        return toAjax(operateVentilateService.operateLight(heartbeatText,equipmentNo,flag,decToHex(startTime),decToHex(stopTime)));
    }


    /**
     * 卷帘卷膜百分比控制
     * @since: 2.0.0
     * @param heartbeatText
     * @param equipmentNo
     * @param percentage
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/18 14:28
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("percentageOperate")
    public R percentageOperate(String heartbeatText,String equipmentNo,Integer percentage,String operateText){
        return toAjax(operateVentilateService.percentageOperate(heartbeatText,equipmentNo,percentage,operateText));
    }


    /**
     * 十进制数转为十六进制
     * @since: 2.0.0
     * @param decNum
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/5/17 9:44
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private static String decToHex(Long decNum){
        if (decNum == null){
            return null;
        }
        Long left =  decNum / 256L;
        Long right = decNum % 256;
        String hex1="";
        if (left<10){
            hex1 ="0"+left+","+right+"";
        }else {
            hex1 =left+","+right+"";
        }
        return hex1;
    }

    public static void main(String[] args){
        String flag = "530";
        int i1 = Integer.parseInt(flag);
        int i2=i1/255;
        int i3=i1%255;
        String hex1="";
        if (i2<10){
            hex1 ="0"+i2+","+i3+"";
        }else {
            hex1 =i2+","+i3+"";
        }
        System.out.println(hex1);
        System.out.println(i1);
        System.out.println(Integer.parseInt("265", 16));
        String tmp = StringUtils.leftPad(Integer.toHexString(01).toUpperCase(), 4, '0');
        String tmp1 = StringUtils.leftPad(Integer.toHexString(530).toUpperCase(), 4, '0');
        System.out.println(tmp+tmp1);
        System.out.println(Integer.toHexString(255));
    }

}
