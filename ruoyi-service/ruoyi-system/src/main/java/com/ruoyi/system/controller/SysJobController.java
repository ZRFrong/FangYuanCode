package com.ruoyi.system.controller;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.domain.SysJob;
import com.ruoyi.system.service.ISysJobService;

/**
 * 定时任务调度 提供者
 *
 * @author zheng
 * @date 2020-09-22
 */
@RestController
@Api("sysJob")
@RequestMapping("sysJob")
public class SysJobController extends BaseController
{

	@Autowired
	private ISysJobService sysJobService;

	/**
	 * 查询${tableComment}
	 */
	@GetMapping("get/{jobId}")
    @ApiOperation(value = "根据id查询" , notes = "查询${tableComment}")
	public SysJob get(@ApiParam(name="id",value="long",required=true)  @PathVariable("jobId") Long jobId)
	{

		return sysJobService.selectSysJobById(jobId);

	}

	/**
	 * 查询定时任务调度列表
	 */
	@GetMapping("list")
    @ApiOperation(value = "查询定时任务调度列表" , notes = "定时任务调度列表")
	public R list(@ApiParam(name="SysJob",value="传入json格式",required=true) SysJob sysJob)
	{
		startPage();
        return result(sysJobService.selectSysJobList(sysJob));
	}


	/**
	 * 新增保存定时任务调度
	 */
	@PostMapping("save")
    @ApiOperation(value = "新增保存定时任务调度" , notes = "新增保存定时任务调度")
	public R addSave(@ApiParam(name="SysJob",value="传入json格式",required=true) @RequestBody SysJob sysJob)
	{
		return toAjax(sysJobService.insertSysJob(sysJob));
	}

	/**
	 * 修改保存定时任务调度
	 */
	@PostMapping("update")
    @ApiOperation(value = "修改保存定时任务调度" , notes = "修改保存定时任务调度")
	public R editSave(@ApiParam(name="SysJob",value="传入json格式",required=true) @RequestBody SysJob sysJob)
	{
		return toAjax(sysJobService.updateSysJob(sysJob));
	}

	/**
	 * 删除${tableComment}
	 */
	@PostMapping("remove")
    @ApiOperation(value = "删除定时任务调度" , notes = "删除定时任务调度")
	public R remove(@ApiParam(name="删除的id子串",value="已逗号分隔的id集",required=true) String ids)
	{
		return toAjax(sysJobService.deleteSysJobByIds(ids));
	}

	/**
	 * 任务调度立即执行一次
	 */
	@PostMapping("run")
	@ResponseBody
	@ApiOperation(value = "任务调度立即执行一次" , notes = "任务调度立即执行一次")
	public R run(@ApiParam(name="SysJob",value="传入json格式",required=true) SysJob job) throws SchedulerException
	{
		sysJobService.run(job);
		return R.ok();
	}

	/**
	 * 任务调度状态修改
	 */
	@PostMapping("/changeStatus")
	@ResponseBody
	public  R changeStatus(SysJob job) throws SchedulerException
	{
		SysJob newJob = sysJobService.selectSysJobById(job.getJobId());
		newJob.setStatus(job.getStatus());
		return toAjax(sysJobService.changeStatus(newJob));
	}

	/**
	 * 校验cron表达式是否有效
	 */
	@PostMapping("/checkCronExpressionIsValid")
	@ResponseBody
	@ApiOperation(value = "校验cron表达式是否有效" , notes = "校验cron表达式是否有效")
	public boolean checkCronExpressionIsValid(@ApiParam(name="SysJob",value="传入json格式",required=true)SysJob job)
	{
		return sysJobService.checkCronExpressionIsValid(job.getCronExpression());
	}




}
