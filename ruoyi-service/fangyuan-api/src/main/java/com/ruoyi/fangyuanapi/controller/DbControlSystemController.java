package com.ruoyi.fangyuanapi.controller;


import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuanapi.aspect.OperationLogUtils;
import com.ruoyi.fangyuanapi.conf.ControlSystemConf;
import com.ruoyi.fangyuanapi.conf.OperationConf;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("control")
public class DbControlSystemController {

    @Autowired
    private ControlSystemConf controlSystemConf;

    @Autowired
    private IDbEquipmentService equipmentService;

    @Autowired
    private RemoteTcpService remoteTcpService;

    @Autowired
    private OperationConf operationConf;

    @Autowired
    private OperationLogUtils operationLogUtils;

    @GetMapping("getControlSystem/{type}")
    @ApiOperation(notes = "方圆智控平台获取操作集接口",value = "如未返回天气则默认为0",httpMethod = "POST")
    @ApiImplicitParam(name = "type",value = "针对不同地区类型不同：0榆社，1韩康",required = true)
    public R get(@PathVariable Integer type){
        System.out.println(operationLogUtils.toOperationText("5", "start"));
        String s = controlSystemConf.getMap().get(type);
        String[] ids = s.split(",");
        ArrayList<Map<String,String>> list = new ArrayList<>();
        for (String id : ids) {
            String[][] arrs = operationConf.getArrs();
            System.out.println(Arrays.toString(arrs));
            DbEquipment equipment = equipmentService.selectDbEquipmentById(Long.valueOf(id));
            HashMap<String, String> map = new HashMap<>();
            map.put("heartbeatText",equipment.getHeartbeatText());
            map.put("equipmentName",equipment.getEquipmentName());
            map.put("handlerText",equipment.getHandlerText());
            map.put("equipmentNo",equipment.getEquipmentNo()+"");
            map.put("isOnline",equipment.getIsOnline()+"");
            map.put("isFault",equipment.getIsFault()+"");
            DbTcpType dbTcpType = new DbTcpType();
            dbTcpType.setHeartName(equipment.getHeartbeatText());
            List<DbTcpType> dbTcpTypes = remoteTcpService.list(dbTcpType);
            if (dbTcpTypes != null && dbTcpTypes.size()>0){
                DbTcpType tcpType = dbTcpTypes.get(0);
                map.put("co2",tcpType.getCo2());
                map.put("temperatureSoil",tcpType.getTemperatureSoil());
                map.put("humiditySoil",tcpType.getHumiditySoil());
                map.put("light",tcpType.getLight());
            }
            list.add(map);
        }
        return R.data(list);
    }

    @PostMapping ("sendInstruct")
    @ApiOperation(notes = "单发指令", value = "方圆智控平台单发指令接口",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "heartbeatText",value = "心跳名",required = true),
            @ApiImplicitParam(name = "instruct",value = "指令",required = true),
            @ApiImplicitParam(name = "operationName",value = "指令名称",required = true),
            @ApiImplicitParam(name = "equipmentNo",value = "唯一编号",required = true),
    })
    public  R sendInstruct(String heartbeatText,String instruct,String operationName,String equipmentNo){
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(heartbeatText);
        if (Integer.parseInt(equipmentNo)<10){
            dbOperationVo.setFacility("0"+equipmentNo);
        }else {
        dbOperationVo.setFacility(equipmentNo);
        }
        dbOperationVo.setOperationName(operationName);
        dbOperationVo.setOperationText(instruct);
        dbOperationVo.setCreateTime(new Date());
        dbOperationVo.setOperationTextType("05");
        return remoteTcpService.operation(dbOperationVo);
    }

}
