package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.fangyuanapi.utils.MonitorCloudRequestUtils;
import com.ruoyi.system.domain.monitor.DbMonitor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 视频监控设备 提供者
 * 
 * @author zheng
 * @date 2021-05-25
 */
@RestController
@Api("monitor")
@RequestMapping("monitor")
public class DbMonitorController extends BaseController
{
	
	@Autowired
	private IDbMonitorService dbMonitorService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{deviceRegisterCode}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public DbMonitor get(@ApiParam(name="id",value="long",required=true)  @PathVariable("deviceRegisterCode") String deviceRegisterCode)
	{
		return dbMonitorService.selectDbMonitorById(deviceRegisterCode);
	}

	/**
	 * 查询视频监控设备类型列表
	 */
	@GetMapping("listMonitorType")
	@ApiOperation(value = "查询视频监控设备类型列表" , notes = "视频监控设备类型列表")
	public R listMonitorType()
	{
		MonitorCloudRequestUtils.getDevice("11");
		return result(dbMonitorService.selectDbMonitorTypeList());
	}
	
	/**
	 * 查询视频监控设备列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询视频监控设备列表" , notes = "视频监控设备列表")
	public R list(@ApiParam(name="DbMonitor",value="传入json格式",required=true) DbMonitor dbMonitor)
	{
		startPage();
        return result(dbMonitorService.selectDbMonitorList(dbMonitor));
	}
	
	
	/**
	 * 新增保存视频监控设备
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存视频监控设备" , notes = "新增保存视频监控设备")
	public R addSave(@ApiParam(name="DbMonitor",value="传入json格式",required=true) @RequestBody DbMonitor dbMonitor)
	{		
		return toAjax(dbMonitorService.insertDbMonitor(dbMonitor));
	}

	/**
	 * 修改保存视频监控设备
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存视频监控设备" , notes = "修改保存视频监控设备")
	public R editSave(@ApiParam(name="DbMonitor",value="传入json格式",required=true) @RequestBody DbMonitor dbMonitor)
	{		
		return toAjax(dbMonitorService.updateDbMonitor(dbMonitor));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除视频监控设备" , notes = "删除视频监控设备")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{		
		return toAjax(dbMonitorService.deleteDbMonitorByIds(ids));
	}
	
}
