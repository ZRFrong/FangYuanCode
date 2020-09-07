package com.ruoyi.fangyuantcp.controller;

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
import com.ruoyi.fangyuantcp.domain.DbTcpOrder;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;

/**
 * 操作记录 提供者
 *
 * @author fangyuan
 * @date 2020-09-07
 */
@RestController
@Api("order")
@RequestMapping("order")
public class DbTcpOrderController extends BaseController
{

	@Autowired
	private IDbTcpOrderService dbTcpOrderService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{tcpOrderId}")
	@ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbTcpOrder get(@ApiParam(name="id",value="long",required=true)  @PathVariable("tcpOrderId") Long tcpOrderId)
	{
		return dbTcpOrderService.selectDbTcpOrderById(tcpOrderId);

	}

	/**
	 * 查询操作记录列表
	 */
	@GetMapping("list")
	@ApiOperation(value = "查询操作记录列表" , notes = "操作记录列表")
	public R list(@ApiParam(name="DbTcpOrder",value="传入json格式",required=true) DbTcpOrder dbTcpOrder)
	{
		startPage();
		return result(dbTcpOrderService.selectDbTcpOrderList(dbTcpOrder));
	}


	/**
	 * 新增保存操作记录
	 */
	@PostMapping("save")
	@ApiOperation(value = "新增保存操作记录" , notes = "新增保存操作记录")
	public R addSave(@ApiParam(name="DbTcpOrder",value="传入json格式",required=true) @RequestBody DbTcpOrder dbTcpOrder)
	{
		return toAjax(dbTcpOrderService.insertDbTcpOrder(dbTcpOrder));
	}

	/**
	 * 修改保存操作记录
	 */
	@PostMapping("update")
	@ApiOperation(value = "修改保存操作记录" , notes = "修改保存操作记录")
	public R editSave(@ApiParam(name="DbTcpOrder",value="传入json格式",required=true) @RequestBody DbTcpOrder dbTcpOrder)
	{
		return toAjax(dbTcpOrderService.updateDbTcpOrder(dbTcpOrder));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	@ApiOperation(value = "删除操作记录" , notes = "删除操作记录")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(dbTcpOrderService.deleteDbTcpOrderByIds(ids));
	}

}