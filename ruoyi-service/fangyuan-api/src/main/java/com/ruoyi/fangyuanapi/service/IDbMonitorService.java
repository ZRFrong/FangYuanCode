package com.ruoyi.fangyuanapi.service;

import com.ruoyi.common.json.JSONObject;
import com.ruoyi.system.domain.monitor.DbMonitor;

import java.util.List;
import java.util.Map;

/**
 * 视频监控设备Service接口
 * 
 * @author zheng
 * @date 2021-05-25
 */
public interface IDbMonitorService 
{
    /**
     * 查询视频监控设备
     * 
     * @param deviceRegisterCode 视频监控设备ID
     * @return 视频监控设备
     */
    public DbMonitor selectDbMonitorById(String deviceRegisterCode);

    /**
     * 查询视频监控设备类型列表
     * @return 视频监控设备
     */
    public List<JSONObject> selectDbMonitorTypeList();

    /**
     * 查询视频监控设备列表
     * 
     * @param dbMonitor 视频监控设备
     * @return 视频监控设备集合
     */
    public List<DbMonitor> selectDbMonitorList(DbMonitor dbMonitor);

    /**
     * 新增视频监控设备
     * 
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    public int insertDbMonitor(DbMonitor dbMonitor);

    /**
     * 修改视频监控设备
     * 
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    public int updateDbMonitor(DbMonitor dbMonitor);

    /**
     * 批量删除视频监控设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbMonitorByIds(String ids);

    /**
     * 删除视频监控设备信息
     * 
     * @param monitorId 视频监控设备ID
     * @return 结果
     */
    public int deleteDbMonitorById(Long monitorId);
}
