package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbAttentionMapper;
import com.ruoyi.system.domain.DbAttention;
import com.ruoyi.fangyuanapi.service.IDbAttentionService;
import com.ruoyi.common.core.text.Convert;

/**
 * 关注和被关注Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbAttentionServiceImpl implements IDbAttentionService 
{
    @Autowired
    private DbAttentionMapper dbAttentionMapper;

    /**
     * 查询关注和被关注
     * 
     * @param id 关注和被关注ID
     * @return 关注和被关注
     */
    @Override
    public DbAttention selectDbAttentionById(Long id)
    {
        return dbAttentionMapper.selectDbAttentionById(id);
    }

    /**
     * 查询关注和被关注列表
     * 
     * @param dbAttention 关注和被关注
     * @return 关注和被关注
     */
    @Override
    public List<DbAttention> selectDbAttentionList(DbAttention dbAttention)
    {
        return dbAttentionMapper.selectDbAttentionList(dbAttention);
    }

    /**
     * 新增关注和被关注
     * 
     * @param dbAttention 关注和被关注
     * @return 结果
     */
    @Override
    public int insertDbAttention(DbAttention dbAttention)
    {
        return dbAttentionMapper.insertDbAttention(dbAttention);
    }

    /**
     * 修改关注和被关注
     * 
     * @param dbAttention 关注和被关注
     * @return 结果
     */
    @Override
    public int updateDbAttention(DbAttention dbAttention)
    {
        return dbAttentionMapper.updateDbAttention(dbAttention);
    }

    /**
     * 删除关注和被关注对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbAttentionByIds(String ids)
    {
        return dbAttentionMapper.deleteDbAttentionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除关注和被关注信息
     * 
     * @param id 关注和被关注ID
     * @return 结果
     */
    public int deleteDbAttentionById(Long id)
    {
        return dbAttentionMapper.deleteDbAttentionById(id);
    }
}
