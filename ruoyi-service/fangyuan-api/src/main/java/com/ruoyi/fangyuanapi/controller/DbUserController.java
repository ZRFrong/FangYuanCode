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
import com.ruoyi.fangyuanapi.domain.DbUser;
import com.ruoyi.fangyuanapi.service.IDbUserService;

/**
 * 前台用户 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("homeUser")
@RequestMapping("homeUser")
public class DbUserController extends BaseController
{

	@Autowired
	private IDbUserService dbUserService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbUser get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbUserService.selectDbUserById(id);

	}

	/**
	 * 查询前台用户列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询前台用户列表" , notes = "前台用户列表")
	public R list(@ApiParam(name="DbUser",value="传入json格式",required=true) DbUser dbUser)
	{
		startPage();
		return result(dbUserService.selectDbUserList(dbUser));
	}


	/**
	 * 新增保存前台用户
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存前台用户" , notes = "新增保存前台用户")
	public R addSave(@ApiParam(name="DbUser",value="传入json格式",required=true) @RequestBody DbUser dbUser)
	{
		return toAjax(dbUserService.insertDbUser(dbUser));
	}

	/**
	 * 修改保存前台用户
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存前台用户" , notes = "修改保存前台用户")
	public R editSave(@ApiParam(name="DbUser",value="传入json格式",required=true) @RequestBody DbUser dbUser)
	{
		return toAjax(dbUserService.updateDbUser(dbUser));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除前台用户" , notes = "删除前台用户")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbUserService.deleteDbUserByIds(ids));
	}

}