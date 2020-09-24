package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbManualReview;

import java.util.List;

/**
 * 审核表Service接口
 * 
 * @author ruoyi
 * @date 2020-09-22
 */
public interface IDbManualReviewService 
{
    /**
     * 查询审核表
     * 
     * @param id 审核表ID
     * @return 审核表
     */
    public DbManualReview selectDbManualReviewById(Long id);

    /**
     * 查询审核表列表
     * 
     * @param dbManualReview 审核表
     * @return 审核表集合
     */
    public List<DbManualReview> selectDbManualReviewList(DbManualReview dbManualReview);

    /**
     * 新增审核表
     * 
     * @param dbManualReview 审核表
     * @return 结果
     */
    public int insertDbManualReview(DbManualReview dbManualReview);

    /**
     * 修改审核表
     * 
     * @param dbManualReview 审核表
     * @return 结果
     */
    public int updateDbManualReview(DbManualReview dbManualReview);

    /**
     * 批量删除审核表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbManualReviewByIds(String ids);

    /**
     * 删除审核表信息
     * 
     * @param id 审核表ID
     * @return 结果
     */
    public int deleteDbManualReviewById(Long id);
}
