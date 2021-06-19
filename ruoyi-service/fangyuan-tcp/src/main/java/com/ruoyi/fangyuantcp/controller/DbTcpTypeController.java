package com.ruoyi.fangyuantcp.controller;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.fangyuantcp.aspect.EquipmentTag;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.service.IDbStateRecordsService;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.feign.DbLandClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @Autowired
    private IDbStateRecordsService stateRecordsService;

    @Autowired
    private DbLandClient dbLandClient;

    private SendCodeUtils sendCodeUtils = new SendCodeUtils();

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
    @PostMapping("listonly")
    @EquipmentTag
    public List<DbTcpType> listonly(@RequestBody DbTcpType dbTcpType) {
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

//    /*
//     * 状态留根
//     * */
//    @GetMapping("curingType")
//    public R curingTypeTiming() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                dbTcpTypeService.curingTypeTiming();
//            }
//        });
//        return R.ok();
//    }

    /*
     * 状态查询 温度湿度等
     * */
    @GetMapping("timingType")
    public R timingType() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                dbTcpTypeService.timingType();
            }
        });
        return R.ok();
    }

    /*
     *通风 自动手动监测
     * */
    @GetMapping("timingTongFengHand")
    public R timingTongFengHand() throws ExecutionException, InterruptedException {
        dbTcpTypeService.timingTongFengHand();

        return toAjax(1);

    }


    /*
     * 通风当前的开风口，关风口温度查询
     * */
    @GetMapping("saveTongFengType")
    public R timingTongFengType() throws ExecutionException, InterruptedException {
        dbTcpTypeService.timingTongFengType();
        return toAjax(1);

    }


    /*
     * 根据描述返回状态的变化集
     * */
    @GetMapping("intervalState/{startTime}/{endTime}/{interval}/{hearName}")
    public R intervalState(
            @ApiParam(name = "开始时间", value = "string") @PathVariable("startTime") String startTime,
            @ApiParam(name = "结束时间", value = "string", required = true) @PathVariable("endTime") String endTime,
            @ApiParam(name = "单位小时", value = "interval", required = true) @PathVariable("interval") String INterval,
            @ApiParam(name = "心跳名称", value = "hearName", required = true) @PathVariable("hearName") String hearName
    ) {
        /*
         *
         * */
        List<DbStateRecords> dbStateRecords = dbTcpTypeService.intervalState(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, startTime), DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, endTime), INterval, hearName);
        List<DbStateRecords> dbStateRecords1 = new ArrayList<>();
        for (DbStateRecords dbStateRecord : dbStateRecords) {
            dbStateRecord.setType(JSON.parseObject(dbStateRecord.getStateJson(), DbTcpType.class));
            dbStateRecords1.add(dbStateRecord);
        }

        return R.data(dbStateRecords1);
    }

   /**
    * @Author Mr.Zhao
    * @Description 获取温湿度曲线
    * @Date 10:57 2021/6/19
    * @param heartName
    * @param strips
    * @param curveType
    * @return com.ruoyi.common.core.domain.R
    * @sign 他日若遂凌云志,敢笑黄巢不丈夫!
    **/
    @GetMapping("getStateCurveData/{heartName}/{strips}/{curveType}")
    public R getStateCurveData(@PathVariable("heartName") String heartName,@PathVariable("strips") Integer strips,@PathVariable("curveType") Integer curveType){
        strips = strips == null || strips<12 ? 12 : strips;
        return dbTcpTypeService.getStateCurveData(heartName,strips,curveType);
    }

    /*
     *所有状态更新
     * */
    @PostMapping("stateAllQuery")
    public R stateAllQuery(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true) @RequestBody List<DbOperationVo> dbOperationVo) throws ExecutionException, InterruptedException {
        return dbTcpTypeService.stateAllQuery(dbOperationVo);

    }


    public static void main(String[] args){
        System.out.println(new Date());
    }

}
