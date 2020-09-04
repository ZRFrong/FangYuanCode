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
import com.ruoyi.fangyuanapi.domain.DbForward;
import com.ruoyi.fangyuanapi.service.IDbForwardService;

/**
 * 转发 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("forward")
public class DbForwardController extends BaseController
{
	
	@Autowired
	private IDbForwardService dbForwardService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbForward get(@PathVariable("id") Long id)
	{
		return dbForwardService.selectDbForwardById(id);
		
	}
	
	/**
	 * 查询转发列表
	 */
	@GetMapping("list")
	public R list(DbForward dbForward)
	{
		startPage();
        return result(dbForwardService.selectDbForwardList(dbForward));
	}
	
	
	/**
	 * 新增保存转发
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbForward dbForward)
	{		
		return toAjax(dbForwardService.insertDbForward(dbForward));
	}

	/**
	 * 修改保存转发
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbForward dbForward)
	{		
		return toAjax(dbForwardService.updateDbForward(dbForward));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbForwardService.deleteDbForwardByIds(ids));
	}
	
}
