package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbProductBatch;
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
import com.ruoyi.fangyuanapi.service.IDbProductBatchService;

/**
 * 产品批次 提供者
 * 
 * @author zheng
 * @date 2021-04-08
 */
@RestController
@Api("ProductBatch")
@RequestMapping("ProductBatch")
public class DbProductBatchController extends BaseController
{
	
	@Autowired
	private IDbProductBatchService dbProductBatchService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbProductBatch get(@ApiParam(name="id",value="long",required=true)  @PathVariable("id") Long id)
	{
		return dbProductBatchService.selectDbProductBatchById(id);
		
	}
	
	/**
	 * 查询产品批次列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询产品批次列表" , notes = "产品批次列表")
	public R list(@ApiParam(name="DbProductBatch",value="传入json格式",required=true) DbProductBatch dbProductBatch)
	{
		startPage();
        return result(dbProductBatchService.selectDbProductBatchList(dbProductBatch));
	}
	
	
	/**
	 * 新增保存产品批次
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存产品批次" , notes = "新增保存产品批次")
	public R addSave(@ApiParam(name="DbProductBatch",value="传入json格式",required=true) @RequestBody DbProductBatch dbProductBatch)
	{		
		return toAjax(dbProductBatchService.insertDbProductBatch(dbProductBatch));
	}

	/**
	 * 修改保存产品批次
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存产品批次" , notes = "修改保存产品批次")
	public R editSave(@ApiParam(name="DbProductBatch",value="传入json格式",required=true) @RequestBody DbProductBatch dbProductBatch)
	{		
		return toAjax(dbProductBatchService.updateDbProductBatch(dbProductBatch));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除产品批次" , notes = "删除产品批次")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbProductBatchService.deleteDbProductBatchByIds(ids));
	}
	
}
