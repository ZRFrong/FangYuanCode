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
@Api("manualReview")
@RequestMapping("manualReview")
public class DbManualReviewController extends BaseController
{
	
	@Autowired
	private IDbManualReviewService dbManualReviewService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbManualReview get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbManualReviewService.selectDbManualReviewById(id);
		
	}
	
	/**
	 * 查询审核表列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询审核表列表" , notes = "审核表列表")
	public R list(@ApiParam(name="DbManualReview",value="传入json格式",required=true) DbManualReview dbManualReview)
	{
		startPage();
        return result(dbManualReviewService.selectDbManualReviewList(dbManualReview));
	}
	
	
	/**
	 * 新增保存审核表
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存审核表" , notes = "新增保存审核表")
	public R addSave(@ApiParam(name="DbManualReview",value="传入json格式",required=true) @RequestBody DbManualReview dbManualReview)
	{		
		return toAjax(dbManualReviewService.insertDbManualReview(dbManualReview));
	}

	/**
	 * 修改保存审核表
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存审核表" , notes = "修改保存审核表")
	public R editSave(@ApiParam(name="DbManualReview",value="传入json格式",required=true) @RequestBody DbManualReview dbManualReview)
	{		
		return toAjax(dbManualReviewService.updateDbManualReview(dbManualReview));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除审核表" , notes = "删除审核表")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbManualReviewService.deleteDbManualReviewByIds(ids));
	}
	
}
