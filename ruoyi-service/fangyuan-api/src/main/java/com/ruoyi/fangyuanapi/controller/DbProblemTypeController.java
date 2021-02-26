package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.page.PageConf;
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
import com.ruoyi.system.domain.DbProblemType;
import com.ruoyi.fangyuanapi.service.IDbProblemTypeService;
import java.util.List;
import java.util.Map;

/**
 * 问题类型 提供者
 * 
 * @author zheng
 * @date 2021-01-19
 */
@RestController
@Api("type")
@RequestMapping("type")
public class DbProblemTypeController extends BaseController
{
	
	@Autowired
	private IDbProblemTypeService dbProblemTypeService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbProblemType get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbProblemTypeService.selectDbProblemTypeById(id);
		
	}
	
	/**
	 * 查询问题类型列表
	 */
	@GetMapping("list/{type}/{currPage}")
    @ApiOperation(value = "查询问题类型列表" , notes = "问题类型列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type",value = "0：客服服务 1：功能反馈",required = true),
			@ApiImplicitParam(name = "currPage",value = "当前页码",required = true),
	})
	public R list(@PathVariable Integer type,@PathVariable  Integer currPage)
	{
		currPage = currPage <= 0 ? 0 :(currPage -1) * PageConf.pageSize;
		List<Map<String,Object>> dbProblemList = dbProblemTypeService.selectDbProblemList(type,currPage);
        return R.data(dbProblemList);
	}

	/**
	 *
	 */
	@GetMapping("getAllProblemType")
	public  R getAllProblemType(){
		List<Map<String,Object>> result = dbProblemTypeService.getAllProblemType();
		return R.data(result);
	}

	/**
	 * 新增保存问题类型
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存问题类型" , notes = "新增保存问题类型")
	public R addSave(@ApiParam(name="DbProblemType",value="传入json格式",required=true) @RequestBody DbProblemType dbProblemType)
	{		
		return toAjax(dbProblemTypeService.insertDbProblemType(dbProblemType));
	}

	/**
	 * 修改保存问题类型
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存问题类型" , notes = "修改保存问题类型")
	public R editSave(@ApiParam(name="DbProblemType",value="传入json格式",required=true) @RequestBody DbProblemType dbProblemType)
	{		
		return toAjax(dbProblemTypeService.updateDbProblemType(dbProblemType));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除问题类型" , notes = "删除问题类型")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbProblemTypeService.deleteDbProblemTypeByIds(ids));
	}
	
}
