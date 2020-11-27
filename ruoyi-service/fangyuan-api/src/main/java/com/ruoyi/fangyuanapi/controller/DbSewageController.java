package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbSewage;
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
import com.ruoyi.fangyuanapi.service.IDbSewageService;

/**
 * 水肥 提供者
 * 
 * @author zheng
 * @date 2020-11-23
 */
@RestController
@Api("水肥")
@RequestMapping("sewage")
public class DbSewageController extends BaseController
{
	
	@Autowired
	private IDbSewageService dbSewageService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{id}")
	public DbSewage get(  @PathVariable("id") Long id)
	{
		return dbSewageService.selectDbSewageById(id);
		
	}
	
	/**
	 * 查询水肥列表
	 */
	@GetMapping("list")
	public R list( DbSewage dbSewage)
	{
		startPage();
        return result(dbSewageService.selectDbSewageList(dbSewage));
	}
	
	
	/**
	 * 新增保存水肥
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbSewage dbSewage)
	{		
		return toAjax(dbSewageService.insertDbSewage(dbSewage));
	}

	/**
	 * 修改保存水肥
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbSewage dbSewage)
	{		
		return toAjax(dbSewageService.updateDbSewage(dbSewage));
	}



	/*
	 * 更改稀释度和  施肥量
	 * */
	@GetMapping("updateFertilization")
	@ApiOperation(value = "设置施肥量", notes = "施肥量")
	public R updateFertilization(@ApiParam("施肥量") String fertilization) {


		return toAjax(0);
	}


	/*
	 * 更改稀释度和  施肥量
	 *
	 * */
	@GetMapping("updateDilutability")
	@ApiOperation(value = "设置稀释度", notes = "稀释度")
	public R updateDilutability( @ApiParam("稀释度") String dilutabilit ){


		return toAjax(0);
	}

	/*
	 * 开启，关闭水肥  依次发送操作指令
	 * */
	@GetMapping("turnOperateSewage")
	@ApiOperation(value = "设置稀释度", notes = "稀释度")
	public R turnOperateSewage( @ApiParam("稀释度") String dilutability) {


		return toAjax(0);

	}


	
}
