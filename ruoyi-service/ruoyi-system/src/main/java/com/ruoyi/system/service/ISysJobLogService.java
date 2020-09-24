package com.ruoyi.system.service;

import com.ruoyi.system.domain.SysJobLog;
import java.util.List;

/**
 * 定时任务调度日志Service接口
 * 
 * @author zheng
 * @date 2020-09-22
 */
public interface ISysJobLogService 
{
    /**
     * 查询定时任务调度日志
     * 
     * @param jobLogId 定时任务调度日志ID
     * @return 定时任务调度日志
     */
    public SysJobLog selectSysJobLogById(Long jobLogId);

    /**
     * 查询定时任务调度日志列表
     * 
     * @param sysJobLog 定时任务调度日志
     * @return 定时任务调度日志集合
     */
    public List<SysJobLog> selectSysJobLogList(SysJobLog sysJobLog);

    /**
     * 新增定时任务调度日志
     * 
     * @param sysJobLog 定时任务调度日志
     * @return 结果
     */
    public int insertSysJobLog(SysJobLog sysJobLog);

    /**
     * 修改定时任务调度日志
     * 
     * @param sysJobLog 定时任务调度日志
     * @return 结果
     */
    public int updateSysJobLog(SysJobLog sysJobLog);

    /**
     * 批量删除定时任务调度日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJobLogByIds(String ids);

    /**
     * 删除定时任务调度日志信息
     * 
     * @param jobLogId 定时任务调度日志ID
     * @return 结果
     */
    public int deleteSysJobLogById(Long jobLogId);
}
