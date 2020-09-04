package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.fangyuanapi.domain.DbLand;
import java.util.List;

/**
 * 土地Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbLandMapper 
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
     * 删除土地
     * 
     * @param landId 土地ID
     * @return 结果
     */
    public int deleteDbLandById(Long landId);

    /**
     * 批量删除土地
     * 
     * @param landIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbLandByIds(String[] landIds);
}
