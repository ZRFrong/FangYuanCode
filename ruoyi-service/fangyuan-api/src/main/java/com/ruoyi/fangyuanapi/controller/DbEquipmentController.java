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
import com.ruoyi.fangyuanapi.domain.DbEquipment;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;

/**
 * 设备 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("equipment")
@RequestMapping("equipment")
public class DbEquipmentController extends BaseController
{

	@Autowired
	private IDbEquipmentService dbEquipmentService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{equipmentId}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbEquipment get(@ApiParam(name="id",value="long",required=true)  @PathVariable("equipmentId") Long equipmentId)
	{
		return dbEquipmentService.selectDbEquipmentById(equipmentId);

	}

	/**
	 * 查询设备列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询设备列表" , notes = "设备列表")
	public R list(@ApiParam(name="DbEquipment",value="传入json格式",required=true) DbEquipment dbEquipment)
	{
		startPage();
		return result(dbEquipmentService.selectDbEquipmentList(dbEquipment));
	}


	/**
	 * 新增保存设备
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存设备" , notes = "新增保存设备")
	public R addSave(@ApiParam(name="DbEquipment",value="传入json格式",required=true) @RequestBody DbEquipment dbEquipment)
	{
		return toAjax(dbEquipmentService.insertDbEquipment(dbEquipment));
	}

	/**
	 * 修改保存设备
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存设备" , notes = "修改保存设备")
	public R editSave(@ApiParam(name="DbEquipment",value="传入json格式",required=true) @RequestBody DbEquipment dbEquipment)
	{
		return toAjax(dbEquipmentService.updateDbEquipment(dbEquipment));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除设备" , notes = "删除设备")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbEquipmentService.deleteDbEquipmentByIds(ids));
	}

}