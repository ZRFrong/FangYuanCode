package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;

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
    public R list(@ApiParam(name = "DbTcpType", value = "传入json格式", required = true)@RequestBody DbTcpType dbTcpType) {
        startPage();
        return result(dbTcpTypeService.selectDbTcpTypeList(dbTcpType));
    }

    /**
     * 查询设备状态列表
     */
    @RequestMapping("listonly")
    public List<DbTcpType> listonly( DbTcpType dbTcpType) {
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
        int operation =   dbTcpTypeService.timingTongFengHand();

        return toAjax(operation);

    }


    /*
     * 通风当前的开风口，关风口温度查询
     * */
    @GetMapping("timingTongFengType")
    public R timingTongFengType() {
        int operation =   dbTcpTypeService.timingTongFengType();

        return toAjax(operation);

    }

    /*
    * 通风 自动手动状态更改
    * */
    @GetMapping("operateTongFengHand")
    public R operateTongFengHand(@ApiParam(name = "DbEquipment", value = "DbEquipment")DbEquipment dbEquipment, @ApiParam(name = "i", value = "是否开启0,1")int i) {
        int operation =   dbTcpTypeService.operateTongFengHand(dbEquipment,i);

        return toAjax(operation);

    }

    /*
    * 自动通风  开启关闭温度修改
    * */
    @GetMapping("operateTongFengType")
    public R operateTongFengType(@ApiParam(name = "DbEquipment", value = "DbEquipment")DbEquipment dbEquipment, @ApiParam(name = "i", value = "是否开启0,1")int i, @ApiParam(name = "temp", value = "温度")String temp) {
        int operation =   dbTcpTypeService.operateTongFengType(dbEquipment,i,temp);

        return toAjax(operation);

    }

}