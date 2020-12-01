package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbStateRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;

import javax.naming.Name;
import java.util.Date;
import java.util.List;

/**
 * 设备状态 提供者     4g模块在线否
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("type")
@RequestMapping("type")
public class DbTcpTypeController extends BaseController {

    @Autowired
    private IDbTcpTypeService dbTcpTypeService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{tcpTypeId}")
    @ApiOperation(value = "根据id查询", notes = "查询${tableComment}")
    public DbTcpType get(@ApiParam(name = "id", value = "long", required = true) @PathVariable("tcpTypeId") Long tcpTypeId) {
        return dbTcpTypeService.selectDbTcpTypeById(tcpTypeId);

    }

    /**
     * 查询设备状态列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询设备状态列表", notes = "设备状态列表")
    public R list(@ApiParam(name = "DbTcpType", value = "传入json格式", required = true) @RequestBody DbTcpType dbTcpType) {
        startPage();
        return result(dbTcpTypeService.selectDbTcpTypeList(dbTcpType));
    }

    /**
     * 查询设备状态列表
     */
    @RequestMapping("listonly")
    public List<DbTcpType> listonly(DbTcpType dbTcpType) {
        List<DbTcpType> list = dbTcpTypeService.selectDbTcpTypeList(dbTcpType);
        return list;
    }


    /**
     * 新增保存设备状态
     */
    @PostMapping("save")
    @ApiOperation(value = "新增保存设备状态", notes = "新增保存设备状态")
    public R addSave(@ApiParam(name = "DbTcpType", value = "传入json格式", required = true) @RequestBody DbTcpType dbTcpType) {
        return toAjax(dbTcpTypeService.insertDbTcpType(dbTcpType));
    }

    /**
     * 修改保存设备状态
     */
    @PostMapping("update")
    @ApiOperation(value = "修改保存设备状态", notes = "修改保存设备状态")
    public R editSave(@ApiParam(name = "DbTcpType", value = "传入json格式", required = true) @RequestBody DbTcpType dbTcpType) {
        return toAjax(dbTcpTypeService.updateDbTcpType(dbTcpType));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    @ApiOperation(value = "删除设备状态", notes = "删除设备状态")
    public R remove(@ApiParam(name = "删除的id子串", value = "已逗号分隔的id集", required = true) String ids) {
        return toAjax(dbTcpTypeService.deleteDbTcpTypeByIds(ids));
    }

    /*
     * 状态留根
     * */
    @GetMapping("curingType")
    public R curingTypeTiming() {
        dbTcpTypeService.curingTypeTiming();
        return R.ok();
    }

    /*
     * 状态查询 温度湿度等
     * */
    @GetMapping("timingType")
    public R timingType() {
        dbTcpTypeService.timingType();
        return R.ok();

    }

    /*
     *通风 自动手动监测
     * */
    @GetMapping("timingTongFengHand")
    public R timingTongFengHand() {
        int operation = dbTcpTypeService.timingTongFengHand();

        return toAjax(operation);

    }


    /*
     * 通风当前的开风口，关风口温度查询
     * */
    @GetMapping("saveTongFengType")
    public R timingTongFengType() {
        int operation = dbTcpTypeService.timingTongFengType();

        return toAjax(operation);

    }

    /*
     * 通风 自动手动状态更改
     * */
    @GetMapping("operateTongFengHand/{heartbeatText}/{equipmentNo}/{i}")
    public R operateTongFengHand(@ApiParam(name = "heartbeatText", value = "string") @PathVariable String heartbeatText,
                                 @ApiParam(name = "equipmentNo", value = "string", required = true) @PathVariable("equipmentNo") String equipmentNo,
                                 @ApiParam(name = "i", value = "inter", required = true) @PathVariable("i") Integer i) {
        int operation = dbTcpTypeService.operateTongFengHand(heartbeatText, equipmentNo, i);

        return toAjax(operation);

    }

    /*
     * 自动通风  开启关闭温度修改
     * */
    @GetMapping("operateTongFengType/{heartbeatText}/{equipmentNo}/{i}/{temp}")
    public R operateTongFengType(@ApiParam(name = "heartbeatText", value = "string") @PathVariable("heartbeatText") String heartbeatText,
                                 @ApiParam(name = "equipmentNo", value = "string", required = true) @PathVariable("equipmentNo") String equipmentNo,
                                 @ApiParam(name = "i", value = "inter", required = true) @PathVariable("i") Integer i,
                                 @ApiParam(name = "temp", value = "温度") @PathVariable("temp") String temp) {
        int operation = dbTcpTypeService.operateTongFengType(heartbeatText, equipmentNo, i, temp);

        return toAjax(operation);

    }


    /*
     * 根据描述返回状态的变化集
     * */
    @GetMapping("intervalState/{startTime}/{endTime}/{INterval}")
    public R intervalState(
            @ApiParam(name = "开始时间", value = "date") @PathVariable("startTime") Date startTime,
            @ApiParam(name = "结束时间", value = "date", required = true) @PathVariable("equipmentNo") Date endTime,
            @ApiParam(name = "指定条数", value = "Integer", required = true) @PathVariable("INterval") Integer INterval
    ) {
        /*
        *
        * */
          List<DbStateRecords> dbStateRecords= dbTcpTypeService.intervalState(startTime,endTime,INterval);
        return null;
    }


}