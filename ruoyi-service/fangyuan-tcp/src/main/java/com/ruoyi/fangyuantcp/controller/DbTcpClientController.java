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
import com.ruoyi.fangyuantcp.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;

/**
 * tcp在线设备 提供者
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@RestController
@RequestMapping("client")
public class DbTcpClientController extends BaseController
{
	
	@Autowired
	private IDbTcpClientService dbTcpClientService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{tcpClientId}")
	public DbTcpClient get(@PathVariable("tcpClientId") Long tcpClientId)
	{
		return dbTcpClientService.selectDbTcpClientById(tcpClientId);
		
	}
	
	/**
	 * 查询tcp在线设备列表
	 */
	@GetMapping("list")
	public R list(DbTcpClient dbTcpClient)
	{
		startPage();
        return result(dbTcpClientService.selectDbTcpClientList(dbTcpClient));
	}
	
	
	/**
	 * 新增保存tcp在线设备
	 */
	@PostMapping("save")
	public R addSave(@RequestBody DbTcpClient dbTcpClient)
	{		
		return toAjax(dbTcpClientService.insertDbTcpClient(dbTcpClient));
	}

	/**
	 * 修改保存tcp在线设备
	 */
	@PostMapping("update")
	public R editSave(@RequestBody DbTcpClient dbTcpClient)
	{		
		return toAjax(dbTcpClientService.updateDbTcpClient(dbTcpClient));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove(String ids)
	{		
		return toAjax(dbTcpClientService.deleteDbTcpClientByIds(ids));
	}
	
}
