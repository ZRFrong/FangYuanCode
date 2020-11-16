package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbBanner;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.service.IDbBannerService;

import java.util.List;

/**
 * 轮播图 提供者
 * 
 * @author zheng
 * @date 2020-11-12
 */
@RestController
@Api("banner")
@RequestMapping("banner")
public class DbBannerController extends BaseController
{
	
	@Autowired
	private IDbBannerService dbBannerService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbBanner get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbBannerService.selectDbBannerById(id);
		
	}
	
	/**
	 * 查询轮播图列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询轮播图列表" , notes = "轮播图列表")
	public R list(@ApiParam(name="DbBanner",value="传入json格式",required=true) DbBanner dbBanner)
	{
		startPage();
        return result(dbBannerService.selectDbBannerList(dbBanner));
	}
	
	
	/**
	 * 新增保存轮播图
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存轮播图" , notes = "新增保存轮播图")
	public R addSave(@ApiParam(name="DbBanner",value="传入json格式",required=true) @RequestBody DbBanner dbBanner)
	{		
		return toAjax(dbBannerService.insertDbBanner(dbBanner));
	}

	/**
	 * 修改保存轮播图
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存轮播图" , notes = "修改保存轮播图")
	public R editSave(@ApiParam(name="DbBanner",value="传入json格式",required=true) @RequestBody DbBanner dbBanner)
	{		
		return toAjax(dbBannerService.updateDbBanner(dbBanner));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除轮播图" , notes = "删除轮播图")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbBannerService.deleteDbBannerByIds(ids));
	}


	@GetMapping("getBannerList/{bannerType}")
	@ApiOperation(value = "获取轮播图接口",notes = "获取轮播图",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "轮播图类型",value = "0首页 1智慧农业 2我的",required = true)
	})
	public R getBannerList(@PathVariable Integer bannerType){
		List<DbBanner> bannerList = dbBannerService.getBannerList(bannerType);
		return R.data(bannerList);
	}
}
