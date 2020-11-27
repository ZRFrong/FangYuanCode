package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.system.domain.DbEquipment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("dbEquipment")
@RequestMapping("dbEquipment")
public class DbEquipmentController extends BaseController {

    @Autowired
    private IDbEquipmentService dbEquipmentService;



    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{equipmentId}")
    public DbEquipment get( @PathVariable("equipmentId") Long equipmentId) {
        return dbEquipmentService.selectDbEquipmentById(equipmentId);
    }

    /**
     * 查询设备列表
     */
    @GetMapping("list")
    public R list( DbEquipment dbEquipment) {
        startPage();
        return result(dbEquipmentService.selectDbEquipmentList(dbEquipment));
    }


    /**
     * 新增保存设备
     */
    @PostMapping("save")

    public R addSave( DbEquipment dbEquipment) {
        return toAjax(dbEquipmentService.insertDbEquipment(dbEquipment));
    }

    /**
     * 修改保存设备
     */
    @PostMapping("update")
    public R editSave( DbEquipment dbEquipment) {
        return toAjax(dbEquipmentService.updateDbEquipment(dbEquipment));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove( String ids) {
        return toAjax(dbEquipmentService.deleteDbEquipmentByIds(ids));
    }














}