package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysJobLog;
import java.util.List;

/**
 * 定时任务调度日志Mapper接口
 * 
 * @author zheng
 * @date 2020-09-22
 */
public interface SysJobLogMapper 
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
     * 删除定时任务调度日志
     * 
     * @param jobLogId 定时任务调度日志ID
     * @return 结果
     */
    public int deleteSysJobLogById(Long jobLogId);

    /**
     * 批量删除定时任务调度日志
     * 
     * @param jobLogIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJobLogByIds(String[] jobLogIds);
}
