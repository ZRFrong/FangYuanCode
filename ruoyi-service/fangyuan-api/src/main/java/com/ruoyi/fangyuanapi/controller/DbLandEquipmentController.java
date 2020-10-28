package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.system.domain.DbLandEquipment;
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
import com.ruoyi.fangyuanapi.service.IDbLandEquipmentService;

/**
 * 设备和土地中间 提供者
 * 
 * @author zheng
 * @date 2020-09-30
 */
@RestController
@Api("landEquipment")
@RequestMapping("landEquipment")
public class DbLandEquipmentController extends BaseController
{
	
	@Autowired
	private IDbLandEquipmentService dbLandEquipmentService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{dbLandId}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbLandEquipment get(@ApiParam(name="id",value="long",required=true)  @PathVariable("dbLandId") Long dbLandId)
	{
		return dbLandEquipmentService.selectDbLandEquipmentById(dbLandId);
		
	}
	
	/**
	 * 查询设备和土地中间列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询设备和土地中间列表" , notes = "设备和土地中间列表")
	public R list(@ApiParam(name="DbLandEquipment",value="传入json格式",required=true) DbLandEquipment dbLandEquipment)
	{
		startPage();
        return result(dbLandEquipmentService.selectDbLandEquipmentList(dbLandEquipment));
	}
	
	
	/**
	 * 新增保存设备和土地中间
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存设备和土地中间" , notes = "新增保存设备和土地中间")
	public R addSave(@ApiParam(name="DbLandEquipment",value="传入json格式",required=true) @RequestBody DbLandEquipment dbLandEquipment)
	{		
		return toAjax(dbLandEquipmentService.insertDbLandEquipment(dbLandEquipment));
	}

	/**
	 * 修改保存设备和土地中间
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存设备和土地中间" , notes = "修改保存设备和土地中间")
	public R editSave(@ApiParam(name="DbLandEquipment",value="传入json格式",required=true) @RequestBody DbLandEquipment dbLandEquipment)
	{		
		return toAjax(dbLandEquipmentService.updateDbLandEquipment(dbLandEquipment));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除设备和土地中间" , notes = "删除设备和土地中间")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbLandEquipmentService.deleteDbLandEquipmentByIds(ids));
	}
	
}
