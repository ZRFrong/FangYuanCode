package com.ruoyi.fangyuanapi.controller;

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
import com.ruoyi.fangyuanapi.domain.DbComment;
import com.ruoyi.fangyuanapi.service.IDbCommentService;

/**
 * 评论 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("comment")
@RequestMapping("comment")
public class DbCommentController extends BaseController
{

	@Autowired
	private IDbCommentService dbCommentService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbComment get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbCommentService.selectDbCommentById(id);

	}

	/**
	 * 查询评论列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询评论列表" , notes = "评论列表")
	public R list(@ApiParam(name="DbComment",value="传入json格式",required=true) DbComment dbComment)
	{
		startPage();
		return result(dbCommentService.selectDbCommentList(dbComment));
	}


	/**
	 * 新增保存评论
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存评论" , notes = "新增保存评论")
	public R addSave(@ApiParam(name="DbComment",value="传入json格式",required=true) @RequestBody DbComment dbComment)
	{
		return toAjax(dbCommentService.insertDbComment(dbComment));
	}

	/**
	 * 修改保存评论
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存评论" , notes = "修改保存评论")
	public R editSave(@ApiParam(name="DbComment",value="传入json格式",required=true) @RequestBody DbComment dbComment)
	{
		return toAjax(dbCommentService.updateDbComment(dbComment));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除评论" , notes = "删除评论")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbCommentService.deleteDbCommentByIds(ids));
	}

}