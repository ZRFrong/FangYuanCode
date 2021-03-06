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
import com.ruoyi.system.domain.DbDynamicAndEntry;
import com.ruoyi.fangyuanapi.service.IDbDynamicAndEntryService;

/**
 * 动态和词条中间 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("动态和词条中间")
@RequestMapping("entry")
public class DbDynamicAndEntryController extends BaseController
{

	@Autowired
	private IDbDynamicAndEntryService dbDynamicAndEntryService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbDynamicAndEntry get( @PathVariable("id") Long id)
	{
		return dbDynamicAndEntryService.selectDbDynamicAndEntryById(id);

	}

	/**
	 * 查询动态和词条中间列表
	 */
	@GetMapping("list")
	public R list( DbDynamicAndEntry dbDynamicAndEntry)
	{
		startPage();
		return result(dbDynamicAndEntryService.selectDbDynamicAndEntryList(dbDynamicAndEntry));
	}


	/**
	 * 新增保存动态和词条中间
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbDynamicAndEntry dbDynamicAndEntry)
	{
		return toAjax(dbDynamicAndEntryService.insertDbDynamicAndEntry(dbDynamicAndEntry));
	}

	/**
	 * 修改保存动态和词条中间
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbDynamicAndEntry dbDynamicAndEntry)
	{
		return toAjax(dbDynamicAndEntryService.updateDbDynamicAndEntry(dbDynamicAndEntry));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{
		return toAjax(dbDynamicAndEntryService.deleteDbDynamicAndEntryByIds(ids));
	}

}