package com.ruoyi.fangyuanapi.controller;

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
import com.ruoyi.system.domain.DbRemind;
import com.ruoyi.fangyuanapi.service.IDbRemindService;

/**
 * 提醒 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("remind")
@RequestMapping("remind")
public class DbRemindController extends BaseController
{

	@Autowired
	private IDbRemindService dbRemindService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbRemind get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbRemindService.selectDbRemindById(id);

	}

	/**
	 * 查询提醒列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询提醒列表" , notes = "提醒列表")
	public R list(@ApiParam(name="DbRemind",value="传入json格式",required=true) DbRemind dbRemind)
	{
		startPage();
		return result(dbRemindService.selectDbRemindList(dbRemind));
	}


	/**
	 * 新增保存提醒
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存提醒" , notes = "新增保存提醒")
	public R addSave(@ApiParam(name="DbRemind",value="传入json格式",required=true) @RequestBody DbRemind dbRemind)
	{
		return toAjax(dbRemindService.insertDbRemind(dbRemind));
	}

	/**
	 * 修改保存提醒
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存提醒" , notes = "修改保存提醒")
	public R editSave(@ApiParam(name="DbRemind",value="传入json格式",required=true) @RequestBody DbRemind dbRemind)
	{
		return toAjax(dbRemindService.updateDbRemind(dbRemind));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除提醒" , notes = "删除提醒")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbRemindService.deleteDbRemindByIds(ids));
	}

}