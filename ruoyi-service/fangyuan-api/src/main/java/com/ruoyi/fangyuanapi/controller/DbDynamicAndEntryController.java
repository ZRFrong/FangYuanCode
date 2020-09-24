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
@Api("entry")
@RequestMapping("entry")
public class DbDynamicAndEntryController extends BaseController
{

	@Autowired
	private IDbDynamicAndEntryService dbDynamicAndEntryService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbDynamicAndEntry get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbDynamicAndEntryService.selectDbDynamicAndEntryById(id);

	}

	/**
	 * 查询动态和词条中间列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询动态和词条中间列表" , notes = "动态和词条中间列表")
	public R list(@ApiParam(name="DbDynamicAndEntry",value="传入json格式",required=true) DbDynamicAndEntry dbDynamicAndEntry)
	{
		startPage();
		return result(dbDynamicAndEntryService.selectDbDynamicAndEntryList(dbDynamicAndEntry));
	}


	/**
	 * 新增保存动态和词条中间
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存动态和词条中间" , notes = "新增保存动态和词条中间")
	public R addSave(@ApiParam(name="DbDynamicAndEntry",value="传入json格式",required=true) @RequestBody DbDynamicAndEntry dbDynamicAndEntry)
	{
		return toAjax(dbDynamicAndEntryService.insertDbDynamicAndEntry(dbDynamicAndEntry));
	}

	/**
	 * 修改保存动态和词条中间
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存动态和词条中间" , notes = "修改保存动态和词条中间")
	public R editSave(@ApiParam(name="DbDynamicAndEntry",value="传入json格式",required=true) @RequestBody DbDynamicAndEntry dbDynamicAndEntry)
	{
		return toAjax(dbDynamicAndEntryService.updateDbDynamicAndEntry(dbDynamicAndEntry));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除动态和词条中间" , notes = "删除动态和词条中间")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbDynamicAndEntryService.deleteDbDynamicAndEntryByIds(ids));
	}

}