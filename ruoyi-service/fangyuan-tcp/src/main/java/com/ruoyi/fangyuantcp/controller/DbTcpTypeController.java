package com.ruoyi.fangyuantcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.fangyuantcp.domain.DbTcpType;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;

/**
 * 设备状态 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("type")
public class DbTcpTypeController extends BaseController
{
	
	@Autowired
	private IDbTcpTypeService dbTcpTypeService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{tcpTypeId}")
	public DbTcpType get(@PathVariable("tcpTypeId") Long tcpTypeId)
	{
		return dbTcpTypeService.selectDbTcpTypeById(tcpTypeId);
		
	}
	
	/**
	 * 查询设备状态列表
	 */
	@GetMapping("list")
	public R list(DbTcpType dbTcpType)
	{
		startPage();
        return result(dbTcpTypeService.selectDbTcpTypeList(dbTcpType));
	}
	
	
	/**
	 * 新增保存设备状态
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbTcpType dbTcpType)
	{		
		return toAjax(dbTcpTypeService.insertDbTcpType(dbTcpType));
	}

	/**
	 * 修改保存设备状态
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbTcpType dbTcpType)
	{		
		return toAjax(dbTcpTypeService.updateDbTcpType(dbTcpType));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbTcpTypeService.deleteDbTcpTypeByIds(ids));
	}
	
}
