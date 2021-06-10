package com.ruoyi.fangyuanapi.service;

import com.ruoyi.common.json.JSONObject;
import com.ruoyi.system.domain.monitor.DbMonitor;
import com.ruoyi.system.domain.monitor.DbMonitorTree;

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
     * 查询所有视频监控设备列表
     * @return 查询所有视频监控设备列表
     */
    public List<DbMonitor> listAllVideoMonitor();

    /**
     * 查询树形视频监控设备列表（录像机-通道）
     * @return 查询树形视频监控设备列表
     */
    public List<DbMonitorTree> listTreeVideoMonitor();

    /**
     * 查询视频监控设备列表
     *
     * @param landId 大棚ID
     * @return 视频监控设备集合
     */
    public List<DbMonitor> selectVideoChannel(Long landId);

    /**
     * 新增视频监控设备
     *
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    public int insertDbMonitor(DbMonitor dbMonitor);

    /**
     * 同步视频通道
     *
     * @param monitorId 视频设备ID
     * @return 结果
     */
    public int syncChannel(Long monitorId);

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

    /**
     * 获取设备通道列表
     * @param monitorId 视屏设备ID
     * @param pageNo 分页起始页，默认从1开始
     * @param pageSize 分页大小，默认为10，最大为100
     */
    JSONObject listChannel( Long monitorId,Integer pageNo,Integer pageSize);

    /**
     * 获取设备抓图
     *
     * @param monitorId 视屏设备ID
     * @param channel 通道号
     * @return 结果
     */
    public String getCapture(Long monitorId,Integer channel);


    /**
     * 开启云平台控制
     * @param monitorId 设备ID
     * @param channel 通道
     * @param command 操作命令
     * @param speed 云台转速
     */
    void startPtz( Long monitorId, Integer channel, Integer command, Integer speed);

    /**
     * 停止云平台控制
     * @param monitorId 设备ID
     * @param channel 通道
     * @param command 操作命令
     */
    void stopPtz( Long monitorId, Integer channel, Integer command);

    /**
     * 停止云平台控制
     * @param monitorId 设备ID
     * @param channel 通道
     * @return 数据列表
     */
    JSONObject listPreset( Long monitorId, Integer channel, Integer pageNo,Integer pageSize);

    /**
     * 添加预置位
     * @param monitorId 设备ID
     * @param channel 通道
     * @param presetIndex 预置位索引
     * @param name 名称
     * @return 预置位截图
     */
    String addPreset( Long monitorId, Integer channel, Integer presetIndex, String name);

    /**
     * 修改预置位
     * @param monitorId 设备ID
     * @param channel 通道
     * @param presetIndex 预置位索引
     * @param name 名称
     */
    void updatePreset( Long monitorId, Integer channel, Integer presetIndex, String name);

    /**
     * 删除预置位
     * @param monitorId 设备ID
     * @param channel 通道
     * @param presetIndex 预置位索引
     */
    void deletePreset( Long monitorId, Integer channel, Integer presetIndex);

    /**
     * 激活预置位
     * @param monitorId 设备ID
     * @param channel 通道
     * @param presetIndex 预置位索引
     */
    void invokePreset( Long monitorId, Integer channel, Integer presetIndex);

    /**
     *  获取指定设备通道的直播/回放视频地址
     * @param param 获取视屏通道地址参数
     */
    JSONObject getVideo(JSONObject param) ;

    /**
     * 开启直播/回放
     * @param videoUrl 视频播放地址
     * @return liveId 视频流ID
     */
    String startVideo( String videoUrl);

    /**
     * 关闭直播/回放
     * @param liveId 视频流ID
     */
    void stopVideo(String liveId,String videoUrl);

    /**
     * redis缓存同步视频播放数量
     * @param liveId 视频流ID
     * @param updateNum 更新播放数量
     * @return 当前视频播放数
     */
    int syncVideoPlay(String liveId,int updateNum);

    /**
     * 流心跳监测
     * @param liveId 视频流ID
     */
    void heartVideoStream(String liveId);
}
