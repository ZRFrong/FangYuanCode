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
import com.ruoyi.fangyuantcp.domain.DbTcpOrder;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;

/**
 * 操作记录 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("order")
public class DbTcpOrderController extends BaseController
{
	
	@Autowired
	private IDbTcpOrderService dbTcpOrderService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{tcpOrderId}")
	public DbTcpOrder get(@PathVariable("tcpOrderId") Long tcpOrderId)
	{
		return dbTcpOrderService.selectDbTcpOrderById(tcpOrderId);
		
	}
	
	/**
	 * 查询操作记录列表
	 */
	@GetMapping("list")
	public R list(DbTcpOrder dbTcpOrder)
	{
		startPage();
        return result(dbTcpOrderService.selectDbTcpOrderList(dbTcpOrder));
	}
	
	
	/**
	 * 新增保存操作记录
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbTcpOrder dbTcpOrder)
	{		
		return toAjax(dbTcpOrderService.insertDbTcpOrder(dbTcpOrder));
	}

	/**
	 * 修改保存操作记录
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbTcpOrder dbTcpOrder)
	{		
		return toAjax(dbTcpOrderService.updateDbTcpOrder(dbTcpOrder));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbTcpOrderService.deleteDbTcpOrderByIds(ids));
	}
	
}
