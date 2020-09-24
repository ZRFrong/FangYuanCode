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
import com.ruoyi.system.domain.DbUserAndDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserAndDynamicService;

/**
 * 前台用户和动态中间 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("dynamic")
@RequestMapping("dynamic")
public class DbUserAndDynamicController extends BaseController
{

	@Autowired
	private IDbUserAndDynamicService dbUserAndDynamicService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbUserAndDynamic get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbUserAndDynamicService.selectDbUserAndDynamicById(id);

	}

	/**
	 * 查询前台用户和动态中间列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询前台用户和动态中间列表" , notes = "前台用户和动态中间列表")
	public R list(@ApiParam(name="DbUserAndDynamic",value="传入json格式",required=true) DbUserAndDynamic dbUserAndDynamic)
	{
		startPage();
		return result(dbUserAndDynamicService.selectDbUserAndDynamicList(dbUserAndDynamic));
	}


	/**
	 * 新增保存前台用户和动态中间
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存前台用户和动态中间" , notes = "新增保存前台用户和动态中间")
	public R addSave(@ApiParam(name="DbUserAndDynamic",value="传入json格式",required=true) @RequestBody DbUserAndDynamic dbUserAndDynamic)
	{
		return toAjax(dbUserAndDynamicService.insertDbUserAndDynamic(dbUserAndDynamic));
	}

	/**
	 * 修改保存前台用户和动态中间
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存前台用户和动态中间" , notes = "修改保存前台用户和动态中间")
	public R editSave(@ApiParam(name="DbUserAndDynamic",value="传入json格式",required=true) @RequestBody DbUserAndDynamic dbUserAndDynamic)
	{
		return toAjax(dbUserAndDynamicService.updateDbUserAndDynamic(dbUserAndDynamic));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除前台用户和动态中间" , notes = "删除前台用户和动态中间")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbUserAndDynamicService.deleteDbUserAndDynamicByIds(ids));
	}

}