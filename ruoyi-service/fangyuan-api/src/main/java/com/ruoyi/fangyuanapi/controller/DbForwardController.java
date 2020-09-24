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
import com.ruoyi.system.domain.DbForward;
import com.ruoyi.fangyuanapi.service.IDbForwardService;

/**
 * 转发 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("forward")
@RequestMapping("forward")
public class DbForwardController extends BaseController
{

	@Autowired
	private IDbForwardService dbForwardService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbForward get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbForwardService.selectDbForwardById(id);

	}

	/**
	 * 查询转发列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询转发列表" , notes = "转发列表")
	public R list(@ApiParam(name="DbForward",value="传入json格式",required=true) DbForward dbForward)
	{
		startPage();
		return result(dbForwardService.selectDbForwardList(dbForward));
	}


	/**
	 * 新增保存转发
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存转发" , notes = "新增保存转发")
	public R addSave(@ApiParam(name="DbForward",value="传入json格式",required=true) @RequestBody DbForward dbForward)
	{
		return toAjax(dbForwardService.insertDbForward(dbForward));
	}

	/**
	 * 修改保存转发
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存转发" , notes = "修改保存转发")
	public R editSave(@ApiParam(name="DbForward",value="传入json格式",required=true) @RequestBody DbForward dbForward)
	{
		return toAjax(dbForwardService.updateDbForward(dbForward));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除转发" , notes = "删除转发")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbForwardService.deleteDbForwardByIds(ids));
	}

}