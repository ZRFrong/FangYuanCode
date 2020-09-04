package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbCommentMapper;
import com.ruoyi.fangyuanapi.domain.DbComment;
import com.ruoyi.fangyuanapi.service.IDbCommentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 评论Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbCommentServiceImpl implements IDbCommentService 
{
    @Autowired
    private DbCommentMapper dbCommentMapper;

    /**
     * 查询评论
     * 
     * @param id 评论ID
     * @return 评论
     */
    @Override
    public DbComment selectDbCommentById(Long id)
    {
        return dbCommentMapper.selectDbCommentById(id);
    }

    /**
     * 查询评论列表
     * 
     * @param dbComment 评论
     * @return 评论
     */
    @Override
    public List<DbComment> selectDbCommentList(DbComment dbComment)
    {
        return dbCommentMapper.selectDbCommentList(dbComment);
    }

    /**
     * 新增评论
     * 
     * @param dbComment 评论
     * @return 结果
     */
    @Override
    public int insertDbComment(DbComment dbComment)
    {
        return dbCommentMapper.insertDbComment(dbComment);
    }

    /**
     * 修改评论
     * 
     * @param dbComment 评论
     * @return 结果
     */
    @Override
    public int updateDbComment(DbComment dbComment)
    {
        return dbCommentMapper.updateDbComment(dbComment);
    }

    /**
     * 删除评论对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbCommentByIds(String ids)
    {
        return dbCommentMapper.deleteDbCommentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除评论信息
     * 
     * @param id 评论ID
     * @return 结果
     */
    public int deleteDbCommentById(Long id)
    {
        return dbCommentMapper.deleteDbCommentById(id);
    }
}
