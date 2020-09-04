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
import com.ruoyi.fangyuanapi.domain.DbAttention;
import com.ruoyi.fangyuanapi.service.IDbAttentionService;

/**
 * 关注和被关注 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("attention")
public class DbAttentionController extends BaseController
{
	
	@Autowired
	private IDbAttentionService dbAttentionService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbAttention get(@PathVariable("id") Long id)
	{
		return dbAttentionService.selectDbAttentionById(id);
		
	}
	
	/**
	 * 查询关注和被关注列表
	 */
	@GetMapping("list")
	public R list(DbAttention dbAttention)
	{
		startPage();
        return result(dbAttentionService.selectDbAttentionList(dbAttention));
	}
	
	
	/**
	 * 新增保存关注和被关注
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbAttention dbAttention)
	{		
		return toAjax(dbAttentionService.insertDbAttention(dbAttention));
	}

	/**
	 * 修改保存关注和被关注
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbAttention dbAttention)
	{		
		return toAjax(dbAttentionService.updateDbAttention(dbAttention));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbAttentionService.deleteDbAttentionByIds(ids));
	}
	
}
