package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysJob;
import java.util.List;

/**
 * 定时任务调度Mapper接口
 * 
 * @author zheng
 * @date 2020-09-22
 */
public interface SysJobMapper 
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
     * 删除定时任务调度
     * 
     * @param jobId 定时任务调度ID
     * @return 结果
     */
    public int deleteSysJobById(Long jobId);

    /**
     * 批量删除定时任务调度
     * 
     * @param jobIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysJobByIds(String[] jobIds);

}
