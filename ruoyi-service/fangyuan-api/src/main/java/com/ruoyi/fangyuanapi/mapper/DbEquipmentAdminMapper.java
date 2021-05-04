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

    /**
     * 根据userId获取所管理的大棚id
     * @since: 2.0.0
     * @param userId
     * @return: java.util.List<java.lang.Long>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/25 10:28
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<Long> selectEquipmentIdByUserId(Long userId);

    List<DbEquipmentAdmin> selectDbEquipmentAdminByLandId(List<Long> landIds);

    DbEquipmentAdmin selectDbEquipmentAdminByUserIdAndLandId(@Param("landId") Long landId,@Param("userId") Long userId,@Param("equipmentId") String equipmentId);

    /***
     * 根据用户id 查询所管理的大棚
     * @since: 1.0.0
     * @param userId
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipmentAdmin>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/16 16:41
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbEquipmentAdmin> selectDbEquipmentAdminListByUserId(Long userId);

    DbEquipmentAdmin selectIsSuperAdmin(Long landId);

    /**
     * 根据大棚id查询其下的所有管理员
     * @since: 2.0.0
     * @param landId
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipmentAdmin>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/29 10:12
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<DbEquipmentAdmin> selectDbEquipmentAdminsByLandId(Long landId);

    /**
     * 根据userId批量修改管理员头像
     * @since: 2.0.0
     * @param id
     * @param avatar
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/4/29 18:13
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int updateAdminAvatar(@Param("id") Long id,@Param("avatar") String avatar);

}
