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
@Api("设备和土地中间")
@RequestMapping("landEquipment")
public class DbLandEquipmentController extends BaseController
{
	
	@Autowired
	private IDbLandEquipmentService dbLandEquipmentService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{dbLandId}")
	public DbLandEquipment get( @PathVariable("dbLandId") Long dbLandId)
	{
		return dbLandEquipmentService.selectDbLandEquipmentById(dbLandId);
		
	}
	
	/**
	 * 查询设备和土地中间列表
	 */
	@GetMapping("list")
	public R list( DbLandEquipment dbLandEquipment)
	{
		startPage();
        return result(dbLandEquipmentService.selectDbLandEquipmentList(dbLandEquipment));
	}
	
	
	/**
	 * 新增保存设备和土地中间
	 */
	@PostMapping("save")
	public R addSave( @RequestBody DbLandEquipment dbLandEquipment)
	{		
		return toAjax(dbLandEquipmentService.insertDbLandEquipment(dbLandEquipment));
	}

	/**
	 * 修改保存设备和土地中间
	 */
	@PostMapping("update")
	public R editSave( @RequestBody DbLandEquipment dbLandEquipment)
	{		
		return toAjax(dbLandEquipmentService.updateDbLandEquipment(dbLandEquipment));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{		
		return toAjax(dbLandEquipmentService.deleteDbLandEquipmentByIds(ids));
	}
	
}
