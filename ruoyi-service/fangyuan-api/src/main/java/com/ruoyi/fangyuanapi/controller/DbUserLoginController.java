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
import com.ruoyi.system.domain.DbUserLogin;
import com.ruoyi.fangyuanapi.service.IDbUserLoginService;

/**
 * 登录日志 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("UserLoginLog")
@RequestMapping("UserLoginLog")
public class DbUserLoginController extends BaseController
{

	@Autowired
	private IDbUserLoginService dbUserLoginService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbUserLogin get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbUserLoginService.selectDbUserLoginById(id);

	}

	/**
	 * 查询登录日志列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询登录日志列表" , notes = "登录日志列表")
	public R list(@ApiParam(name="DbUserLogin",value="传入json格式",required=true) DbUserLogin dbUserLogin)
	{
		startPage();
		return result(dbUserLoginService.selectDbUserLoginList(dbUserLogin));
	}


	/**
	 * 新增保存登录日志
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存登录日志" , notes = "新增保存登录日志")
	public R addSave(@ApiParam(name="DbUserLogin",value="传入json格式",required=true) @RequestBody DbUserLogin dbUserLogin)
	{
		return toAjax(dbUserLoginService.insertDbUserLogin(dbUserLogin));
	}

	/**
	 * 修改保存登录日志
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存登录日志" , notes = "修改保存登录日志")
	public R editSave(@ApiParam(name="DbUserLogin",value="传入json格式",required=true) @RequestBody DbUserLogin dbUserLogin)
	{
		return toAjax(dbUserLoginService.updateDbUserLogin(dbUserLogin));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除登录日志" , notes = "删除登录日志")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbUserLoginService.deleteDbUserLoginByIds(ids));
	}

}