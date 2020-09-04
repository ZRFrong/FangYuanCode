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
import com.ruoyi.fangyuanapi.domain.DbUserAndDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserAndDynamicService;

/**
 * 前台用户和动态中间 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("dynamic1")
public class DbUserAndDynamicController extends BaseController
{
	
	@Autowired
	private IDbUserAndDynamicService dbUserAndDynamicService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbUserAndDynamic get(@PathVariable("id") Long id)
	{
		return dbUserAndDynamicService.selectDbUserAndDynamicById(id);
		
	}
	
	/**
	 * 查询前台用户和动态中间列表
	 */
	@GetMapping("list")
	public R list(DbUserAndDynamic dbUserAndDynamic)
	{
		startPage();
        return result(dbUserAndDynamicService.selectDbUserAndDynamicList(dbUserAndDynamic));
	}
	
	
	/**
	 * 新增保存前台用户和动态中间
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbUserAndDynamic dbUserAndDynamic)
	{		
		return toAjax(dbUserAndDynamicService.insertDbUserAndDynamic(dbUserAndDynamic));
	}

	/**
	 * 修改保存前台用户和动态中间
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbUserAndDynamic dbUserAndDynamic)
	{		
		return toAjax(dbUserAndDynamicService.updateDbUserAndDynamic(dbUserAndDynamic));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbUserAndDynamicService.deleteDbUserAndDynamicByIds(ids));
	}
	
}
