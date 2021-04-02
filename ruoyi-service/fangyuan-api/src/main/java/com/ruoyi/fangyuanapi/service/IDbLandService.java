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
}
