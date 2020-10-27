package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;

import com.ruoyi.system.domain.DbLandEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbLandEquipmentMapper;
import com.ruoyi.fangyuanapi.service.IDbLandEquipmentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备和土地中间Service业务层处理
 * 
 * @author zheng
 * @date 2020-09-30
 */
@Service
public class DbLandEquipmentServiceImpl implements IDbLandEquipmentService 
{
    @Autowired
    private DbLandEquipmentMapper dbLandEquipmentMapper;

    /**
     * 查询设备和土地中间
     * 
     * @param dbLandId 设备和土地中间ID
     * @return 设备和土地中间
     */
    @Override
    public DbLandEquipment selectDbLandEquipmentById(Long dbLandId)
    {
        return dbLandEquipmentMapper.selectDbLandEquipmentById(dbLandId);
    }

    /**
     * 查询设备和土地中间列表
     * 
     * @param dbLandEquipment 设备和土地中间
     * @return 设备和土地中间
     */
    @Override
    public List<DbLandEquipment> selectDbLandEquipmentList(DbLandEquipment dbLandEquipment)
    {
        return dbLandEquipmentMapper.selectDbLandEquipmentList(dbLandEquipment);
    }

    /**
     * 新增设备和土地中间
     * 
     * @param dbLandEquipment 设备和土地中间
     * @return 结果
     */
    @Override
    public int insertDbLandEquipment(DbLandEquipment dbLandEquipment)
    {
        return dbLandEquipmentMapper.insertDbLandEquipment(dbLandEquipment);
    }

    /**
     * 修改设备和土地中间
     * 
     * @param dbLandEquipment 设备和土地中间
     * @return 结果
     */
    @Override
    public int updateDbLandEquipment(DbLandEquipment dbLandEquipment)
    {
        return dbLandEquipmentMapper.updateDbLandEquipment(dbLandEquipment);
    }

    /**
     * 删除设备和土地中间对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbLandEquipmentByIds(String ids)
    {
        return dbLandEquipmentMapper.deleteDbLandEquipmentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备和土地中间信息
     * 
     * @param dbLandId 设备和土地中间ID
     * @return 结果
     */
    public int deleteDbLandEquipmentById(Long dbLandId)
    {
        return dbLandEquipmentMapper.deleteDbLandEquipmentById(dbLandId);
    }
}
