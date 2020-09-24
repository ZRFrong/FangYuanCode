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
import com.ruoyi.system.domain.DbGiveLike;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;

/**
 * 点赞 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("giveLike")
@RequestMapping("giveLike")
public class DbGiveLikeController extends BaseController
{

	@Autowired
	private IDbGiveLikeService dbGiveLikeService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbGiveLike get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbGiveLikeService.selectDbGiveLikeById(id);

	}

	/**
	 * 查询点赞列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询点赞列表" , notes = "点赞列表")
	public R list(@ApiParam(name="DbGiveLike",value="传入json格式",required=true) DbGiveLike dbGiveLike)
	{
		startPage();
		return result(dbGiveLikeService.selectDbGiveLikeList(dbGiveLike));
	}


	/**
	 * 新增保存点赞
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存点赞" , notes = "新增保存点赞")
	public R addSave(@ApiParam(name="DbGiveLike",value="传入json格式",required=true) @RequestBody DbGiveLike dbGiveLike)
	{
		return toAjax(dbGiveLikeService.insertDbGiveLike(dbGiveLike));
	}

	/**
	 * 修改保存点赞
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存点赞" , notes = "修改保存点赞")
	public R editSave(@ApiParam(name="DbGiveLike",value="传入json格式",required=true) @RequestBody DbGiveLike dbGiveLike)
	{
		return toAjax(dbGiveLikeService.updateDbGiveLike(dbGiveLike));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除点赞" , notes = "删除点赞")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbGiveLikeService.deleteDbGiveLikeByIds(ids));
	}

}