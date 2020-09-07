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
import com.ruoyi.fangyuanapi.domain.DbLand;
import com.ruoyi.fangyuanapi.service.IDbLandService;

/**
 * 土地 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("land")
@RequestMapping("land")
public class DbLandController extends BaseController
{

	@Autowired
	private IDbLandService dbLandService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{landId}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbLand get(@ApiParam(name="id",value="long",required=true)  @PathVariable("landId") Long landId)
	{
		return dbLandService.selectDbLandById(landId);

	}

	/**
	 * 查询土地列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询土地列表" , notes = "土地列表")
	public R list(@ApiParam(name="DbLand",value="传入json格式",required=true) DbLand dbLand)
	{
		startPage();
		return result(dbLandService.selectDbLandList(dbLand));
	}


	/**
	 * 新增保存土地
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存土地" , notes = "新增保存土地")
	public R addSave(@ApiParam(name="DbLand",value="传入json格式",required=true) @RequestBody DbLand dbLand)
	{
		return toAjax(dbLandService.insertDbLand(dbLand));
	}

	/**
	 * 修改保存土地
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存土地" , notes = "修改保存土地")
	public R editSave(@ApiParam(name="DbLand",value="传入json格式",required=true) @RequestBody DbLand dbLand)
	{
		return toAjax(dbLandService.updateDbLand(dbLand));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除土地" , notes = "删除土地")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbLandService.deleteDbLandByIds(ids));
	}

}