package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.SysJobLog;
import com.ruoyi.system.service.ISysJobLogService;

/**
 * 定时任务调度日志 提供者
 * 
 * @author zheng
 * @date 2020-09-22
 */
@RestController
@Api("定时任务调度日志")
@RequestMapping("jobLog")
public class SysJobLogController extends BaseController
{
	
	@Autowired
	private ISysJobLogService sysJobLogService;
	
	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{jobLogId}")
	public SysJobLog get(@ApiParam(name="id",value="long",required=true)  @PathVariable("jobLogId") Long jobLogId)
	{
		return sysJobLogService.selectSysJobLogById(jobLogId);
		
	}
	
	/**
	 * 查询定时任务调度日志列表
	 */
	@GetMapping("list")
	public R list( SysJobLog sysJobLog)
	{
		startPage();
        return result(sysJobLogService.selectSysJobLogList(sysJobLog));
	}
	
	
	/**
	 * 新增保存定时任务调度日志
	 */
	@PostMapping("save")
	public R addSave( @RequestBody SysJobLog sysJobLog)
	{		
		return toAjax(sysJobLogService.insertSysJobLog(sysJobLog));
	}

	/**
	 * 修改保存定时任务调度日志
	 */
	@PostMapping("update")
	public R editSave( @RequestBody SysJobLog sysJobLog)
	{		
		return toAjax(sysJobLogService.updateSysJobLog(sysJobLog));
	}
	
	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
	public R remove( String ids)
	{		
		return toAjax(sysJobLogService.deleteSysJobLogByIds(ids));
	}
	
}
