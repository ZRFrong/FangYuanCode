package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbEquipmentAdmin;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zheng
 * @date 2021-03-30
 */
public interface IDbEquipmentAdminService 
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
     * 批量删除【请填写功能名称】
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEquipmentAdminByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbEquipmentAdminById(Long id);

    /**
     * 根据userId获取其所管理的大棚id
     * @since: 2.0.0
     * @param userId
     * @return: java.util.List<java.lang.Long>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/25 10:29
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<Long> selectEquipmentIdByUserId(Long userId);

    /***
     * 根据设备id集合，获取设备下的管理员列表
     * @since: 1.0.0
     * @param landIds
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/7 10:10
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<Map<String,Object>> selectDbEquipmentAdminByLandId(List<Long> landIds);

    /***
     * 根据设备id添加管理员
     * @since: 1.0.0
     * @param landId
     * @param userId
     * @return: com.ruoyi.system.domain.DbEquipment
     * @author: ZHAOXIAOSI
     * @date: 2021/4/7 10:11
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    DbEquipmentAdmin insertEquipmentAdmin(Long landId, String userId,String equipmentId);

    /***
     * 根据大棚id和用户id 查询当前用户是否已为管理员
     * @since: 1.0.0
     * @param landId
     * @param userId
     * @return: com.ruoyi.system.domain.DbEquipmentAdmin
     * @author: ZHAOXIAOSI
     * @date: 2021/4/7 11:27
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    DbEquipmentAdmin selectDbEquipmentAdminByUserIdAndLandId(Long landId, Long userId,String equipmentId);

    /***
     * 根据UserId 获取所管理的大棚
     * @since: 2.0.0
     * @param userId
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipmentAdmin>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 16:38
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbEquipmentAdmin> selectDbEquipmentAdminListByUserId(Long userId);

    /**
     * 方法描述
     * @since: 2.0.0
     * @param landId
     * @return: com.ruoyi.system.domain.DbEquipmentAdmin
     * @author: ZHAOXIAOSI
     * @date: 2021/4/22 11:26
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    DbEquipmentAdmin selectIsSuperAdmin(Long landId);

    /**
     * 根据大棚id查询其下的所有管理员
     * @since: 2.0.0
     * @param landId
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipmentAdmin>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/29 10:10
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbEquipmentAdmin> selectDbEquipmentAdminsByLandId(Long landId);

    /**
     * 根据大棚id获取用户iD 获取大棚名称
     * @since: 2.0.0
     * @param landId
     * @param userId
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author: ZHAOXIAOSI
     * @date: 2021/5/9 14:10
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<Map<String,Object>> selectLandIdAndNameByUserId(Long landId, Long userId);

    int updateLandNameByLandId(Long landId, String nickName);

}
