package com.ruoyi.fangyuanapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuanapi.domain.DbEquipment;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;

/**
 * 设备 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("equipment")
public class DbEquipmentController extends BaseController
{
	
	@Autowired
	private IDbEquipmentService dbEquipmentService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{equipmentId}")
	public DbEquipment get(@PathVariable("equipmentId") Long equipmentId)
	{
		return dbEquipmentService.selectDbEquipmentById(equipmentId);
		
	}
	
	/**
	 * 查询设备列表
	 */
	@GetMapping("list")
	public R list(DbEquipment dbEquipment)
	{
		startPage();
        return result(dbEquipmentService.selectDbEquipmentList(dbEquipment));
	}
	
	
	/**
	 * 新增保存设备
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbEquipment dbEquipment)
	{		
		return toAjax(dbEquipmentService.insertDbEquipment(dbEquipment));
	}

	/**
	 * 修改保存设备
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbEquipment dbEquipment)
	{		
		return toAjax(dbEquipmentService.updateDbEquipment(dbEquipment));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbEquipmentService.deleteDbEquipmentByIds(ids));
	}
	
}
