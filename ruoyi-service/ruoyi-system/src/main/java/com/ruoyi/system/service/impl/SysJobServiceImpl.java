package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.constant.ScheduleConstants;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.quartz.CronUtils;
import com.ruoyi.system.quartz.ScheduleUtils;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysJobMapper;
import com.ruoyi.system.domain.SysJob;
import com.ruoyi.system.service.ISysJobService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 定时任务调度Service业务层处理
 *
 * @author zheng
 * @date 2020-09-22
 */
@Service
public class SysJobServiceImpl implements ISysJobService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private SysJobMapper sysJobMapper;


    @Autowired
    private SysJobMapper jobMapper;

    /**
     * 项目启动时，初始化定时器
     * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void init() throws SchedulerException, TaskException {
        SysJob sysJob = new SysJob();
        List<SysJob> jobList = jobMapper.selectSysJobList(sysJob);
        for (SysJob job : jobList) {
            updateSchedulerJob(job, job.getJobGroup());
        }
    }

    /**
     * 更新任务
     *
     * @param job      任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }

    /**
     * 校验cron表达式是否有效
     *
     * @param cronExpression 表达式
     * @return 结果
     */
    @Override
    public boolean checkCronExpressionIsValid(String cronExpression) {
        return CronUtils.isValid(cronExpression);
    }

    /**
     * 查询定时任务调度
     *
     * @param jobId 定时任务调度ID
     * @return 定时任务调度
     */
    @Override
    public SysJob selectSysJobById(Long jobId) {
        return sysJobMapper.selectSysJobById(jobId);
    }

    /**
     * 查询定时任务调度列表
     *
     * @param sysJob 定时任务调度
     * @return 定时任务调度
     */
    @Override
    public List<SysJob> selectSysJobList(SysJob sysJob) {
        return sysJobMapper.selectSysJobList(sysJob);
    }

    /**
     * 新增定时任务调度
     *
     * @param sysJob 定时任务调度
     * @return 结果
     */
    @Override
    public int insertSysJob(SysJob sysJob) {
        sysJob.setCreateTime(DateUtils.getNowDate());
        return sysJobMapper.insertSysJob(sysJob);
    }

    /**
     * 修改定时任务调度
     *
     * @param sysJob 定时任务调度
     * @return 结果
     */
    @Override
    public int updateSysJob(SysJob sysJob) {
        sysJob.setUpdateTime(DateUtils.getNowDate());
        return sysJobMapper.updateSysJob(sysJob);
    }

    /**
     * 删除定时任务调度对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysJobByIds(String ids) {
        return sysJobMapper.deleteSysJobByIds(Convert.toStrArray(ids));
    }


    /**
     * /**
     * 立即运行任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public void run(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        SysJob properties = selectSysJobById(job.getJobId());
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, properties);
        scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, jobGroup), dataMap);
    }


    /*
     *
     * */
    @Override
    @Transactional
    public int changeStatus(SysJob newJob) throws SchedulerException {
        int rows = 0;
        String status = newJob.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
            rows = resumeJob(newJob);
        } else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
            rows = pauseJob(newJob);
        }
        return rows;

    }


    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int pauseJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = jobMapper.updateSysJob(job);
        if (rows > 0) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional
    public int resumeJob(SysJob job) throws SchedulerException {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = jobMapper.updateSysJob(job);
        if (rows > 0) {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 删除定时任务调度信息
     *
     * @param jobId 定时任务调度ID
     * @return 结果
     */
    public int deleteSysJobById(Long jobId) {
        return sysJobMapper.deleteSysJobById(jobId);
    }
}
