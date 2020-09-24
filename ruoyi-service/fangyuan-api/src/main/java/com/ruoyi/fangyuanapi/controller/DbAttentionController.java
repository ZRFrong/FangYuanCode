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
import com.ruoyi.system.domain.DbAttention;
import com.ruoyi.fangyuanapi.service.IDbAttentionService;

/**
 * 关注和被关注 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("attention")
@RequestMapping("attention")
public class DbAttentionController extends BaseController
{

	@Autowired
	private IDbAttentionService dbAttentionService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbAttention get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbAttentionService.selectDbAttentionById(id);

	}

	/**
	 * 查询关注和被关注列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询关注和被关注列表" , notes = "关注和被关注列表")
	public R list(@ApiParam(name="DbAttention",value="传入json格式",required=true) DbAttention dbAttention)
	{
		startPage();
		return result(dbAttentionService.selectDbAttentionList(dbAttention));
	}


	/**
	 * 新增保存关注和被关注
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存关注和被关注" , notes = "新增保存关注和被关注")
	public R addSave(@ApiParam(name="DbAttention",value="传入json格式",required=true) @RequestBody DbAttention dbAttention)
	{
		return toAjax(dbAttentionService.insertDbAttention(dbAttention));
	}

	/**
	 * 修改保存关注和被关注
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存关注和被关注" , notes = "修改保存关注和被关注")
	public R editSave(@ApiParam(name="DbAttention",value="传入json格式",required=true) @RequestBody DbAttention dbAttention)
	{
		return toAjax(dbAttentionService.updateDbAttention(dbAttention));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除关注和被关注" , notes = "删除关注和被关注")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbAttentionService.deleteDbAttentionByIds(ids));
	}

}