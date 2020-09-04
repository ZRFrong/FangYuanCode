package com.ruoyi.fangyuanapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.domain.DbUserLogin;
import com.ruoyi.fangyuanapi.service.IDbUserLoginService;

/**
 * 登录日志 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("UserLoginLog")
public class DbUserLoginController extends BaseController
{
	
	@Autowired
	private IDbUserLoginService dbUserLoginService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbUserLogin get(@PathVariable("id") Long id)
	{
		return dbUserLoginService.selectDbUserLoginById(id);
		
	}
	
	/**
	 * 查询登录日志列表
	 */
	@GetMapping("list")
	public R list(DbUserLogin dbUserLogin)
	{
		startPage();
        return result(dbUserLoginService.selectDbUserLoginList(dbUserLogin));
	}
	
	
	/**
	 * 新增保存登录日志
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbUserLogin dbUserLogin)
	{		
		return toAjax(dbUserLoginService.insertDbUserLogin(dbUserLogin));
	}

	/**
	 * 修改保存登录日志
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbUserLogin dbUserLogin)
	{		
		return toAjax(dbUserLoginService.updateDbUserLogin(dbUserLogin));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbUserLoginService.deleteDbUserLoginByIds(ids));
	}
	
}
