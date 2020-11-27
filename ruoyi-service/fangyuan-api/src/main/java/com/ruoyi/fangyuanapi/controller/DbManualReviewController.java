package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbManualReview;
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
import com.ruoyi.fangyuanapi.service.IDbManualReviewService;

/**
 * 审核表 提供者
 * 
 * @author ruoyi
 * @date 2020-09-22
 */
@RestController
@Api("审核表")
@RequestMapping("manualReview")
public class DbManualReviewController extends BaseController
{
	
	@Autowired
	private IDbManualReviewService dbManualReviewService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbManualReview get(  @PathVariable("id") Long id)
	{
		return dbManualReviewService.selectDbManualReviewById(id);
		
	}
	
	/**
	 * 查询审核表列表
	 */
	@GetMapping("list")
	public R list( DbManualReview dbManualReview)
	{
		startPage();
        return result(dbManualReviewService.selectDbManualReviewList(dbManualReview));
	}
	
	
	/**
	 * 新增保存审核表
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbManualReview dbManualReview)
	{		
		return toAjax(dbManualReviewService.insertDbManualReview(dbManualReview));
	}

	/**
	 * 修改保存审核表
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbManualReview dbManualReview)
	{		
		return toAjax(dbManualReviewService.updateDbManualReview(dbManualReview));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{		
		return toAjax(dbManualReviewService.deleteDbManualReviewByIds(ids));
	}
	
}
