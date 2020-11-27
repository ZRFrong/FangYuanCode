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
import com.ruoyi.system.domain.DbProduct;
import com.ruoyi.fangyuanapi.service.IDbProductService;

/**
 * 产品 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("产品")
@RequestMapping("product")
public class DbProductController extends BaseController
{

	@Autowired
	private IDbProductService dbProductService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{productId}")
	public DbProduct get(  @PathVariable("productId") Long productId)
	{
		return dbProductService.selectDbProductById(productId);

	}

	/**
	 * 查询产品列表
	 */
	@GetMapping("list")
	public R list( DbProduct dbProduct)
	{
		startPage();
		return result(dbProductService.selectDbProductList(dbProduct));
	}


	/**
	 * 新增保存产品
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbProduct dbProduct)
	{
		return toAjax(dbProductService.insertDbProduct(dbProduct));
	}

	/**
	 * 修改保存产品
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbProduct dbProduct)
	{
		return toAjax(dbProductService.updateDbProduct(dbProduct));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{
		return toAjax(dbProductService.deleteDbProductByIds(ids));
	}

}