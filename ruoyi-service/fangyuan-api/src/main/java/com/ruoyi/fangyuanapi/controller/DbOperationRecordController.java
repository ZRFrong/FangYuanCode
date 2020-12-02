package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbOperationRecord;
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
import com.ruoyi.fangyuanapi.service.IDbOperationRecordService;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户操作记录 提供者
 *
 * @author zheng
 * @date 2020-10-16
 */
@RestController
@Api("用户操作记录")
@RequestMapping("operationRecord")
public class DbOperationRecordController extends BaseController {

    @Autowired
    private IDbOperationRecordService dbOperationRecordService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public DbOperationRecord get(@PathVariable("id") Long id) {
        return dbOperationRecordService.selectDbOperationRecordById(id);

    }

    /**
     * 查询用户操作记录列表
     */
    @GetMapping("list")
    public R list(DbOperationRecord dbOperationRecord) {
        startPage();
        return result(dbOperationRecordService.selectDbOperationRecordList(dbOperationRecord));
    }

    /**
     * 查询用户操作记录列表
     */
    @GetMapping("listApp")
    public R listApp(DbOperationRecord dbOperationRecord) {
        startPage();
        return result(dbOperationRecordService.selectDbOperationRecordList(dbOperationRecord));
    }



    /*
     * 当天的用户操作记录  默认显示当天的
     * */

    @GetMapping("listGroupDay")
    @ApiOperation(value = "查询操作记录列表", notes = "pagesize,pageName后边跟参即可，拦截会进行处理")
    public R listGroupDay(@ApiParam(name = "operationTime", value = "date", required = false) String operationTime,
                          @ApiParam(name = "operationText", value = "string", required = false) String operationText,
                          @ApiParam(name = "LandId", value = "long", required = false) String LandId) {
//        String header = getRequest().getHeader(Constants.CURRENT_ID);
        DbOperationRecord dbOperationRecord = new DbOperationRecord();
//        dbOperationRecord.setDbUserId(Long.valueOf(header));
        dbOperationRecord.setDbUserId(Long.valueOf("1"));
        if (!operationText.isEmpty()) {
            dbOperationRecord.setOperationText(operationText);
        } else if (!operationTime.isEmpty()) {
            dbOperationRecord.setOperationTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS,operationTime));
        } else if (!LandId.isEmpty()) {
            dbOperationRecord.setOperationObject(LandId);
        }

//	    日期分组的操作记录
        startPage();
        List<DbOperationRecord> objects = dbOperationRecordService.listGroupDay(dbOperationRecord);

        return result(objects);
    }


    /**
     * 新增保存用户操作记录
     */
    @PostMapping("save")
    public R addSave(@RequestBody DbOperationRecord dbOperationRecord) {
        return toAjax(dbOperationRecordService.insertDbOperationRecord(dbOperationRecord));
    }

    /**
     * 修改保存用户操作记录
     */
    @PostMapping("update")
    public R editSave(@RequestBody DbOperationRecord dbOperationRecord) {
        return toAjax(dbOperationRecordService.updateDbOperationRecord(dbOperationRecord));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public R remove(String ids) {
        return toAjax(dbOperationRecordService.deleteDbOperationRecordByIds(ids));
    }

}
