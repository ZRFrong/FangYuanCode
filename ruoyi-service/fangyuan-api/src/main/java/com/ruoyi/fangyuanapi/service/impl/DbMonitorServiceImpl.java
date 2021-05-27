package com.ruoyi.fangyuanapi.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.EquipmentMonitorTypeEnum;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.fangyuanapi.conf.MonitorCloudApiConf;
import com.ruoyi.fangyuanapi.mapper.DbMonitorMapper;
import com.ruoyi.fangyuanapi.service.IDbMonitorService;
import com.ruoyi.system.domain.monitor.DbMonitor;
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

    /**
     * 查询视频监控设备
     * 
     * @param deviceRegisterCode 视频监控设备ID
     * @return 视频监控设备
     */
    @Override
    public DbMonitor selectDbMonitorById(String deviceRegisterCode)
    {
        return dbMonitorMapper.selectDbMonitorById(deviceRegisterCode);
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
     * 新增视频监控设备
     * 
     * @param dbMonitor 视频监控设备
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDbMonitor(DbMonitor dbMonitor)
    {
        Long equipmentId = dbMonitor.getEquipmentId();
        int insertStatus = dbMonitorMapper.insertDbMonitor(dbMonitor);
        Long id = dbMonitor.getId();
        dbMonitorMapper.bindEquipmentRefMonitor(equipmentId,id);
        return insertStatus;
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
        Long equipmentId = dbMonitor.getEquipmentId();
        Long id = dbMonitor.getId();
        dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(id);
        dbMonitorMapper.bindEquipmentRefMonitor(equipmentId,id);
        return dbMonitorMapper.updateDbMonitor(dbMonitor);
    }

    /**
     * 删除视频监控设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbMonitorByIds(String ids)
    {
        String[] idsArr = Convert.toStrArray(ids);
        dbMonitorMapper.deleteEquipmentRefMonitorByMonitorIds(idsArr);
        return dbMonitorMapper.deleteDbMonitorByIds(idsArr);
    }

    /**
     * 删除视频监控设备信息
     * 
     * @param monitorId 视频监控设备ID
     * @return 结果
     */
    public int deleteDbMonitorById(Long monitorId)
    {
        dbMonitorMapper.deleteEquipmentRefMonitorByMonitorId(monitorId);
        return dbMonitorMapper.deleteDbMonitorById(monitorId);
    }
}
