package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbSpecParam;
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
import com.ruoyi.fangyuanapi.service.IDbSpecParamService;

/**
 * db_spec_param 提供者
 * 
 * @author zheng
 * @date 2020-09-30
 */
@RestController
@Api("param")
@RequestMapping("param")
public class DbSpecParamController extends BaseController
{
	
	@Autowired
	private IDbSpecParamService dbSpecParamService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbSpecParam get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbSpecParamService.selectDbSpecParamById(id);
		
	}
	
	/**
	 * 查询db_spec_param列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询db_spec_param列表" , notes = "db_spec_param列表")
	public R list(@ApiParam(name="DbSpecParam",value="传入json格式",required=true) DbSpecParam dbSpecParam)
	{
		startPage();
        return result(dbSpecParamService.selectDbSpecParamList(dbSpecParam));
	}
	
	
	/**
	 * 新增保存db_spec_param
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存db_spec_param" , notes = "新增保存db_spec_param")
	public R addSave(@ApiParam(name="DbSpecParam",value="传入json格式",required=true) @RequestBody DbSpecParam dbSpecParam)
	{		
		return toAjax(dbSpecParamService.insertDbSpecParam(dbSpecParam));
	}

	/**
	 * 修改保存db_spec_param
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存db_spec_param" , notes = "修改保存db_spec_param")
	public R editSave(@ApiParam(name="DbSpecParam",value="传入json格式",required=true) @RequestBody DbSpecParam dbSpecParam)
	{		
		return toAjax(dbSpecParamService.updateDbSpecParam(dbSpecParam));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除db_spec_param" , notes = "删除db_spec_param")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbSpecParamService.deleteDbSpecParamByIds(ids));
	}
	
}
