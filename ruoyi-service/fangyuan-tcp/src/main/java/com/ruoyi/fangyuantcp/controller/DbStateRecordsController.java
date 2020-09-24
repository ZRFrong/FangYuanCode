package com.ruoyi.fangyuantcp.controller;

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
import com.ruoyi.system.domain.DbStateRecords;
import com.ruoyi.fangyuantcp.service.IDbStateRecordsService;

/**
 * 状态记录 提供者
 * 
 * @author 正
 * @date 2020-09-23
 */
@RestController
@Api("records")
@RequestMapping("records")
public class DbStateRecordsController extends BaseController
{
	
	@Autowired
	private IDbStateRecordsService dbStateRecordsService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{stateRecordsId}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbStateRecords get(@ApiParam(name="id",value="long",required=true)  @PathVariable("stateRecordsId") Long stateRecordsId)
	{
		return dbStateRecordsService.selectDbStateRecordsById(stateRecordsId);
		
	}
	
	/**
	 * 查询状态记录列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询状态记录列表" , notes = "状态记录列表")
	public R list(@ApiParam(name="DbStateRecords",value="传入json格式",required=true) DbStateRecords dbStateRecords)
	{
		startPage();
        return result(dbStateRecordsService.selectDbStateRecordsList(dbStateRecords));
	}
	
	
	/**
	 * 新增保存状态记录
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存状态记录" , notes = "新增保存状态记录")
	public R addSave(@ApiParam(name="DbStateRecords",value="传入json格式",required=true) @RequestBody DbStateRecords dbStateRecords)
	{		
		return toAjax(dbStateRecordsService.insertDbStateRecords(dbStateRecords));
	}

	/**
	 * 修改保存状态记录
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存状态记录" , notes = "修改保存状态记录")
	public R editSave(@ApiParam(name="DbStateRecords",value="传入json格式",required=true) @RequestBody DbStateRecords dbStateRecords)
	{		
		return toAjax(dbStateRecordsService.updateDbStateRecords(dbStateRecords));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除状态记录" , notes = "删除状态记录")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbStateRecordsService.deleteDbStateRecordsByIds(ids));
	}
	
}
