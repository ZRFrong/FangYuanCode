package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.conf.OperationConf;
import com.ruoyi.system.domain.DbEquipmentTemp;
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
import com.ruoyi.fangyuanapi.service.IDbEquipmentTempService;

import java.util.List;

/**
 * 设备模板 提供者
 * 
 * @author zheng
 * @date 2020-09-25
 */
@RestController
@Api("temp")
@RequestMapping("temp")
public class DbEquipmentTempController extends BaseController
{
	
	@Autowired
	private IDbEquipmentTempService dbEquipmentTempService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{equipmentTemId}")
	public DbEquipmentTemp get(  @PathVariable("equipmentTemId") Long equipmentTemId)
	{
		return dbEquipmentTempService.selectDbEquipmentTempById(equipmentTemId);
		
	}
	
	/**
	 * 查询设备模板列表
	 */
	@GetMapping("list")
	public R list( DbEquipmentTemp dbEquipmentTemp)
	{
		startPage();
        return result(dbEquipmentTempService.selectDbEquipmentTempList(dbEquipmentTemp));
	}


	 private  OperationConf operationConf=SpringUtils.getBean(OperationConf.class);
	/**
	 * 查询设备模板种类列表
	 */
	@GetMapping("speciesList")
    @ApiOperation(value = "查询设备模板种类列表", notes = "查询设备模板种类列表")
	public R speciesList(  )
	{
        return R.data(operationConf.getTypsMap());
	}
	/**
	 * 查询设备模板列表
	 */
	@GetMapping("listOnly")
	public R listOnly(DbEquipmentTemp dbEquipmentTemp)
	{
		return R.data(dbEquipmentTempService.selectDbEquipmentTempList(dbEquipmentTemp));
	}
	
	/**
	 * 新增保存设备模板
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbEquipmentTemp dbEquipmentTemp)
	{		
		return toAjax(dbEquipmentTempService.insertDbEquipmentTemp(dbEquipmentTemp));
	}

	/**
	 * 修改保存设备模板
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbEquipmentTemp dbEquipmentTemp)
	{		
		return toAjax(dbEquipmentTempService.updateDbEquipmentTemp(dbEquipmentTemp));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{		
		return toAjax(dbEquipmentTempService.deleteDbEquipmentTempByIds(ids));
	}
	
}
