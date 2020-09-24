package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysJobLogMapper;
import com.ruoyi.system.domain.SysJobLog;
import com.ruoyi.system.service.ISysJobLogService;
import com.ruoyi.common.core.text.Convert;

/**
 * 定时任务调度日志Service业务层处理
 * 
 * @author zheng
 * @date 2020-09-22
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService 
{
    @Autowired
    private SysJobLogMapper sysJobLogMapper;

    /**
     * 查询定时任务调度日志
     * 
     * @param jobLogId 定时任务调度日志ID
     * @return 定时任务调度日志
     */
    @Override
    public SysJobLog selectSysJobLogById(Long jobLogId)
    {
        return sysJobLogMapper.selectSysJobLogById(jobLogId);
    }

    /**
     * 查询定时任务调度日志列表
     * 
     * @param sysJobLog 定时任务调度日志
     * @return 定时任务调度日志
     */
    @Override
    public List<SysJobLog> selectSysJobLogList(SysJobLog sysJobLog)
    {
        return sysJobLogMapper.selectSysJobLogList(sysJobLog);
    }

    /**
     * 新增定时任务调度日志
     * 
     * @param sysJobLog 定时任务调度日志
     * @return 结果
     */
    @Override
    public int insertSysJobLog(SysJobLog sysJobLog)
    {
        sysJobLog.setCreateTime(DateUtils.getNowDate());
        return sysJobLogMapper.insertSysJobLog(sysJobLog);
    }

    /**
     * 修改定时任务调度日志
     * 
     * @param sysJobLog 定时任务调度日志
     * @return 结果
     */
    @Override
    public int updateSysJobLog(SysJobLog sysJobLog)
    {
        sysJobLog.setUpdateTime(DateUtils.getNowDate());
        return sysJobLogMapper.updateSysJobLog(sysJobLog);
    }

    /**
     * 删除定时任务调度日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysJobLogByIds(String ids)
    {
        return sysJobLogMapper.deleteSysJobLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除定时任务调度日志信息
     * 
     * @param jobLogId 定时任务调度日志ID
     * @return 结果
     */
    public int deleteSysJobLogById(Long jobLogId)
    {
        return sysJobLogMapper.deleteSysJobLogById(jobLogId);
    }
}
