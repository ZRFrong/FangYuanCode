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
@Api("提醒")
@RequestMapping("remind")
public class DbRemindController extends BaseController
{

	@Autowired
	private IDbRemindService dbRemindService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbRemind get(  @PathVariable("id") Long id)
	{
		return dbRemindService.selectDbRemindById(id);

	}

	/**
	 * 查询提醒列表
	 */
	@GetMapping("list")
	public R list( DbRemind dbRemind)
	{
		startPage();
		return result(dbRemindService.selectDbRemindList(dbRemind));
	}


	/**
	 * 新增保存提醒
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbRemind dbRemind)
	{
		return toAjax(dbRemindService.insertDbRemind(dbRemind));
	}

	/**
	 * 修改保存提醒
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbRemind dbRemind)
	{
		return toAjax(dbRemindService.updateDbRemind(dbRemind));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{
		return toAjax(dbRemindService.deleteDbRemindByIds(ids));
	}



}