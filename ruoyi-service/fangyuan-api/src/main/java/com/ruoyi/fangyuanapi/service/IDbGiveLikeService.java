package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbGiveLike;
import java.util.List;

/**
 * 点赞Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbGiveLikeService 
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
     * 批量删除点赞
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbGiveLikeByIds(String ids);

    /**
     * 删除点赞信息
     * 
     * @param id 点赞ID
     * @return 结果
     */
    public int deleteDbGiveLikeById(Long id);

    /**
     * 我的页面我的赞
     * @param userId
     * @return 点赞数量
     */
    Integer selectUserGiveLikeNum(String userId);

    /**
     * 用户给动态点赞接口
     * @param aLong
     * @param dynamicId
     * @return
     */
    boolean insertDbGiveLikeAndLikeNum(Long aLong, Long dynamicId);

    /**
     * 根据用户id 和动态id查询
     * @param header
     * @param id
     * @return
     */
    DbGiveLike selectDbGiveLikeByUserIdAndDynamicId(String header, Long id);
}
