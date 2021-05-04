package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbIntelligenceControlRelationship;
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
import com.ruoyi.fangyuanapi.service.IDbIntelligenceControlRelationshipService;

/**
 * 产品，功能，产品批次，id ，用户id  ，场所关系中间 提供者
 * 
 * @author zheng
 * @date 2021-04-08
 */
@RestController
@Api("relationship")
@RequestMapping("relationship")
public class DbIntelligenceControlRelationshipController extends BaseController
{
	
	@Autowired
	private IDbIntelligenceControlRelationshipService dbIntelligenceControlRelationshipService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbIntelligenceControlRelationship get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbIntelligenceControlRelationshipService.selectDbIntelligenceControlRelationshipById(id);
		
	}
	
	/**
	 * 查询产品，功能，产品批次，id ，用户id  ，场所关系中间列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询产品，功能，产品批次，id ，用户id  ，场所关系中间列表" , notes = "产品，功能，产品批次，id ，用户id  ，场所关系中间列表")
	public R list(@ApiParam(name="DbIntelligenceControlRelationship",value="传入json格式",required=true) DbIntelligenceControlRelationship dbIntelligenceControlRelationship)
	{
		startPage();
        return result(dbIntelligenceControlRelationshipService.selectDbIntelligenceControlRelationshipList(dbIntelligenceControlRelationship));
	}
	
	
	/**
	 * 新增保存产品，功能，产品批次，id ，用户id  ，场所关系中间
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存产品，功能，产品批次，id ，用户id  ，场所关系中间" , notes = "新增保存产品，功能，产品批次，id ，用户id  ，场所关系中间")
	public R addSave(@ApiParam(name="DbIntelligenceControlRelationship",value="传入json格式",required=true) @RequestBody DbIntelligenceControlRelationship dbIntelligenceControlRelationship)
	{		
		return toAjax(dbIntelligenceControlRelationshipService.insertDbIntelligenceControlRelationship(dbIntelligenceControlRelationship));
	}

	/**
	 * 修改保存产品，功能，产品批次，id ，用户id  ，场所关系中间
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存产品，功能，产品批次，id ，用户id  ，场所关系中间" , notes = "修改保存产品，功能，产品批次，id ，用户id  ，场所关系中间")
	public R editSave(@ApiParam(name="DbIntelligenceControlRelationship",value="传入json格式",required=true) @RequestBody DbIntelligenceControlRelationship dbIntelligenceControlRelationship)
	{		
		return toAjax(dbIntelligenceControlRelationshipService.updateDbIntelligenceControlRelationship(dbIntelligenceControlRelationship));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除产品，功能，产品批次，id ，用户id  ，场所关系中间" , notes = "删除产品，功能，产品批次，id ，用户id  ，场所关系中间")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbIntelligenceControlRelationshipService.deleteDbIntelligenceControlRelationshipByIds(ids));
	}
	
}
