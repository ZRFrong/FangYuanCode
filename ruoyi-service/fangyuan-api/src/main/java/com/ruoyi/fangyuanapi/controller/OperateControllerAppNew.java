package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.json.JSON;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.RemoteTcpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 新版app接口2.0
 * @Author zheng
 * @Date 2021/4/6 10:40
 * @Version 1.0
 */
@RestController
@Api("operateNewApp")
@RequestMapping("operateNewApp")
public class OperateControllerAppNew extends BaseController {

    @Autowired
    private IDbLandService landService;

    @Autowired
    private IDbEquipmentService equipmentService;


    @Autowired
    private RemoteTcpService remoteTcpService;

    /*
     *列表回写    当前用户下边所有土地  DbLandAppVo
     *
     * */
    @GetMapping("showList")
    @ApiOperation(value = "查询当前用户下的操作列表", notes = "查询当前用户下的操作列表")
    public R showList() {
//            {{传感，功能集{功能名称，标识，状态，进度，指令{指令集}}}}
//        long currentUserId = getCurrentUserId();
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        List<DbLandAppVo> dbLandAppVos = new ArrayList<>();
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(247L);
        List<DbLand> dbLands = landService.selectDbLandListByUserIdAndSideId(Long.valueOf(247L));

        dbLands.stream().forEach(ite -> {
            Arrays.asList(ite.getEquipmentIds().split(",")).forEach(ite2 -> {
                DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(ite2));
                DbLandAppVo dbLandAppVo = new DbLandAppVo();
                DbTcpType dbTcpType = new DbTcpType();
                dbTcpType.setHeartName(dbEquipment.getHeartbeatText() + "_" + dbEquipment.getEquipmentNoString());
                List<DbTcpType> list = remoteTcpService.list(dbTcpType);
                if (list.size() != 0 && list != null) {
                    DbTcpType dbTcpType1 = list.get(0);
                    dbLandAppVo.setDbTcpType(dbTcpType1);
                }
                List<DbLandAppVo.Function> functions = new ArrayList<DbLandAppVo.Function>();
                DbLandAppVo.Function function = new DbLandAppVo.Function();
                function.setEquipmentName(dbEquipment.getEquipmentName());
                function.setOperatePojos(com.alibaba.fastjson.JSON.parseArray(dbEquipment.getHandlerText(),OperatePojo.class ));
                functions.add(function);
                dbLandAppVo.setFunctions(functions);
                dbLandAppVos.add(dbLandAppVo);
            });
        });

        return R.data(dbLandAppVos);


    }

     /*
     *
     * */


}
