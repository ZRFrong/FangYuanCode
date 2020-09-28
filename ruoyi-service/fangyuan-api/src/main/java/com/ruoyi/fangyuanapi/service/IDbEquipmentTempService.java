package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbEquipmentTemp;

import java.util.List;

/**
 * 设备模板Service接口
 * 
 * @author zheng
 * @date 2020-09-25
 */
public interface IDbEquipmentTempService 
{
    /**
     * 查询设备模板
     * 
     * @param equipmentTemId 设备模板ID
     * @return 设备模板
     */
    public DbEquipmentTemp selectDbEquipmentTempById(Long equipmentTemId);

    /**
     * 查询设备模板列表
     * 
     * @param dbEquipmentTemp 设备模板
     * @return 设备模板集合
     */
    public List<DbEquipmentTemp> selectDbEquipmentTempList(DbEquipmentTemp dbEquipmentTemp);

    /**
     * 新增设备模板
     * 
     * @param dbEquipmentTemp 设备模板
     * @return 结果
     */
    public int insertDbEquipmentTemp(DbEquipmentTemp dbEquipmentTemp);

    /**
     * 修改设备模板
     * 
     * @param dbEquipmentTemp 设备模板
     * @return 结果
     */
    public int updateDbEquipmentTemp(DbEquipmentTemp dbEquipmentTemp);

    /**
     * 批量删除设备模板
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEquipmentTempByIds(String ids);

    /**
     * 删除设备模板信息
     * 
     * @param equipmentTemId 设备模板ID
     * @return 结果
     */
    public int deleteDbEquipmentTempById(Long equipmentTemId);
}
