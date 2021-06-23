package com.ruoyi.fangyuantcp.mapper;

import com.ruoyi.system.domain.DbEquipment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设备Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbEquipmentMapper1
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

    /**
     * 删除设备
     * 
     * @param equipmentId 设备ID
     * @return 结果
     */
    public int deleteDbEquipmentById(Long equipmentId);

    /**
     * 批量删除设备
     * 
     * @param equipmentIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEquipmentByIds(String[] equipmentIds);

    @Select("SELECT equipment_no from db_equipment where heartbeat_text=#{heartName} GROUP BY equipment_no;")
    List<String> selectByHeartNameToEqumentNo(String heartName);

    /**
     * 根据心跳名查找设备
     * @since: 2.0.0
     * @param heartbeatText
     * @return: com.ruoyi.system.domain.DbEquipment
     * @author: ZHAOXIAOSI
     * @date: 2021/6/23 15:04
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    DbEquipment selectDbEquipmentByHeartName(String heartbeatText);
}
