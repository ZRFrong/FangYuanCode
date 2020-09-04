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
import com.ruoyi.fangyuanapi.domain.DbUserDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;

/**
 * 动态 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("dynamic")
public class DbUserDynamicController extends BaseController
{
	
	@Autowired
	private IDbUserDynamicService dbUserDynamicService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbUserDynamic get(@PathVariable("id") Long id)
	{
		return dbUserDynamicService.selectDbUserDynamicById(id);
		
	}
	
	/**
	 * 查询动态列表
	 */
	@GetMapping("list")
	public R list(DbUserDynamic dbUserDynamic)
	{
		startPage();
        return result(dbUserDynamicService.selectDbUserDynamicList(dbUserDynamic));
	}
	
	
	/**
	 * 新增保存动态
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbUserDynamic dbUserDynamic)
	{		
		return toAjax(dbUserDynamicService.insertDbUserDynamic(dbUserDynamic));
	}

	/**
	 * 修改保存动态
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbUserDynamic dbUserDynamic)
	{		
		return toAjax(dbUserDynamicService.updateDbUserDynamic(dbUserDynamic));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbUserDynamicService.deleteDbUserDynamicByIds(ids));
	}
	
}
