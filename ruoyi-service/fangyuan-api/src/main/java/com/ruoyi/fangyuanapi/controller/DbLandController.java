package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.domain.LandVo;
import com.ruoyi.system.feign.RemoteTcpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.fangyuanapi.service.IDbLandService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 土地 提供者
 *
 * @author zheng
 * @date 2020-09-24
 */
@RestController
@Api("land")
@RequestMapping("land")
public class DbLandController extends BaseController {

    @Autowired
    private IDbLandService dbLandService;


    @Autowired
    private IDbEquipmentService equipmentService;


    @Autowired
    private RemoteTcpService remoteTcpService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{landId}")
    public DbLand get(@PathVariable("landId") Long landId) {
        return dbLandService.selectDbLandById(landId);

    }

    /**
     * 查询土地列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询土地列表", notes = "土地列表")
    public R list(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        startPage();
        return result(dbLandService.selectDbLandList(dbLand));
    }

    /**
     * 查询土地列表
     */
    @GetMapping("listApp")
    @ApiOperation(value = "查询土地列表app", notes = "土地列表")
    public R listApp(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        return R.data(dbLands);
    }

    /**
     * 查询土地列表
     */
    @GetMapping("listBinding")
    @ApiOperation(value = "查询土地列表app", notes = "土地列表")
    public R listBinding(@ApiParam(name = "DbLand", value = "传入json格式", required = true) DbLand dbLand) {
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setSiteId(0l);
        List<LandVo> landVos = new ArrayList<>();
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        for (DbLand land : dbLands) {
            LandVo landVo = new LandVo();
            landVo.setPlotName(land.getNickName());
            DbLand dbLand1 = new DbLand();
            dbLand1.setSiteId(land.getLandId());
            List<DbLand> dbLands2 = dbLandService.selectDbLandList(dbLand1);
            landVo.setLands(dbLands2);
            landVos.add(landVo);
        }

        return R.data(landVos);
    }


    /**
     * 新增保存土地
     */
    @PostMapping("save")
    @ApiOperation(value = "新增土地/地块", notes = "土地/地块id")
    public R addSave(DbLand dbLand, HttpServletRequest request) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setCreateTime(new Date());
        int i = dbLandService.insertDbLand(dbLand);
        return R.data(dbLand.getLandId());
    }

    /**
     * 修改保存土地
     */
    @PostMapping("update")
    public R editSave(@RequestBody DbLand dbLand, HttpServletRequest request) {
        String userId = request.getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        return toAjax(dbLandService.updateDbLand(dbLand));
    }

    /**
     * 删除${tableComment}
     */
    @GetMapping("remove")
    public R remove(String landId) {
        return toAjax(dbLandService.deleteDbLandByIds(landId));
    }

    /*
     * 用户关联地块返回
     * */
    @GetMapping("listPlot")
    public R listPlot(DbLand dbLand) {
//        获取当前用户id
        String userId = getRequest().getHeader(Constants.CURRENT_ID);
        dbLand.setDbUserId(Long.valueOf(userId));
        dbLand.setSiteId(0L);
        startPage();
        return result(dbLandService.selectDbLandList(dbLand));
    }


    /*
     * 根据土地id返回当前的装态
     * */
    @GetMapping("typeNow/{landId}")
    @ApiOperation(value = " 根据土地id返回当前的装态",notes = " 根据土地id返回当前的装态",httpMethod = "GET")
    public R typeNow(@ApiParam(name = "Long", value = "Long格式", required = true) @PathVariable("landId") Long landId) {
        DbLand dbLand = dbLandService.selectDbLandById(landId);
        String equipmentIds = dbLand.getEquipmentIds();
        String s = equipmentIds.split(",")[0];
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(s));
        String heartbeatText = dbEquipment.getHeartbeatText();
        String equipmentNo = dbEquipment.getEquipmentNo();
        String hear = heartbeatText + "_" + equipmentNo;
        DbTcpType dbTcpType = new DbTcpType();
        dbTcpType.setHeartName(hear);
        List<DbTcpType> list = remoteTcpService.list(dbTcpType);
        DbTcpType dbTcpType1 = list.get(0);
        return R.data(dbTcpType1);
    }


}
