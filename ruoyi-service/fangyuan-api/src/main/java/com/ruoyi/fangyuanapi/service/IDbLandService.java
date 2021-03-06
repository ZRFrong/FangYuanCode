package com.ruoyi.fangyuanapi.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbLand;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.Ztree;

/**
 * 土地Service接口
 * 
 * @author zheng
 * @date 2020-09-24
 */
public interface IDbLandService 
{
    /**
     * 查询土地
     * 
     * @param landId 土地ID
     * @return 土地
     */
    public DbLand selectDbLandById(Long landId);

    /**
     * 查询土地列表
     * 
     * @param dbLand 土地
     * @return 土地集合
     */
    public List<DbLand> selectDbLandList(DbLand dbLand);

    /**
     * 新增土地
     * 
     * @param dbLand 土地
     * @return 结果
     */
    public int insertDbLand(DbLand dbLand);

    /**
     * 修改土地
     * 
     * @param dbLand 土地
     * @return 结果
     */
    public int updateDbLand(DbLand dbLand);

    /**
     * 批量删除土地
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbLandByIds(String ids);

    /**
     * 删除土地信息
     * 
     * @param landId 土地ID
     * @return 结果
     */
    public int deleteDbLandById(Long landId);

    /**
     * 查询土地树列表
     * 
     * @return 所有土地信息
     */
    public List<Ztree> selectDbLandTree();

    R weChatAddSave(DbLand dbLand);

    List<DbLand> selectDbLandListByUserId(Long aLong);

    List<Long> groupByUserId();

    List<DbLand> selectDbLandNoSiteList(DbLand dbLand);

    List<DbLand> selectDbLandWeChatList(DbLand dbLand);

    List<Map<String,Object>> selectLandOperationByLandId(Long landId);

    List<Map<String,Object>> selectDbLandByUserIdAndSideId(Long aLong);

    List<DbLand> selectDbLandsByUserId(Long userId);

    List<DbLand> selectDbLandListByUserIdAndSideId(Long userId);

    /**
     * 将设备绑定到大棚，修改操作
     * @since: 2.0.0
     * @param landId 大棚id
     * @param userId 用户id
     * @param equipmentId 设备id
     * @return: boolean
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 11:22
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    boolean updateEquipmentToLand(Long landId, Long equipmentId ,String userId);

    /**
     * 将设备与大棚绑定，新增操作
     * @since: 2.0.0
     * @param dbLand
     * @param userId
     * @param equipmentId
     * @return: boolean
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 13:58
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    boolean insertEquipmentToLand(DbLand dbLand, String userId, Long equipmentId,Long qrCodeId);

    /**
     * 根据userId 查询不等于地块的
     * @since: 2.0.0
     * @param userId
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/28 15:58
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    List<Map<String,String>> selectDbLandsByUserIdAndSiteId(Long userId);

    /**
     * 获取该用户下的所有大棚名称，及要绑定设备得操作信息
     * @since: 2.0.0
     * @param equipmentId
     * @param userId
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @author: ZHAOXIAOSI
     * @date: 2021/5/8 11:09
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    Map<String,Object> getLandAndOperateInfo(Long equipmentId, Long userId,Integer type);
}
