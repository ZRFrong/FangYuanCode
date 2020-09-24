package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysJob;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * 定时任务调度Service接口
 * 
 * @author zheng
 * @date 2020-09-22
 */
public interface ISysJobService 
{
    /**
     * 查询定时任务调度
     * 
     * @param jobId 定时任务调度ID
     * @return 定时任务调度
     */
    public SysJob selectSysJobById(Long jobId);

    /**
     * 查询定时任务调度列表
     * 
     * @param sysJob 定时任务调度
     * @return 定时任务调度集合
     */
    public List<SysJob> selectSysJobList(SysJob sysJob);

    /**
     * 新增定时任务调度
     * 
     * @param sysJob 定时任务调度
     * @return 结果
     */
    public int insertSysJob(SysJob sysJob);

    /**
     * 修改定时任务调度
     * 
     * @param sysJob 定时任务调度
     * @return 结果
     */
    public int updateSysJob(SysJob sysJob);

    /**
     * 批量删除定时任务调度
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJobByIds(String ids);

    /**
     * 删除定时任务调度信息
     * 
     * @param jobId 定时任务调度ID
     * @return 结果
     */
    public int deleteSysJobById(Long jobId);

    void run(SysJob job) throws SchedulerException;

    int changeStatus(SysJob newJob) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int pauseJob(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    public int resumeJob(SysJob job) throws SchedulerException;

    boolean checkCronExpressionIsValid(String cronExpression);
}
