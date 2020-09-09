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
import com.ruoyi.fangyuanapi.domain.DbUserDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;

/**
 * 动态 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("dynamic")
@RequestMapping("dynamic1")
public class DbUserDynamicController extends BaseController
{

	@Autowired
	private IDbUserDynamicService dbUserDynamicService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbUserDynamic get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbUserDynamicService.selectDbUserDynamicById(id);

	}

	/**
	 * 查询动态列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询动态列表" , notes = "动态列表")
	public R list(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) DbUserDynamic dbUserDynamic)
	{
		startPage();
		return result(dbUserDynamicService.selectDbUserDynamicList(dbUserDynamic));
	}


	/**
	 * 新增保存动态
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存动态" , notes = "新增保存动态")
	public R addSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
	{
		return toAjax(dbUserDynamicService.insertDbUserDynamic(dbUserDynamic));
	}

	/**
	 * 修改保存动态
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存动态" , notes = "修改保存动态")
	public R editSave(@ApiParam(name="DbUserDynamic",value="传入json格式",required=true) @RequestBody DbUserDynamic dbUserDynamic)
	{
		return toAjax(dbUserDynamicService.updateDbUserDynamic(dbUserDynamic));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除动态" , notes = "删除动态")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbUserDynamicService.deleteDbUserDynamicByIds(ids));
	}

}