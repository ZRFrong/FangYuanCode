package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbIntelligenceControlRelationship;

import java.util.List;

/**
 * 产品，功能，产品批次，id ，用户id  ，场所关系中间Mapper接口
 * 
 * @author zheng
 * @date 2021-04-08
 */
public interface DbIntelligenceControlRelationshipMapper 
{
    /**
     * 查询产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param id 产品，功能，产品批次，id ，用户id  ，场所关系中间ID
     * @return 产品，功能，产品批次，id ，用户id  ，场所关系中间
     */
    public DbIntelligenceControlRelationship selectDbIntelligenceControlRelationshipById(Long id);

    /**
     * 查询产品，功能，产品批次，id ，用户id  ，场所关系中间列表
     * 
     * @param dbIntelligenceControlRelationship 产品，功能，产品批次，id ，用户id  ，场所关系中间
     * @return 产品，功能，产品批次，id ，用户id  ，场所关系中间集合
     */
    public List<DbIntelligenceControlRelationship> selectDbIntelligenceControlRelationshipList(DbIntelligenceControlRelationship dbIntelligenceControlRelationship);

    /**
     * 新增产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param dbIntelligenceControlRelationship 产品，功能，产品批次，id ，用户id  ，场所关系中间
     * @return 结果
     */
    public int insertDbIntelligenceControlRelationship(DbIntelligenceControlRelationship dbIntelligenceControlRelationship);

    /**
     * 修改产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param dbIntelligenceControlRelationship 产品，功能，产品批次，id ，用户id  ，场所关系中间
     * @return 结果
     */
    public int updateDbIntelligenceControlRelationship(DbIntelligenceControlRelationship dbIntelligenceControlRelationship);

    /**
     * 删除产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param id 产品，功能，产品批次，id ，用户id  ，场所关系中间ID
     * @return 结果
     */
    public int deleteDbIntelligenceControlRelationshipById(Long id);

    /**
     * 批量删除产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbIntelligenceControlRelationshipByIds(String[] ids);
}
