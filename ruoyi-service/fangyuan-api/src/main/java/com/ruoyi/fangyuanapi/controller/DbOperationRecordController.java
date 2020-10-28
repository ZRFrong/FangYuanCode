package com.ruoyi.fangyuanapi.controller;

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

import java.util.List;

/**
 * 用户操作记录 提供者
 * 
 * @author zheng
 * @date 2020-10-16
 */
@RestController
@Api("operationRecord")
@RequestMapping("operationRecord")
public class DbOperationRecordController extends BaseController
{
	
	@Autowired
	private IDbOperationRecordService dbOperationRecordService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbOperationRecord get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbOperationRecordService.selectDbOperationRecordById(id);
		
	}
	
	/**
	 * 查询用户操作记录列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询用户操作记录列表" , notes = "用户操作记录列表")
	public R list(@ApiParam(name="DbOperationRecord",value="传入json格式",required=true) DbOperationRecord dbOperationRecord)
	{
		startPage();
        return result(dbOperationRecordService.selectDbOperationRecordList(dbOperationRecord));
	}
	/*
	* 当天的用户操作记录  按照天统计
	* */

	public  R listGroupDay (){
//	    日期分组的操作记录
        List<DbOperationRecord> objects = dbOperationRecordService.listGroupDay();

        return result(objects);
	}




	
	
	/**
	 * 新增保存用户操作记录
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存用户操作记录" , notes = "新增保存用户操作记录")
	public R addSave(@ApiParam(name="DbOperationRecord",value="传入json格式",required=true) @RequestBody DbOperationRecord dbOperationRecord)
	{		
		return toAjax(dbOperationRecordService.insertDbOperationRecord(dbOperationRecord));
	}

	/**
	 * 修改保存用户操作记录
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存用户操作记录" , notes = "修改保存用户操作记录")
	public R editSave(@ApiParam(name="DbOperationRecord",value="传入json格式",required=true) @RequestBody DbOperationRecord dbOperationRecord)
	{		
		return toAjax(dbOperationRecordService.updateDbOperationRecord(dbOperationRecord));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除用户操作记录" , notes = "删除用户操作记录")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbOperationRecordService.deleteDbOperationRecordByIds(ids));
	}
	
}
