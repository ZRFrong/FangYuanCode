package com.ruoyi.fangyuanapi.controller;

/*
 * app操作controller  土地操作
 * */

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.*;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.aspect.OperationLog;
import com.ruoyi.fangyuanapi.aspect.OperationLogType;
import com.ruoyi.fangyuanapi.aspect.OperationLogUtils;
import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.fangyuanapi.utils.OperateSendUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Api("operateApp")
@RequestMapping("operateApp")
public class OperateControllerApp extends BaseController {

    @Autowired
    private IDbLandService landService;

    @Autowired
    private IDbEquipmentService equipmentService;


    @Autowired
    private IDbLandEquipmentService landEquipmentService;


    @Autowired
    private IDbOperationRecordService operationRecordService;


    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private OperationLogUtils operationLogUtils;


    private List<DbOperationVo> lists = new ArrayList<>();

    /*
     * 土地页面操作
     *  土地集合    操作项 {卷帘，通风，浇水，补光}
     *              {key：value}   key：{卷帘，通风，浇水，补光}    value{卷起，卷起暂停，放下，放下暂停，开始，暂停}
     *                      {1,2,3,4}
     * */
    @GetMapping("oprateLand")
    @OperationLog(OperationLogType = true, OperationLogNmae = OperationLogType.LAND, OperationLogSource = OperationLogType.APP)
    @ApiOperation(value = "土地页面操作", notes = "土地页面操作")
    public R oprateLand(@ApiParam(name = "ids", value = "土地的子串，分隔", required = true) String ids, @ApiParam(name = "type",
            value = "卷帘:1，通风:2，补光:3,浇水:4", required = true) String type, @ApiParam(name = "handleName",
            value = "开始 ：start，暂停：start_stop，暂停down_stop，结束down", required = true) String handleName, Integer time) {

        List<String> strings = Arrays.asList(ids.split(","));
        String text = "";

        if (time!=null){
//            添加定时任务

        }

        strings.forEach(ite -> send(landService.selectDbLandById(Long.valueOf(ite)), type, handleName,text));

//        添加操作记录

//        int i = OperateSendUtils.operationList(lists);
        R r = remoteTcpService.operationList(lists);
        lists=null;
        lists=new ArrayList<>();
        return r;
    }


    private void send(DbLand dbLand, String type, String handleName,String text) {

        if (dbLand.getSiteId() == 0) {
//            地块
            DbLand dbLand1 = new DbLand();
            dbLand1.setSiteId(dbLand.getLandId());
            List<DbLand> dbLands = landService.selectDbLandList(dbLand1);
            for (DbLand land : dbLands) {
                String text1=land.getNickName()+text;
                Arrays.asList(land.getEquipmentIds().split(",")).forEach(
                        ite -> sendTcp(equipmentService.selectDbEquipmentById(Long.valueOf(ite)), type, handleName,text1));
            }
        } else {
            String text1=dbLand.getNickName()+text;
            if (StringUtils.isNotEmpty(dbLand.getEquipmentIds())) {
                Arrays.asList(dbLand.getEquipmentIds().split(",")).forEach(
                        ite -> sendTcp(equipmentService.selectDbEquipmentById(Long.valueOf(ite)), type, handleName, text1));
            }
        }


    }

    /*
     *handlerText  操作集json {key：key:{key:value}}
     * type:{1:卷帘卷起 ， 2：卷帘放下，3通风开启， 4通风关闭，5补光开启，6补光结束，7浇水开启，8浇水关闭}
     * */
    private void sendTcp(DbEquipment equipment, String type, String handleName,String text) {
        List<OperatePojo> objects = JSON.parseArray(equipment.getHandlerText(), OperatePojo.class);
        //        操作集


        for (OperatePojo object : objects) {
            if (type.equals(object.getCheckCode())) {
                if (object.getSpList().size()==3){
                    if (handleName.equals("down_stop")){
                        handleName="start_stop";
                    }
                }
                for (OperatePojo.OperateSp operateSp : object.getSpList()) {
                    String handleName1 = operateSp.getHandleName();

                    if (handleName.equals(handleName1)) {

                        DbOperationVo dbOperationVo = new DbOperationVo();
//        心跳名称
                        dbOperationVo.setHeartName(equipment.getHeartbeatText());
//        设备号
                        dbOperationVo.setFacility(equipment.getEquipmentNoString());
//                        操作名称
                        dbOperationVo.setOperationName(text+"-"+object.getCheckName()+"-"+operationLogUtils.toOperationText(object.getCheckCode(), operateSp.getHandleName()));
//        是否完成
                        dbOperationVo.setIsTrue("1");

                        dbOperationVo.setOperationId(equipment.getEquipmentId().toString());
//        创建时间
                        dbOperationVo.setCreateTime(new Date());
//                        循环调用发送接口
                        dbOperationVo.setOperationText(operateSp.getHandleCode());
                        lists.add(dbOperationVo);
                    }
                }

            }
        }


    }


    /*
     * 设备页面的操作   具体操作项   ok
     * */
    @GetMapping("oprateEqment")
    @ApiOperation(value = "设备页面操作", notes = "设备页面操作")
    @OperationLog(OperationLogType = true, OperationLogNmae = OperationLogType.EQUIPMENT, OperationLogSource = OperationLogType.APP)
    public R oprateEqment(@ApiParam(name = "id", value = "设备id", required = true) Long id, @ApiParam(name = "type"
            , value = "操作单位名称:例如卷帘1", required = true) String type,
                          @ApiParam(name = "handleName", value = "具体操作名称开始 ：start，暂停：start_stop，暂停down_stop，结束down", required = true) String handleName)
            throws Exception {
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        DbLandEquipment dbLandEquipment = new DbLandEquipment();
        dbLandEquipment.setDbEquipmentId(dbEquipment.getEquipmentId());

        List<OperatePojo> pojos = JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class);
        DbOperationVo dbOperationVo = new DbOperationVo();
//        心跳名称
        dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());
//                        操作名称
        dbOperationVo.setOperationName(operationLogUtils.toOperationText(type, handleName));
//        设备号
        dbOperationVo.setFacility(dbEquipment.getEquipmentNoString());
//        是否完成
        dbOperationVo.setIsTrue("1");
//        创建时间
        dbOperationVo.setCreateTime(new Date());

        for (OperatePojo pojo : pojos) {
            if (type.equals(pojo.getCheckCode())) {
                for (OperatePojo.OperateSp operateSp : pojo.getSpList()) {
                    if (operateSp.getHandleName().equals(handleName)) {
                        dbOperationVo.setOperationText(operateSp.getHandleCode());
                    }
                }
            }
        }


//        发送接口

//        调用发送模块
        R operation = remoteTcpService.operation(dbOperationVo);
        if (operation.get("code").equals(200)) {
            return R.ok();
        } else {
            return R.error();
        }


    }


    private List<DbTcpType> lists2 = new ArrayList<>();


    /*
     *温度状态查询接口
     * 土地列表
     * */
    @GetMapping("StateLand")
    @ApiOperation(value = "查询土地下的所有的设备列表", notes = "查询土地下的所有的设备列表")
    public AjaxResult StateLand(@ApiParam(name = "ids", value = "土地数组") String[] ids) {
        Arrays.asList(ids).forEach(ite -> Arrays.asList(landService.selectDbLandById(Long.valueOf(ite)).getEquipmentIds().split(",")).forEach(
                ite2 -> sendState(equipmentService.selectDbEquipmentById(Long.valueOf(ite2)))));
        return AjaxResult.success(lists2);
    }

    private void sendState(DbEquipment equipment) {
        DbTcpType dbTcpType = new DbTcpType();
        dbTcpType.setHeartName(equipment.getHeartbeatText() + "," + equipment.getEquipmentNoString());
        List<DbTcpType> list = remoteTcpService.list(dbTcpType);
        lists2.addAll(list);

    }


    /*
     * 状态查询接口  设备
     * */
    @GetMapping("StateEqment")
    @ApiOperation(value = "查询设备下的状态", notes = "查询设备下的状态")
    public R StateEqment(@ApiParam(name = "id", value = "设备id", required = true) Long id) throws Exception {
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        DbTcpType dbTcpType = new DbTcpType();
        dbTcpType.setHeartName(dbEquipment.getHeartbeatText() + "," + dbEquipment.getEquipmentNoString());
        DbTcpType dbTcpType1 = OperateSendUtils.StateList(dbEquipment.getHeartbeatText());
        return R.data(dbTcpType1);
    }

    /*
    * 操作计时
    * */





    /*
    * 操作时长
    * */



}
