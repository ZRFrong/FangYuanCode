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
import com.ruoyi.fangyuanapi.domain.DbComment;
import com.ruoyi.fangyuanapi.service.IDbCommentService;

/**
 * 评论 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("comment")
public class DbCommentController extends BaseController
{
	
	@Autowired
	private IDbCommentService dbCommentService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbComment get(@PathVariable("id") Long id)
	{
		return dbCommentService.selectDbCommentById(id);
		
	}
	
	/**
	 * 查询评论列表
	 */
	@GetMapping("list")
	public R list(DbComment dbComment)
	{
		startPage();
        return result(dbCommentService.selectDbCommentList(dbComment));
	}
	
	
	/**
	 * 新增保存评论
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbComment dbComment)
	{		
		return toAjax(dbCommentService.insertDbComment(dbComment));
	}

	/**
	 * 修改保存评论
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbComment dbComment)
	{		
		return toAjax(dbCommentService.updateDbComment(dbComment));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbCommentService.deleteDbCommentByIds(ids));
	}
	
}
