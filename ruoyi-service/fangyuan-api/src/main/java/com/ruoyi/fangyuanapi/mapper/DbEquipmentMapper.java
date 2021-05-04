package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbEquipment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设备Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbEquipmentMapper 
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

    @Select("select * from db_equipment where equipment_no=#{equipmentNo} and heartbeat_text LIKE CONCAT('%',#{heartbeatText},'%') limit 1")
    DbEquipment selectByHeart(@Param("heartbeatText") String heartbeatText,@Param("equipmentNo") String equipmentNo);

    int updateDbEquipmentName(DbEquipment dbEquipment);

    DbEquipment selectByHeartbeatText(String heartbeatText);

    List<DbEquipment> selectAllDbEquipment();

}
