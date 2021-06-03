package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.monitor.DbMonitor;
import org.apache.ibatis.annotations.Param;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

/**
 * 视频监控设备Mapper接口
 *
 * @author zheng
 * @date 2021-05-25
 */
public interface DbMonitorMapper
{
    /**
     * 查询视频监控设备
     *
     * @param id 视频监控设备ID
     * @return 视频监控设备
     */
    public DbMonitor selectDbMonitorById(String id);

    /**
     * 查询指定录像机下的所有通道
     *
     * @param deviceSerial 录像机设备序列号
     * @return 通道集合
     */
    public List<DbMonitor> selectChannelBySerial(String deviceSerial);

    /**
     * 查询视频监控设备
     * @param deviceRegisterCode 视频监控设备注册码
     * @return 视频监控设备
     */
    public DbMonitor selectDbMonitorByRegisterCode(String deviceRegisterCode);

    /**
     * 查询批量视频监控设备
     *
     * @param ids 视频监控设备ID
     * @return 视频监控设备
     */
    public List<DbMonitor> selectDbMonitorByIds(String[] ids);

    /**
     * 查询视频监控设备列表
     *
     * @param dbMonitor 视频监控设备
     * @return 视频监控设备集合
     */
    public List<DbMonitor> selectDbMonitorList(DbMonitor dbMonitor);

    /**
     * 查询所有视频监控设备列表
     * @param type 视频设备类型 （0:录像机  1:视频摄像头 2:通道  ）
     * @return 所有视频监控设备集合
     */
    public List<DbMonitor> listAllVideoMonitorByType(@Param("type") Byte type);

    /**
     * 查询视频监控设备列表
     *
     * @param landId 大棚ID
     * @return 视频监控设备集合
     */
    public List<DbMonitor> selectVideoChannelByLandId(Long landId);

    /**
     * 新增视频监控设备
     *
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    public int insertDbMonitor(DbMonitor dbMonitor);

    /**
     * 批量新增视频通道设备
     *
     * @param channelList 视频通道集合
     * @return 结果
     */
    public int batchInsertChannel(@Param("channelList") List<DbMonitor> channelList);

    /**
     * 批量删除视频通道设备
     *
     * @param deviceSerial 序列号
     * @return 结果
     */
    public int deleteDbChannelMonitorByDeviceSerial(@Param("deviceSerial") String deviceSerial);

    /**
     * 修改视频监控设备
     *
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    public int updateDbMonitor(DbMonitor dbMonitor);

    /**
     * 删除视频监控设备
     *
     * @param monitorId 视频监控设备ID
     * @return 结果
     */
    public int deleteDbMonitorById(Long monitorId);

    /**
     * 批量删除视频监控设备
     *
     * @param deviceRegisterCodes 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbMonitorByIds(String[] deviceRegisterCodes);

    /**
     * 批量删除设备与机柜对应关系
     * @param monitorIds 设备ID
     * @return 结果
     */
    int deleteEquipmentRefMonitorByMonitorIds(String[] monitorIds);
    /**
     * 删除设备与机柜对应关系
     * @param monitorId 设备ID
     * @return 结果
     */
    int deleteEquipmentRefMonitorByMonitorId(Long monitorId);

    /**
     * 绑定设备与机柜关联关系
     * @param equipmentId 机柜ID
     * @param monitorId 设备ID
     * @return 结果
     */
    int bindEquipmentRefMonitor(@Param("equipmentId") Long equipmentId, @Param("monitorId")Long monitorId);
}
