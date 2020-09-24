package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;

import com.ruoyi.system.domain.DbManualReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbManualReviewMapper;
import com.ruoyi.fangyuanapi.service.IDbManualReviewService;
import com.ruoyi.common.core.text.Convert;

/**
 * 审核表Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-22
 */
@Service
public class DbManualReviewServiceImpl implements IDbManualReviewService 
{
    @Autowired
    private DbManualReviewMapper dbManualReviewMapper;

    /**
     * 查询审核表
     * 
     * @param id 审核表ID
     * @return 审核表
     */
    @Override
    public DbManualReview selectDbManualReviewById(Long id)
    {
        return dbManualReviewMapper.selectDbManualReviewById(id);
    }

    /**
     * 查询审核表列表
     * 
     * @param dbManualReview 审核表
     * @return 审核表
     */
    @Override
    public List<DbManualReview> selectDbManualReviewList(DbManualReview dbManualReview)
    {
        return dbManualReviewMapper.selectDbManualReviewList(dbManualReview);
    }

    /**
     * 新增审核表
     * 
     * @param dbManualReview 审核表
     * @return 结果
     */
    @Override
    public int insertDbManualReview(DbManualReview dbManualReview)
    {
        return dbManualReviewMapper.insertDbManualReview(dbManualReview);
    }

    /**
     * 修改审核表
     * 
     * @param dbManualReview 审核表
     * @return 结果
     */
    @Override
    public int updateDbManualReview(DbManualReview dbManualReview)
    {
        return dbManualReviewMapper.updateDbManualReview(dbManualReview);
    }

    /**
     * 删除审核表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbManualReviewByIds(String ids)
    {
        return dbManualReviewMapper.deleteDbManualReviewByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除审核表信息
     * 
     * @param id 审核表ID
     * @return 结果
     */
    public int deleteDbManualReviewById(Long id)
    {
        return dbManualReviewMapper.deleteDbManualReviewById(id);
    }
}
