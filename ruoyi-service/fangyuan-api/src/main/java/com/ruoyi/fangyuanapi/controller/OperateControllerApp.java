package com.ruoyi.fangyuanapi.controller;

/*
 * app操作controller  土地操作
 * */
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.*;
import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.system.domain.*;
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


//        @Autowired
//        private RemoteTcpService remoteTcpService;


    private List<DbOperationVo> lists = new ArrayList<>();

    /*
     * 土地页面操作
     *  土地集合    操作项 {卷帘，通风，浇水，补光}
     *              {key：value}   key：{卷帘，通风，浇水，补光}    value{卷起，卷起暂停，放下，放下暂停，开始，暂停}
     *                      {1,2,3,4}
     * */
    @GetMapping("oprateLand")
    @ApiOperation(value = "土地页面操作", notes = "土地页面操作")
    public AjaxResult oprateLand(@ApiParam(name = "ids", value = "土地的子串，分隔", required = true) String ids, @ApiParam(name = "type",
            value = "卷帘:1，通风:2，浇水:3，补光:4", required = true) String type, @ApiParam(name = "handleName",
            value = "开始 ：start，开始暂停：start_stop，结束暂停down_stop，结束down", required = true) String handleName) {

        DbOperationRecord dbOperationRecord = new DbOperationRecord();
        String header = getRequest().getHeader(Constants.LOGIN_SOURCE);
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbOperationRecord.setOperationSource(Integer.parseInt(header));
        dbOperationRecord.setDbUserId(Long.valueOf(userId));
        dbOperationRecord.setOperationObject(ids);
        dbOperationRecord.setOperationObjectType(0);
        dbOperationRecord.setOperationTime(new Date());
        dbOperationRecord.setOperationText(toOperationText(type, handleName));


        List<String> strings = Arrays.asList(ids.split(","));
        strings.forEach(ite -> send(landService.selectDbLandById(Long.valueOf(ite)), type, handleName));
//        添加操作记录
//
//        return AjaxResult.success(remoteTcpService.operationList(lists));
//临时
        int i = OperateSendUtils.operationList(lists);
        lists.clear();
        if (i == 1) {
            dbOperationRecord.setIsComplete(0);
        } else {
            dbOperationRecord.setIsComplete(1);
        }
        operationRecordService.insertDbOperationRecord(dbOperationRecord);
        return AjaxResult.success(i);
    }




    private  String[]  typs=new String[]{"1","2","3","4"};
    private String[] handleNamecode = new String[]{"start", "start_stop", "down_stop", "down"};

    private static String[][] arrs = new String[][]{{"卷帘", "通风", "浇水", "补光"}, {"卷起", "卷起暂停", "放下", "放下暂停", "开始", "结束"}};

    private String toOperationText(String type, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (type.equals("1")||type.equals("2")){
            stringBuilder.append(gettxt1("1", handleName));
        }else {
            stringBuilder.append(gettxt2("2", handleName));

        }
        return stringBuilder.toString();
    }

    private String gettxt2(String s, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < typs.length; i++) {
            if (typs[i].equals(s)) {
                for (int i1 = 0; i1 < handleNamecode.length; i1++) {
                    if (handleNamecode[i1].equals(handleName)) {
                        if (i1 == 0) {
                            stringBuilder.append(arrs[i][5]);
                        } else if (i1 == 4) {
                            stringBuilder.append(arrs[i][6]);
                        }
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private String gettxt1(String s, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < typs.length; i++) {
            if (typs[i].equals(s)) {
                for (int i1 = 0; i1 < handleNamecode.length; i1++) {
                    if (handleNamecode[i1].equals(handleName)) {
                        stringBuilder.append(arrs[0][i]);
                        stringBuilder.append("_"+arrs[1][i1]);
                        return stringBuilder.toString();
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private void send(DbLand dbLand, String type, String handleName) {
        Arrays.asList(dbLand.getEquipmentIds().split(",")).forEach(
                ite -> sendTcp(equipmentService.selectDbEquipmentById(Long.valueOf(ite)), type, handleName));

    }

    /*
     *handlerText  操作集json {key：key:{key:value}}
     * type:{1:卷帘卷起 ， 2：卷帘放下，3通风开启， 4通风关闭，5补光开启，6补光结束，7浇水开启，8浇水关闭}
     * */
    private void sendTcp(DbEquipment equipment, String type, String handleName) {
        List<OperatePojo> objects = JSON.parseArray(equipment.getHandlerText(), OperatePojo.class);
        //        操作集


        for (OperatePojo object : objects) {
            if (type.equals(object.getCheckCode())) {
                for (OperatePojo.OperateSp operateSp : object.getSpList()) {
                    String handleName1 = operateSp.getHandleName();
                    if (handleName.equals(handleName1)) {
                        DbOperationVo dbOperationVo = new DbOperationVo();
//        心跳名称
                        dbOperationVo.setHeartName(equipment.getHeartbeatText());
//        设备号
                        dbOperationVo.setFacility(equipment.getEquipmentNo() + "");
//        是否完成
                        dbOperationVo.setIsTrue("1");
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
    public AjaxResult oprateEqment(@ApiParam(name = "id", value = "设备id", required = true) Long id, @ApiParam(name = "type"
            , value = "操作单位名称", required = true) String type,
                                   @ApiParam(name = "handleName", value = "具体操作名称", required = true) String handleName) throws Exception {

        DbOperationRecord dbOperationRecord = new DbOperationRecord();
//        用户id和操作来源
        String header = getRequest().getHeader(Constants.LOGIN_SOURCE);
        String userId = getRequest().getHeader(Constants.CURRENT_ID);


        dbOperationRecord.setOperationSource(Integer.parseInt(header));
        dbOperationRecord.setDbUserId(Long.valueOf(userId));
        dbOperationRecord.setOperationObject(id.toString());
        dbOperationRecord.setOperationObjectType(1);
        dbOperationRecord.setOperationTime(new Date());
        dbOperationRecord.setOperationText(type+handleName);
        dbOperationRecord.setIsComplete(0);
        operationRecordService.insertDbOperationRecord(dbOperationRecord);



        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        DbLandEquipment dbLandEquipment = new DbLandEquipment();
        dbLandEquipment.setDbEquipmentId(dbEquipment.getEquipmentId());


        List<OperatePojo> pojos = JSON.parseArray(dbEquipment.getHandlerText(), OperatePojo.class);
        DbOperationVo dbOperationVo = new DbOperationVo();
//        心跳名称
        dbOperationVo.setHeartName(dbEquipment.getHeartbeatText());
//        设备号
        dbOperationVo.setFacility(dbEquipment.getEquipmentNo() + "");
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
//        return AjaxResult.success(remoteTcpService.operation(dbOperationVo));

//临时使用
        return AjaxResult.success(OperateSendUtils.doget(dbOperationVo));
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
        dbTcpType.setHeartName(equipment.getHeartbeatText() + "," + equipment.getEquipmentNo());
//        R list = remoteTcpService.list(dbTcpType);
//        lists2.addAll((List<DbTcpType>) list.get("rows"));
//临时
        DbTcpType list = null;
        try {
            list = OperateSendUtils.StateList(equipment.getHeartbeatText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        lists2.add(list);
    }

    /*
     * 状态查询接口  设备
     * */
    @GetMapping("StateEqment")
    @ApiOperation(value = "查询设备下的状态", notes = "查询设备下的状态")
    public R StateEqment(@ApiParam(name = "id", value = "设备id", required = true) Long id) throws Exception {
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(id);
        DbTcpType dbTcpType = new DbTcpType();
        dbTcpType.setHeartName(dbEquipment.getHeartbeatText() + "," + dbEquipment.getEquipmentNo());
        DbTcpType dbTcpType1 = OperateSendUtils.StateList(dbEquipment.getHeartbeatText());
        return R.data(dbTcpType1);
    }






}
