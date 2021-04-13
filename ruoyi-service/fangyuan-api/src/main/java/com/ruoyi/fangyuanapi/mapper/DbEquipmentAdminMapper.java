package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbEquipmentAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zheng
 * @date 2021-03-30
 */
public interface DbEquipmentAdminMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DbEquipmentAdmin selectDbEquipmentAdminById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbEquipmentAdmin 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DbEquipmentAdmin> selectDbEquipmentAdminList(DbEquipmentAdmin dbEquipmentAdmin);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbEquipmentAdmin 【请填写功能名称】
     * @return 结果
     */
    public int insertDbEquipmentAdmin(DbEquipmentAdmin dbEquipmentAdmin);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbEquipmentAdmin 【请填写功能名称】
     * @return 结果
     */
    public int updateDbEquipmentAdmin(DbEquipmentAdmin dbEquipmentAdmin);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbEquipmentAdminById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEquipmentAdminByIds(String[] ids);

    List<Long> selectEquipmentIdByUserId(Long userId);

    List<DbEquipmentAdmin> selectDbEquipmentAdminByLandId(List<Long> landIds);

    DbEquipmentAdmin selectDbEquipmentAdminByUserIdAndLandId(@Param("landId") Long landId,@Param("userId") Long userId,@Param("equipmentId") String equipmentId);
}
