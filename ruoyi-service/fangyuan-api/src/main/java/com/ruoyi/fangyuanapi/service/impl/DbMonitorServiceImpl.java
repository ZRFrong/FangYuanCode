package com.ruoyi.fangyuanapi.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.EquipmentMonitorTypeEnum;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.listener.RedisExpireKeyListener;
import com.ruoyi.fangyuanapi.mapper.DbMonitorMapper;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.fangyuanapi.utils.MonitorCloudRequestUtils;
import com.ruoyi.system.domain.monitor.DbMonitor;
import com.ruoyi.system.domain.monitor.DbMonitorTree;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

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
     * 查询所有视频监控设备列表
     * @return 所有视频监控设备
     */
    @Override
    public List<DbMonitor> listAllVideoMonitor()
    {
        return dbMonitorMapper.listAllVideoMonitorByType(null);
    }

    /**
     * 查询树形视频监控设备列表（录像机-通道）
     * @return 查询树形视频监控设备列表（录像机-通道）
     */
    @Override
    public List<DbMonitorTree> listTreeVideoMonitor()
    {
        List<DbMonitorTree> treeList = new ArrayList<>();
        // 查询录像机
        List<DbMonitor> recorderMonitors = dbMonitorMapper.listAllVideoMonitorByType(EquipmentMonitorTypeEnum.VIDEO_RECORDER_DEVICE.getTypeCode());
        // 查询通道
        List<DbMonitor> channelMonitors = dbMonitorMapper.listAllVideoMonitorByType(EquipmentMonitorTypeEnum.CHANNEL_CAMERAS_DEVICE.getTypeCode());
        Map<String, List<DbMonitor>> channelCollect = channelMonitors.stream().collect(Collectors.groupingBy(DbMonitor::getDeviceSerial));

        // 组装树形数据
        recorderMonitors.forEach(v -> {
            DbMonitorTree dbMonitorTree = new DbMonitorTree().setParentId(v.getId()).setParentName(v.getDeviceName());
            List<DbMonitor> channelList = channelCollect.get(v.getDeviceSerial());
            if(CollectionUtil.isNotEmpty(channelList)){
                // 组装通道数据
                List<DbMonitorTree> channelTreeList = new ArrayList<>(channelList.size());
                channelList.forEach(channel -> {
                    channelTreeList.add(new DbMonitorTree().setParentName(channel.getDeviceName()).setParentId(channel.getId()));
                });
                dbMonitorTree.setChild(channelTreeList);
                dbMonitorTree.setHasChild(true);
            }
            treeList.add(dbMonitorTree);

        });
        return treeList;
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
            // 通道数据调用宇视接口获取视频流地址
            if(dbMonitor.getDeviceType() == EquipmentMonitorTypeEnum.CHANNEL_CAMERAS_DEVICE.getTypeCode().intValue()){
                JSONObject param = new JSONObject();
                param.put("deviceSerial",dbMonitor.getDeviceSerial());
                param.put("channelNo",dbMonitor.getDeviceChannel());
                param.put("streamIndex",streamIndex);
                param.put("streamType",streamType);
                JSONObject videoUrlJson = getVideo(param);
                dbMonitor.setDeviceVideoUrls(videoUrlJson);
            }
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
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public int insertDbMonitor(DbMonitor dbMonitor)
    {
        // 录像机设备添加
        if(dbMonitor.getDeviceType() == EquipmentMonitorTypeEnum.VIDEO_RECORDER_DEVICE.getTypeCode().intValue()){
            // 获取设备序列号
            String deviceSerial = MonitorCloudRequestUtils.addDevice(dbMonitor.getDeviceRegisterCode(), dbMonitor.getDeviceName());
            dbMonitor.setDeviceSerial(deviceSerial);
            int insertStatus = dbMonitorMapper.insertDbMonitor(dbMonitor);
            // 同步通道添加
            syncChannel(dbMonitor.getId());
            return insertStatus;
        }

        //  摄像头添加未处理 预留位置
        if(dbMonitor.getDeviceType() == EquipmentMonitorTypeEnum.CAMERA_DEVICE.getTypeCode().intValue()){
            int insertStatus = dbMonitorMapper.insertDbMonitor(dbMonitor);
            // 插入设备与机柜绑定关系
            if(dbMonitor.getEquipmentId() != null) dbMonitorMapper.bindEquipmentRefMonitor(dbMonitor.getEquipmentId(),dbMonitor.getId());
            return insertStatus;
        }

        // 通道摄像头添加
        if(dbMonitor.getDeviceType() == EquipmentMonitorTypeEnum.CHANNEL_CAMERAS_DEVICE.getTypeCode().intValue()){
            // 通道表单添加下的状态
            final byte channelFormAddStatus = 0;
            DbMonitor orignData = dbMonitorMapper.selectDbMonitorById(String.valueOf(dbMonitor.getId()));
            Assert.notNull(orignData,"通道不存在,请重新同步录像机通道");
            if(orignData.getChannelStatus() == channelFormAddStatus){
                throw  new Exception("该通道已经添加");
            }
            // 插入设备与机柜绑定关系
            dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(dbMonitor.getId());
            dbMonitorMapper.bindEquipmentRefMonitor(dbMonitor.getEquipmentId(),dbMonitor.getId());
            dbMonitor.setChannelStatus(channelFormAddStatus);
            return dbMonitorMapper.updateDbMonitor(dbMonitor);
        }

        throw  new Exception("视频设备类型参数异常");
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
        Assert.notNull(dbMonitor,"录像机不存在");
        // 现在该录像机下的通道
        List<DbMonitor> origChannelList = dbMonitorMapper.selectChannelBySerial(dbMonitor.getDeviceSerial());
        Set<String> channelNoSet = origChannelList.stream().map(DbMonitor::getDeviceChannel).collect(Collectors.toSet());
        // 调用宇视接口获取通道
        JSONObject jsonObject = MonitorCloudRequestUtils.listChannel(dbMonitor.getDeviceSerial(), 1, 100);
        Integer total = jsonObject.getInt("total");
        JSONObject.JSONArray channelArr = jsonObject.getArr("channelList");
        List<DbMonitor> channelList = new ArrayList<>(channelArr.size());
        // 通道表单添加下的状态
        final byte channelSyncAddStatus = 1;
        // 过滤宇视返回所有通道 只添加同步过来新增的通道，原有通道保持不变
        channelArr.forEach( v -> {
            JSONObject channel = (JSONObject) v;
            if(!channelNoSet.contains(channel.getStr("channelNo"))){
                DbMonitor channelMonitor = new DbMonitor();
                BeanUtils.copyProperties(dbMonitor,channelMonitor);
                channelMonitor.setDeviceName(channel.getStr("channelName"))
                        .setDeviceChannel(channel.getStr("channelNo"))
                        .setDeviceType(EquipmentMonitorTypeEnum.CHANNEL_CAMERAS_DEVICE.getTypeCode().intValue())
                        .setChannelStatus(channelSyncAddStatus)
                        .setEquipmentId(dbMonitor.getEquipmentId())
                        .setId(null);
                channelList.add(channelMonitor);
            }
        });
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

        if(EquipmentMonitorTypeEnum.VIDEO_RECORDER_DEVICE.getTypeCode().intValue() == dbMonitor.getDeviceType()){
            // 录像机设备修改 同步宇视云
            MonitorCloudRequestUtils.updateDevice(dbMonitor.getDeviceSerial(),dbMonitor.getDeviceName());

        } else if(EquipmentMonitorTypeEnum.CHANNEL_CAMERAS_DEVICE.getTypeCode().intValue() == dbMonitor.getDeviceType()
                || EquipmentMonitorTypeEnum.CAMERA_DEVICE.getTypeCode().intValue() == dbMonitor.getDeviceType()) {
            // 通道、摄像头设备修改 重新绑定与机柜的关联关系
            Long id = dbMonitor.getId();
            dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(id);
            dbMonitorMapper.bindEquipmentRefMonitor(dbMonitor.getEquipmentId(),id);
        }

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
        int operateNum = 0;
        for (String id : idsArr) {
            // 暂时调用单个删除方法 待后续业务稳定在进行优化
            operateNum += deleteDbMonitorById(Long.valueOf(id));
        }
        return operateNum;
        /*// 同步云视频设备进行删除
        List<DbMonitor> dbMonitors = dbMonitorMapper.selectDbMonitorByIds(idsArr);
        dbMonitors.forEach(v -> {
            MonitorCloudRequestUtils.deleteDevice(v.getDeviceSerial());
        });
        dbMonitorMapper.deleteEquipmentRefMonitorByMonitorIds(idsArr);
        return dbMonitorMapper.deleteDbMonitorByIds(idsArr);*/
    }

    /**
     * 删除视频监控设备信息
     *
     * @param monitorId 视频监控设备ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @SneakyThrows
    public int deleteDbMonitorById(Long monitorId)
    {
        DbMonitor dbMonitor = selectDbMonitorById(String.valueOf(monitorId));
        Assert.notNull(dbMonitor,"数据不存在");
        // 录像机删除同步云视频设备进行删除
        if(EquipmentMonitorTypeEnum.VIDEO_RECORDER_DEVICE.getTypeCode().intValue() == dbMonitor.getDeviceType()){
            MonitorCloudRequestUtils.deleteDevice(dbMonitor.getDeviceSerial());
            List<DbMonitor> channelList = dbMonitorMapper.selectChannelBySerial(dbMonitor.getDeviceSerial());
            // 同步删除下级通道
            dbMonitorMapper.deleteDbChannelMonitorByDeviceSerial(dbMonitor.getDeviceSerial());
            // todo 同步删除下级通道与机柜设备的关联关系
            if(CollectionUtil.isNotEmpty(channelList)){
                Set<String> collect = channelList.stream().map((v) -> {return String.valueOf(v.getId());}).collect(Collectors.toSet());
                String arr[] = new String[collect.size()];
                collect.toArray(arr);
                dbMonitorMapper.deleteEquipmentRefMonitorByMonitorIds(arr);
            }
            return dbMonitorMapper.deleteDbMonitorById(monitorId);
        }

        else if(EquipmentMonitorTypeEnum.CHANNEL_CAMERAS_DEVICE.getTypeCode().intValue() == dbMonitor.getDeviceType()) {
            // 删除关联关系
            dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(monitorId);
            // 通道状态修改同步添加的状态
            dbMonitor.setChannelStatus((byte)1);
            return dbMonitorMapper.updateDbMonitor(dbMonitor);
        }

        else if(EquipmentMonitorTypeEnum.CAMERA_DEVICE.getTypeCode().intValue() == dbMonitor.getDeviceType()) {
            // 网络摄像头删除关联关系
            dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(monitorId);
            return dbMonitorMapper.deleteDbMonitorById(monitorId);
        }

        throw  new Exception("删除设备异常");

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
        // 清除心跳
        redisExpireKeyListener.removeHeartbeat(liveId);
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
