package com.ruoyi.fangyuanapi.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.EquipmentMonitorTypeEnum;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.listener.RedisExpireKeyListener;
import com.ruoyi.fangyuanapi.mapper.DbMonitorMapper;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.fangyuanapi.utils.MonitorCloudRequestUtils;
import com.ruoyi.system.domain.monitor.DbMonitor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频监控设备Service业务层处理
 * 
 * @author zheng
 * @date 2021-05-25
 */
@Service
public class DbMonitorServiceImpl implements IDbMonitorService
{
    @Autowired
    private DbMonitorMapper dbMonitorMapper;
    @Autowired
    private RedisExpireKeyListener redisExpireKeyListener;


    /**
     * 查询视频监控设备
     * 
     * @param id 视频监控设备ID
     * @return 视频监控设备
     */
    @Override
    public DbMonitor selectDbMonitorById(String id)
    {
        return dbMonitorMapper.selectDbMonitorById(id);
    }

    /**
     * 查询视频监控设备类型列表
     *
     * @return 视频监控设备
     */
    @Override
    public List<JSONObject> selectDbMonitorTypeList()
    {
        List<JSONObject> typeData = new ArrayList<>();
        for (EquipmentMonitorTypeEnum equipmentMonitorTypeEnum : EquipmentMonitorTypeEnum.values()) {
            JSONObject singleTypeData = new JSONObject();
            singleTypeData.put("typeCode",equipmentMonitorTypeEnum.getTypeCode());
            singleTypeData.put("typeName",equipmentMonitorTypeEnum.getTypeName());
            typeData.add(singleTypeData);
        }
        return typeData;
    }

    /**
     * 查询视频监控设备列表
     * 
     * @param dbMonitor 视频监控设备
     * @return 视频监控设备
     */
    @Override
    public List<DbMonitor> selectDbMonitorList(DbMonitor dbMonitor)
    {
        return dbMonitorMapper.selectDbMonitorList(dbMonitor);
    }

    /**
     * 通过大棚ID获取视频流地址
     * @param landId 大棚ID
     * @return 通道集合
     */
    @Override
    public List<DbMonitor> selectVideoChannel(Long landId) {
        List<DbMonitor> dbMonitors = dbMonitorMapper.selectVideoChannelByLandId(landId);
        // 媒体流类型，默认为live,取值live为直播，record为回放
        final String streamType = "live";
        // 媒体流索引
        final Integer streamIndex = 0;
        for (DbMonitor dbMonitor : dbMonitors) {
            JSONObject param = new JSONObject();
            param.put("deviceSerial",dbMonitor.getDeviceSerial());
            param.put("channelNo",dbMonitor.getDeviceChannel());
            param.put("streamIndex",streamIndex);
            param.put("streamType",streamType);
            JSONObject videoUrlJson = getVideo(param);
            dbMonitor.setDeviceVideoUrls(videoUrlJson);
        }
        return dbMonitors;
    }

    /**
     * 新增视频监控设备
     * 
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDbMonitor(DbMonitor dbMonitor)
    {
        // 获取设备序列号
        String deviceSerial = MonitorCloudRequestUtils.addDevice(dbMonitor.getDeviceRegisterCode(), dbMonitor.getDeviceName());
        dbMonitor.setDeviceSerial(deviceSerial);
        int insertStatus = dbMonitorMapper.insertDbMonitor(dbMonitor);
        // 插入设备与机柜绑定关系
        dbMonitorMapper.bindEquipmentRefMonitor(dbMonitor.getEquipmentId(),dbMonitor.getId());
        return insertStatus;
    }

    /**
     * 根据设备同步通道列表
     * @param monitorId 视频设备ID
     * @return 操作数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncChannel(Long monitorId) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(String.valueOf(monitorId));
        JSONObject jsonObject = MonitorCloudRequestUtils.listChannel(dbMonitor.getDeviceSerial(), 1, 100);
        Integer total = jsonObject.getInt("total");
        JSONObject.JSONArray deviceList = jsonObject.getArr("channelList");
        List<DbMonitor> channelList = new ArrayList<>(deviceList.size());
        deviceList.forEach( v -> {
            JSONObject channel = (JSONObject) v;
            DbMonitor channelMonitor = new DbMonitor();
            BeanUtils.copyProperties(dbMonitor,channelMonitor);
            channelMonitor.setDeviceName(channel.getStr("channelName"))
                    .setDeviceChannel(channel.getStr("channelNo"))
                    .setDeviceType(1)
                    .setEquipmentId(dbMonitor.getEquipmentId())
                    .setId(null);
            channelList.add(channelMonitor);
        });
        // 删除之前的通道数据
        dbMonitorMapper.deleteDbChannelMonitorByDeviceSerial(dbMonitor.getDeviceSerial());
        return dbMonitorMapper.batchInsertChannel(channelList);
    }

    /**
     * 修改视频监控设备
     * 
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDbMonitor(DbMonitor dbMonitor)
    {
        // 同步视屏云设备修改
        if(0 == dbMonitor.getDeviceType()){
            MonitorCloudRequestUtils.updateDevice(dbMonitor.getDeviceSerial(),dbMonitor.getDeviceName());
        }
        Long id = dbMonitor.getId();
        dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(id);
        dbMonitorMapper.bindEquipmentRefMonitor(dbMonitor.getEquipmentId(),id);
        return dbMonitorMapper.updateDbMonitor(dbMonitor);
    }

    /**
     * 删除视频监控设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDbMonitorByIds(String ids)
    {
        String[] idsArr = Convert.toStrArray(ids);
        // 同步云视频设备进行删除
        List<DbMonitor> dbMonitors = dbMonitorMapper.selectDbMonitorByIds(idsArr);
        dbMonitors.forEach(v -> {
            MonitorCloudRequestUtils.deleteDevice(v.getDeviceSerial());
        });
        dbMonitorMapper.deleteEquipmentRefMonitorByMonitorIds(idsArr);
        return dbMonitorMapper.deleteDbMonitorByIds(idsArr);
    }

    /**
     * 删除视频监控设备信息
     * 
     * @param monitorId 视频监控设备ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteDbMonitorById(Long monitorId)
    {
        // 同步云视频设备进行删除
        MonitorCloudRequestUtils.deleteDevice(
                dbMonitorMapper.selectDbMonitorById(monitorId.toString()).getDeviceSerial()
        );
        dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(monitorId);
        return dbMonitorMapper.deleteDbMonitorById(monitorId);
    }

    /**
     * 获取设备通道列表
     * @param monitorId 视屏设备ID
     * @param pageNo 分页起始页，默认从1开始
     * @param pageSize 分页大小，默认为10，最大为100
     * @return 通道集合数据
     */
    @Override
    public JSONObject listChannel(Long monitorId, Integer pageNo, Integer pageSize) {
        String deviceSerial = dbMonitorMapper.selectDbMonitorById(monitorId.toString()).getDeviceSerial();
        return MonitorCloudRequestUtils.listChannel(deviceSerial,pageNo,pageSize);
    }

    /**
     * 设备抓图
     * @param monitorId 视屏设备ID
     * @param channel 通道号
     * @return 图像URL
     */
    @Override
    public String getCapture(Long monitorId,Integer channel) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        String captureUrl = MonitorCloudRequestUtils.getCapture(dbMonitor.getDeviceSerial(), channel);
        dbMonitorMapper.updateDbMonitor(dbMonitor.setDeviceCameraImage(captureUrl));
        return captureUrl;
    }

    @Override
    public void startPtz(Long monitorId, Integer channel, Integer command, Integer speed) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        MonitorCloudRequestUtils.startPtz(dbMonitor.getDeviceSerial(),channel,command,speed);
    }

    @Override
    public void stopPtz(Long monitorId, Integer channel, Integer command) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        MonitorCloudRequestUtils.stopPtz(dbMonitor.getDeviceSerial(),channel,command);
    }

    @Override
    public JSONObject listPreset(Long monitorId, Integer channel, Integer pageNo, Integer pageSize) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        return MonitorCloudRequestUtils.listPreset(dbMonitor.getDeviceSerial(),channel,pageNo,pageSize);
    }

    @Override
    public String addPreset(Long monitorId, Integer channel, Integer presetIndex, String name) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        return MonitorCloudRequestUtils.addPreset(dbMonitor.getDeviceSerial(),channel,presetIndex,name);
    }

    @Override
    public void updatePreset(Long monitorId, Integer channel, Integer presetIndex, String name) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        MonitorCloudRequestUtils.updatePreset(dbMonitor.getDeviceSerial(),channel,presetIndex,name);
    }

    @Override
    public void deletePreset(Long monitorId, Integer channel, Integer presetIndex) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        MonitorCloudRequestUtils.deletePreset(dbMonitor.getDeviceSerial(),channel,presetIndex);
    }

    @Override
    public void invokePreset(Long monitorId, Integer channel, Integer presetIndex) {
        DbMonitor dbMonitor = dbMonitorMapper.selectDbMonitorById(monitorId.toString());
        MonitorCloudRequestUtils.invokePreset(dbMonitor.getDeviceSerial(),channel,presetIndex);
    }

    /**
     * 获取指定设备通道的直播
     * @param param 获取视屏通道地址参数
     * @return 视频流地址
     */
    @Override
    @SneakyThrows
    public JSONObject getVideo(JSONObject param) {
        String deviceSerial = param.getStr("deviceSerial");
        Integer channelNo = param.getInt("channelNo");
        Integer streamIndex = param.getInt("streamIndex");
        String streamType = param.getStr("streamType");
        Long startTime = null;
        Long endTime = null;
        Integer recordTypes = null;
        // 回放类型
        if("record".equals(streamType)){
            startTime = param.getLong("startTime");
            endTime = param.getLong("endTime");
            recordTypes = param.getInt("recordTypes");
        }
        return MonitorCloudRequestUtils.getVideo(deviceSerial, channelNo, streamIndex, streamType, startTime, endTime, recordTypes);
    }

    /**
     * 开启直播/回放
     * @param videoUrl 视频流地址
     */
    @Override
    public String startVideo(String videoUrl) {
        return MonitorCloudRequestUtils.startVideo(videoUrl);
    }

    /**
     * 关闭直播/回放
     * @param liveId 视频流ID
     * @param videoUrl 视频流地址
     */
    @Override
    @SneakyThrows
    public void stopVideo(String liveId,String videoUrl) {
        if(StringUtils.isNotBlank(liveId)){
            MonitorCloudRequestUtils.stopVideo(liveId);
        } else if(StringUtils.isBlank(liveId) && StringUtils.isNotBlank(videoUrl)){
            liveId = MonitorCloudRequestUtils.startVideo(videoUrl);
            MonitorCloudRequestUtils.stopVideo(liveId);
        }else {
            throw new Exception("参数异常");
        }
    }

    /**
     * 视频流心跳监测
     * @param liveId 视频流ID
     */
    @Override
    public void heartVideoStream(String liveId) {
        redisExpireKeyListener.addHeartbeat(liveId);
    }
}
