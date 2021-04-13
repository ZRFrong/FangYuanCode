package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbFunctionDisplay;
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
import com.ruoyi.fangyuanapi.service.IDbFunctionDisplayService;

/**
 * 页面功能显示 提供者
 * 
 * @author zheng
 * @date 2021-04-08
 */
@RestController
@Api("display")
@RequestMapping("display")
public class DbFunctionDisplayController extends BaseController
{
	
	@Autowired
	private IDbFunctionDisplayService dbFunctionDisplayService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbFunctionDisplay get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbFunctionDisplayService.selectDbFunctionDisplayById(id);
		
	}
	
	/**
	 * 查询页面功能显示列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询页面功能显示列表" , notes = "页面功能显示列表")
	public R list(@ApiParam(name="DbFunctionDisplay",value="传入json格式",required=true) DbFunctionDisplay dbFunctionDisplay)
	{
		startPage();
        return result(dbFunctionDisplayService.selectDbFunctionDisplayList(dbFunctionDisplay));
	}
	
	
	/**
	 * 新增保存页面功能显示
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存页面功能显示" , notes = "新增保存页面功能显示")
	public R addSave(@ApiParam(name="DbFunctionDisplay",value="传入json格式",required=true) @RequestBody DbFunctionDisplay dbFunctionDisplay)
	{		
		return toAjax(dbFunctionDisplayService.insertDbFunctionDisplay(dbFunctionDisplay));
	}

	/**
	 * 修改保存页面功能显示
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存页面功能显示" , notes = "修改保存页面功能显示")
	public R editSave(@ApiParam(name="DbFunctionDisplay",value="传入json格式",required=true) @RequestBody DbFunctionDisplay dbFunctionDisplay)
	{		
		return toAjax(dbFunctionDisplayService.updateDbFunctionDisplay(dbFunctionDisplay));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除页面功能显示" , notes = "删除页面功能显示")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbFunctionDisplayService.deleteDbFunctionDisplayByIds(ids));
	}
	
}
