package com.ruoyi.fangyuanapi.controller;

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
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbEquipmentTemp get(@ApiParam(name="id",value="long",required=true)  @PathVariable("equipmentTemId") Long equipmentTemId)
	{
		return dbEquipmentTempService.selectDbEquipmentTempById(equipmentTemId);
		
	}
	
	/**
	 * 查询设备模板列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询设备模板列表" , notes = "设备模板列表")
	public R list(@ApiParam(name="DbEquipmentTemp",value="传入json格式",required=true) DbEquipmentTemp dbEquipmentTemp)
	{
		startPage();
        return result(dbEquipmentTempService.selectDbEquipmentTempList(dbEquipmentTemp));
	}
	
	
	/**
	 * 新增保存设备模板
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存设备模板" , notes = "新增保存设备模板")
	public R addSave(@ApiParam(name="DbEquipmentTemp",value="传入json格式",required=true) @RequestBody DbEquipmentTemp dbEquipmentTemp)
	{		
		return toAjax(dbEquipmentTempService.insertDbEquipmentTemp(dbEquipmentTemp));
	}

	/**
	 * 修改保存设备模板
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存设备模板" , notes = "修改保存设备模板")
	public R editSave(@ApiParam(name="DbEquipmentTemp",value="传入json格式",required=true) @RequestBody DbEquipmentTemp dbEquipmentTemp)
	{		
		return toAjax(dbEquipmentTempService.updateDbEquipmentTemp(dbEquipmentTemp));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除设备模板" , notes = "删除设备模板")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbEquipmentTempService.deleteDbEquipmentTempByIds(ids));
	}
	
}
