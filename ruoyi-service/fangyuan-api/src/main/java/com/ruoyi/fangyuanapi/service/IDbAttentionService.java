package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbAttention;
import java.util.List;
import java.util.Map;

/**
 * 关注和被关注Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbAttentionService 
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
     * 批量删除关注和被关注
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbAttentionByIds(String ids);

    /**
     * 删除关注和被关注信息
     * 
     * @param id 关注和被关注ID
     * @return 结果
     */
    public int deleteDbAttentionById(Long id);

    /**
     * 查询关注的人
     * @param userId
     * @return
     */
    List<Map<String,String>> selectDbAttentionByUserId(String userId,Integer currPage);

    /**
     * 取消关注
     * @param loginUserId
     * @param userId
     * @return
     */
    boolean deleteAttention(String loginUserId, Long userId);

    /**
     * 查询粉丝列表
     * @param userId
     * @param currPage
     * @return
     */
    List<Map<String,String>> getFans(String userId, Integer currPage);

    /**
     * 查询关注的人id
     * @param userId 当前用户id
     * @return ids
     */
    List<Long> selectReplyAttentionUserIds(String userId);
}
