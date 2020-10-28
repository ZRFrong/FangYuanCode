package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbLandEquipment;

import java.util.List;

/**
 * 设备和土地中间Service接口
 * 
 * @author zheng
 * @date 2020-09-30
 */
public interface IDbLandEquipmentService 
{
    /**
     * 查询设备和土地中间
     * 
     * @param dbLandId 设备和土地中间ID
     * @return 设备和土地中间
     */
    public DbLandEquipment selectDbLandEquipmentById(Long dbLandId);

    /**
     * 查询设备和土地中间列表
     * 
     * @param dbLandEquipment 设备和土地中间
     * @return 设备和土地中间集合
     */
    public List<DbLandEquipment> selectDbLandEquipmentList(DbLandEquipment dbLandEquipment);

    /**
     * 新增设备和土地中间
     * 
     * @param dbLandEquipment 设备和土地中间
     * @return 结果
     */
    public int insertDbLandEquipment(DbLandEquipment dbLandEquipment);

    /**
     * 修改设备和土地中间
     * 
     * @param dbLandEquipment 设备和土地中间
     * @return 结果
     */
    public int updateDbLandEquipment(DbLandEquipment dbLandEquipment);

    /**
     * 批量删除设备和土地中间
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbLandEquipmentByIds(String ids);

    /**
     * 删除设备和土地中间信息
     * 
     * @param dbLandId 设备和土地中间ID
     * @return 结果
     */
    public int deleteDbLandEquipmentById(Long dbLandId);
}
