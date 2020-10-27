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
import com.ruoyi.system.domain.DbEntry;
import com.ruoyi.fangyuanapi.service.IDbEntryService;

/**
 * 词条 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("entry1")
@RequestMapping("entry1")
public class DbEntryController extends BaseController
{

	@Autowired
	private IDbEntryService dbEntryService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbEntry get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbEntryService.selectDbEntryById(id);

	}

	/**
	 * 查询词条列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询词条列表" , notes = "词条列表")
	public R list(@ApiParam(name="DbEntry",value="传入json格式",required=true) DbEntry dbEntry)
	{
		startPage();
		return result(dbEntryService.selectDbEntryList(dbEntry));
	}


	/**
	 * 新增保存词条
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存词条" , notes = "新增保存词条")
	public R addSave(@ApiParam(name="DbEntry",value="传入json格式",required=true) @RequestBody DbEntry dbEntry)
	{
		return toAjax(dbEntryService.insertDbEntry(dbEntry));
	}

	/**
	 * 修改保存词条
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存词条" , notes = "修改保存词条")
	public R editSave(@ApiParam(name="DbEntry",value="传入json格式",required=true) @RequestBody DbEntry dbEntry)
	{
		return toAjax(dbEntryService.updateDbEntry(dbEntry));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除词条" , notes = "删除词条")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbEntryService.deleteDbEntryByIds(ids));
	}


}