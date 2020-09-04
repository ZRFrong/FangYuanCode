package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.fangyuanapi.domain.DbGiveLike;
import java.util.List;

/**
 * 点赞Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbGiveLikeMapper 
{
    /**
     * 查询点赞
     * 
     * @param id 点赞ID
     * @return 点赞
     */
    public DbGiveLike selectDbGiveLikeById(Long id);

    /**
     * 查询点赞列表
     * 
     * @param dbGiveLike 点赞
     * @return 点赞集合
     */
    public List<DbGiveLike> selectDbGiveLikeList(DbGiveLike dbGiveLike);

    /**
     * 新增点赞
     * 
     * @param dbGiveLike 点赞
     * @return 结果
     */
    public int insertDbGiveLike(DbGiveLike dbGiveLike);

    /**
     * 修改点赞
     * 
     * @param dbGiveLike 点赞
     * @return 结果
     */
    public int updateDbGiveLike(DbGiveLike dbGiveLike);

    /**
     * 删除点赞
     * 
     * @param id 点赞ID
     * @return 结果
     */
    public int deleteDbGiveLikeById(Long id);

    /**
     * 批量删除点赞
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbGiveLikeByIds(String[] ids);
}
