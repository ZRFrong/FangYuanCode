package com.ruoyi.fangyuantcp.controller;

import com.ruoyi.system.domain.DbStateRecords;
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
import com.ruoyi.fangyuantcp.service.IDbStateRecordsService;

/**
 * 状态记录 提供者
 * 
 * @author 正
 * @date 2020-09-23
 */
@RestController
@Api("状态记录")
@RequestMapping("records")
public class DbStateRecordsController extends BaseController
{
	
	@Autowired
	private IDbStateRecordsService dbStateRecordsService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{stateRecordsId}")
	public DbStateRecords get(  @PathVariable("s tateRecordsId") Long stateRecordsId)
	{
		return dbStateRecordsService.selectDbStateRecordsById(stateRecordsId);

	}

	/**
	 * 查询状态记录列表
	 */
	@GetMapping("list")
	public R list( DbStateRecords dbStateRecords)
	{
		startPage();
        return result(dbStateRecordsService.selectDbStateRecordsList(dbStateRecords));
	}
	
	
	/**
	 * 新增保存状态记录
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbStateRecords dbStateRecords)
	{		
		return toAjax(dbStateRecordsService.insertDbStateRecords(dbStateRecords));
	}

	/**
	 * 修改保存状态记录
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbStateRecords dbStateRecords)
	{		
		return toAjax(dbStateRecordsService.updateDbStateRecords(dbStateRecords));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{		
		return toAjax(dbStateRecordsService.deleteDbStateRecordsByIds(ids));
	}
	
}
