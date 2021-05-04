package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;

import com.ruoyi.system.domain.DbIntelligenceControlRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbIntelligenceControlRelationshipMapper;
import com.ruoyi.fangyuanapi.service.IDbIntelligenceControlRelationshipService;
import com.ruoyi.common.core.text.Convert;

/**
 * 产品，功能，产品批次，id ，用户id  ，场所关系中间Service业务层处理
 * 
 * @author zheng
 * @date 2021-04-08
 */
@Service
public class DbIntelligenceControlRelationshipServiceImpl implements IDbIntelligenceControlRelationshipService 
{
    @Autowired
    private DbIntelligenceControlRelationshipMapper dbIntelligenceControlRelationshipMapper;

    /**
     * 查询产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param id 产品，功能，产品批次，id ，用户id  ，场所关系中间ID
     * @return 产品，功能，产品批次，id ，用户id  ，场所关系中间
     */
    @Override
    public DbIntelligenceControlRelationship selectDbIntelligenceControlRelationshipById(Long id)
    {
        return dbIntelligenceControlRelationshipMapper.selectDbIntelligenceControlRelationshipById(id);
    }

    /**
     * 查询产品，功能，产品批次，id ，用户id  ，场所关系中间列表
     * 
     * @param dbIntelligenceControlRelationship 产品，功能，产品批次，id ，用户id  ，场所关系中间
     * @return 产品，功能，产品批次，id ，用户id  ，场所关系中间
     */
    @Override
    public List<DbIntelligenceControlRelationship> selectDbIntelligenceControlRelationshipList(DbIntelligenceControlRelationship dbIntelligenceControlRelationship)
    {
        return dbIntelligenceControlRelationshipMapper.selectDbIntelligenceControlRelationshipList(dbIntelligenceControlRelationship);
    }

    /**
     * 新增产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param dbIntelligenceControlRelationship 产品，功能，产品批次，id ，用户id  ，场所关系中间
     * @return 结果
     */
    @Override
    public int insertDbIntelligenceControlRelationship(DbIntelligenceControlRelationship dbIntelligenceControlRelationship)
    {
        return dbIntelligenceControlRelationshipMapper.insertDbIntelligenceControlRelationship(dbIntelligenceControlRelationship);
    }

    /**
     * 修改产品，功能，产品批次，id ，用户id  ，场所关系中间
     * 
     * @param dbIntelligenceControlRelationship 产品，功能，产品批次，id ，用户id  ，场所关系中间
     * @return 结果
     */
    @Override
    public int updateDbIntelligenceControlRelationship(DbIntelligenceControlRelationship dbIntelligenceControlRelationship)
    {
        return dbIntelligenceControlRelationshipMapper.updateDbIntelligenceControlRelationship(dbIntelligenceControlRelationship);
    }

    /**
     * 删除产品，功能，产品批次，id ，用户id  ，场所关系中间对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbIntelligenceControlRelationshipByIds(String ids)
    {
        return dbIntelligenceControlRelationshipMapper.deleteDbIntelligenceControlRelationshipByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品，功能，产品批次，id ，用户id  ，场所关系中间信息
     * 
     * @param id 产品，功能，产品批次，id ，用户id  ，场所关系中间ID
     * @return 结果
     */
    public int deleteDbIntelligenceControlRelationshipById(Long id)
    {
        return dbIntelligenceControlRelationshipMapper.deleteDbIntelligenceControlRelationshipById(id);
    }
}
