package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbLand;
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
import com.ruoyi.fangyuanapi.service.IDbAbnormalInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * 报警信息 提供者
 *
 * @author zheng
 * @date 2020-12-02
 */
@RestController
@Api("abnormalInfo")
@RequestMapping("abnormalInfo")
public class DbAbnormalInfoController extends BaseController {

    @Autowired
    private IDbAbnormalInfoService dbAbnormalInfoService;

    @Autowired
    private IDbLandService dbLandService;


    @Autowired
    private IDbEquipmentService equipmentService;


    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public DbAbnormalInfo get(@ApiParam(name = "id", value = "long", required = true) @PathVariable("id") Long id) {
        return dbAbnormalInfoService.selectDbAbnormalInfoById(id);

    }

    /**
     * 查询报警信息列表
     */
    @GetMapping("list")
    @ApiOperation(value = "查询报警信息列表", notes = "报警信息列表")
    public R list(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) DbAbnormalInfo dbAbnormalInfo) {
        startPage();
        return result(dbAbnormalInfoService.selectDbAbnormalInfoList(dbAbnormalInfo));
    }


    /**
     * 新增保存报警信息
     */
    @PostMapping("save")
    @ApiOperation(value = "新增保存报警信息", notes = "新增保存报警信息")
    public R addSave(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) @RequestBody DbAbnormalInfo dbAbnormalInfo) {
        return toAjax(dbAbnormalInfoService.insertDbAbnormalInfo(dbAbnormalInfo));
    }

    @PostMapping("saveEquiment")
    public R saveEquiment(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) @RequestBody DbAbnormalInfo dbAbnormalInfo) {
        String objectType = dbAbnormalInfo.getObjectType();
        DbEquipment dbEquipment = equipmentService.selectDbEquipmentById(Long.valueOf(objectType));
        dbAbnormalInfo.setDbEquipmentId(dbEquipment.getEquipmentId());
        return toAjax(dbAbnormalInfoService.insertDbAbnormalInfo(dbAbnormalInfo));
    }

    /**
     * 修改保存报警信息
     */
    @PostMapping("update")
    public R editSave(@ApiParam(name = "DbAbnormalInfo", value = "传入json格式", required = true) @RequestBody DbAbnormalInfo dbAbnormalInfo) {
        return toAjax(dbAbnormalInfoService.updateDbAbnormalInfo(dbAbnormalInfo));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(@ApiParam(name = "删除的id子串", value = "已逗号分隔的id集", required = true) String ids) {
        return toAjax(dbAbnormalInfoService.deleteDbAbnormalInfoByIds(ids));
    }

    /**
     * 查询报警信息列表
     */
    @GetMapping("listApp")
    @ApiOperation(value = "查询报警信息列表", notes = "报警信息列表")
    public R list1(@ApiParam(name = "开始时间", value = "date", required = true) String startTime) {
        String header = getRequest().getHeader(Constants.CURRENT_ID);
        DbLand dbLand = new DbLand();
        dbLand.setDbUserId(Long.valueOf(header));
        List<DbLand> dbLands = dbLandService.selectDbLandList(dbLand);
        List<DbAbnormalInfo> dbAbnormalInfos = new ArrayList<>();
        for (DbLand land : dbLands) {
            String equipmentIds = land.getEquipmentIds();
            DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
            for (String s : equipmentIds.split(",")) {
                if (StringUtils.isEmpty(startTime)) {
                    dbAbnormalInfo.setAlarmExplain(startTime);
                }
                dbAbnormalInfo.setDbEquipmentId(Long.valueOf(s));
                dbAbnormalInfos.addAll(dbAbnormalInfoService.selectDbAbnormalInfoList(dbAbnormalInfo));
            }
        }
        return result1(dbAbnormalInfos);
    }


}
