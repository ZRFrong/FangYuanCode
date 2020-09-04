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
import com.ruoyi.fangyuanapi.domain.DbUser;
import com.ruoyi.fangyuanapi.service.IDbUserService;

/**
 * 前台用户 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("homeUser")
public class DbUserController extends BaseController
{
	
	@Autowired
	private IDbUserService dbUserService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbUser get(@PathVariable("id") Long id)
	{
		return dbUserService.selectDbUserById(id);
		
	}
	
	/**
	 * 查询前台用户列表
	 */
	@GetMapping("list")
	public R list(DbUser dbUser)
	{
		startPage();
        return result(dbUserService.selectDbUserList(dbUser));
	}
	
	
	/**
	 * 新增保存前台用户
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbUser dbUser)
	{		
		return toAjax(dbUserService.insertDbUser(dbUser));
	}

	/**
	 * 修改保存前台用户
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbUser dbUser)
	{		
		return toAjax(dbUserService.updateDbUser(dbUser));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbUserService.deleteDbUserByIds(ids));
	}
	
}
