package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbEquipmentComponent;

import java.util.List;

/**
 * 版本加功能Mapper接口
 * 
 * @author zheng
 * @date 2021-04-08
 */
public interface DbEquipmentComponentMapper 
{
    /**
     * 查询版本加功能
     * 
     * @param id 版本加功能ID
     * @return 版本加功能
     */
    public DbEquipmentComponent selectDbEquipmentComponentById(Long id);

    /**
     * 查询版本加功能列表
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 版本加功能集合
     */
    public List<DbEquipmentComponent> selectDbEquipmentComponentList(DbEquipmentComponent dbEquipmentComponent);

    /**
     * 新增版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    public int insertDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent);

    /**
     * 修改版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    public int updateDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent);

    /**
     * 删除版本加功能
     * 
     * @param id 版本加功能ID
     * @return 结果
     */
    public int deleteDbEquipmentComponentById(Long id);

    /**
     * 批量删除版本加功能
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEquipmentComponentByIds(String[] ids);

    /**
     * 根据设备id查询出对应功能对象
     * @since: 2.0.0
     * @param equipmentId
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipmentComponent>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 13:33
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbEquipmentComponent> selectDbEquipmentComponentByEquipmentId(Long equipmentId);

    /**
     * 批量查询
     * @since: 2.0.0
     * @param split
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipmentComponent>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/25 11:28
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbEquipmentComponent> selectDbEquipmentComponentByIds(String[] ids);
}
