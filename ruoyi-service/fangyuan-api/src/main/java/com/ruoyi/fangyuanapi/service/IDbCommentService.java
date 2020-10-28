package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbComment;
import java.util.List;
import java.util.Map;

/**
 * 评论Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbCommentService 
{
    /**
     * 查询评论
     * 
     * @param id 评论ID
     * @return 评论
     */
    public DbComment selectDbCommentById(Long id);

    /**
     * 查询评论列表
     * 
     * @param dbComment 评论
     * @return 评论集合
     */
    public List<DbComment> selectDbCommentList(DbComment dbComment);

    /**
     * 新增评论
     * 
     * @param dbComment 评论
     * @return 结果
     */
    public int insertDbComment(DbComment dbComment);

    /**
     * 修改评论
     * 
     * @param dbComment 评论
     * @return 结果
     */
    public int updateDbComment(DbComment dbComment);

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbCommentByIds(String ids);

    /**
     * 删除评论信息
     * 
     * @param id 评论ID
     * @return 结果
     */
    public int deleteDbCommentById(Long id);

    /**
     * 一级评论查询接口
     * @param dynamicId
     * @param userId
     * @param currPage
     * @return
     */
    List<Map<String,Object>> getCommentList(Long dynamicId, String userId,Integer currPage);

    /**
     * 二级评论查询接口
     * @param commentId
     * @param userId
     * @param currPage
     * @param pageSize
     * @return
     */
    List<Map<String,Object>> getTwoCommentList(Long commentId,String userId,Integer currPage,Integer pageSize);

    /**
     * 新增评论接口
     * @param dynamicId 动态id
     * @param commentId 评论 id
     * @param replyUserId 回复的用户id
     * @param replyCommentId 回复的评论id
     * @param userId 当前用户id
     * @param text 评论的内容
     * @return
     */
    DbComment addComment(Long dynamicId, Long commentId, Long replyUserId,Long replyCommentId, Long userId,String text);
}
