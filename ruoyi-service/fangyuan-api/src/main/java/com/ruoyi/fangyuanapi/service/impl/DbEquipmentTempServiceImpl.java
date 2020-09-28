package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbEquipmentTemp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentTempMapper;
import com.ruoyi.fangyuanapi.service.IDbEquipmentTempService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备模板Service业务层处理
 * 
 * @author zheng
 * @date 2020-09-25
 */
@Service
public class DbEquipmentTempServiceImpl implements IDbEquipmentTempService 
{
    @Autowired
    private DbEquipmentTempMapper dbEquipmentTempMapper;

    /**
     * 查询设备模板
     * 
     * @param equipmentTemId 设备模板ID
     * @return 设备模板
     */
    @Override
    public DbEquipmentTemp selectDbEquipmentTempById(Long equipmentTemId)
    {
        return dbEquipmentTempMapper.selectDbEquipmentTempById(equipmentTemId);
    }

    /**
     * 查询设备模板列表
     * 
     * @param dbEquipmentTemp 设备模板
     * @return 设备模板
     */
    @Override
    public List<DbEquipmentTemp> selectDbEquipmentTempList(DbEquipmentTemp dbEquipmentTemp)
    {
        return dbEquipmentTempMapper.selectDbEquipmentTempList(dbEquipmentTemp);
    }

    /**
     * 新增设备模板
     * 
     * @param dbEquipmentTemp 设备模板
     * @return 结果
     */
    @Override
    public int insertDbEquipmentTemp(DbEquipmentTemp dbEquipmentTemp)
    {
        dbEquipmentTemp.setCreateTime(DateUtils.getNowDate());
        return dbEquipmentTempMapper.insertDbEquipmentTemp(dbEquipmentTemp);
    }

    /**
     * 修改设备模板
     * 
     * @param dbEquipmentTemp 设备模板
     * @return 结果
     */
    @Override
    public int updateDbEquipmentTemp(DbEquipmentTemp dbEquipmentTemp)
    {
        dbEquipmentTemp.setUpdateTime(DateUtils.getNowDate());
        return dbEquipmentTempMapper.updateDbEquipmentTemp(dbEquipmentTemp);
    }

    /**
     * 删除设备模板对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentTempByIds(String ids)
    {
        return dbEquipmentTempMapper.deleteDbEquipmentTempByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备模板信息
     * 
     * @param equipmentTemId 设备模板ID
     * @return 结果
     */
    public int deleteDbEquipmentTempById(Long equipmentTemId)
    {
        return dbEquipmentTempMapper.deleteDbEquipmentTempById(equipmentTemId);
    }
}
