package com.ruoyi.fangyuantcp.service;

import com.ruoyi.fangyuantcp.aspect.FeedbackIntercept;
import com.ruoyi.system.domain.DbEquipment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public interface IDbEquipmentService
{
    /**
     * 查询设备
     * 
     * @param equipmentId 设备ID
     * @return 设备
     */
    public DbEquipment selectDbEquipmentById(Long equipmentId);

    /**
     * 查询设备列表
     * 
     * @param dbEquipment 设备
     * @return 设备集合
     */
    public List<DbEquipment> selectDbEquipmentList(DbEquipment dbEquipment);

    /**
     * 新增设备
     * 
     * @param dbEquipment 设备
     * @return 结果
     */
    public int insertDbEquipment(DbEquipment dbEquipment);

    /**
     * 修改设备
     * 
     * @param dbEquipment 设备
     * @return 结果
     */
    public int updateDbEquipment(DbEquipment dbEquipment);


    public DbEquipment updateDbEquipmentFeedback(DbEquipment dbEquipment);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEquipmentByIds(String ids);

    /**
     * 删除设备信息
     * 
     * @param equipmentId 设备ID
     * @return 结果
     */
    public int deleteDbEquipmentById(Long equipmentId);


    DbEquipment selectDbEquipmentByHeartName(String heartbeatText);
}
