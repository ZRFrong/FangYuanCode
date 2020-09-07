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
import com.ruoyi.fangyuanapi.domain.DbProduct;
import com.ruoyi.fangyuanapi.service.IDbProductService;

/**
 * 产品 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("product")
@RequestMapping("product")
public class DbProductController extends BaseController
{

	@Autowired
	private IDbProductService dbProductService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{productId}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbProduct get(@ApiParam(name="id",value="long",required=true)  @PathVariable("productId") Long productId)
	{
		return dbProductService.selectDbProductById(productId);

	}

	/**
	 * 查询产品列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询产品列表" , notes = "产品列表")
	public R list(@ApiParam(name="DbProduct",value="传入json格式",required=true) DbProduct dbProduct)
	{
		startPage();
		return result(dbProductService.selectDbProductList(dbProduct));
	}


	/**
	 * 新增保存产品
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存产品" , notes = "新增保存产品")
	public R addSave(@ApiParam(name="DbProduct",value="传入json格式",required=true) @RequestBody DbProduct dbProduct)
	{
		return toAjax(dbProductService.insertDbProduct(dbProduct));
	}

	/**
	 * 修改保存产品
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存产品" , notes = "修改保存产品")
	public R editSave(@ApiParam(name="DbProduct",value="传入json格式",required=true) @RequestBody DbProduct dbProduct)
	{
		return toAjax(dbProductService.updateDbProduct(dbProduct));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除产品" , notes = "删除产品")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbProductService.deleteDbProductByIds(ids));
	}

}