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
import com.ruoyi.fangyuanapi.domain.DbGiveLike;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;

/**
 * 点赞 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("giveLike")
public class DbGiveLikeController extends BaseController
{
	
	@Autowired
	private IDbGiveLikeService dbGiveLikeService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbGiveLike get(@PathVariable("id") Long id)
	{
		return dbGiveLikeService.selectDbGiveLikeById(id);
		
	}
	
	/**
	 * 查询点赞列表
	 */
	@GetMapping("list")
	public R list(DbGiveLike dbGiveLike)
	{
		startPage();
        return result(dbGiveLikeService.selectDbGiveLikeList(dbGiveLike));
	}
	
	
	/**
	 * 新增保存点赞
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbGiveLike dbGiveLike)
	{		
		return toAjax(dbGiveLikeService.insertDbGiveLike(dbGiveLike));
	}

	/**
	 * 修改保存点赞
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbGiveLike dbGiveLike)
	{		
		return toAjax(dbGiveLikeService.updateDbGiveLike(dbGiveLike));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbGiveLikeService.deleteDbGiveLikeByIds(ids));
	}
	
}
