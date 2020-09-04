package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.fangyuanapi.domain.DbAttention;
import java.util.List;

/**
 * 关注和被关注Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbAttentionMapper 
{
    /**
     * 查询关注和被关注
     * 
     * @param id 关注和被关注ID
     * @return 关注和被关注
     */
    public DbAttention selectDbAttentionById(Long id);

    /**
     * 查询关注和被关注列表
     * 
     * @param dbAttention 关注和被关注
     * @return 关注和被关注集合
     */
    public List<DbAttention> selectDbAttentionList(DbAttention dbAttention);

    /**
     * 新增关注和被关注
     * 
     * @param dbAttention 关注和被关注
     * @return 结果
     */
    public int insertDbAttention(DbAttention dbAttention);

    /**
     * 修改关注和被关注
     * 
     * @param dbAttention 关注和被关注
     * @return 结果
     */
    public int updateDbAttention(DbAttention dbAttention);

    /**
     * 删除关注和被关注
     * 
     * @param id 关注和被关注ID
     * @return 结果
     */
    public int deleteDbAttentionById(Long id);

    /**
     * 批量删除关注和被关注
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbAttentionByIds(String[] ids);
}
