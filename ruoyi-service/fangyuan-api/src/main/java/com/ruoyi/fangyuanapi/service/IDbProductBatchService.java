package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbProductBatch;

import java.util.List;

/**
 * 产品批次Service接口
 * 
 * @author zheng
 * @date 2021-04-08
 */
public interface IDbProductBatchService 
{
    /**
     * 查询产品批次
     * 
     * @param id 产品批次ID
     * @return 产品批次
     */
    public DbProductBatch selectDbProductBatchById(Long id);

    /**
     * 查询产品批次列表
     * 
     * @param dbProductBatch 产品批次
     * @return 产品批次集合
     */
    public List<DbProductBatch> selectDbProductBatchList(DbProductBatch dbProductBatch);

    /**
     * 新增产品批次
     * 
     * @param dbProductBatch 产品批次
     * @return 结果
     */
    public int insertDbProductBatch(DbProductBatch dbProductBatch);

    /**
     * 修改产品批次
     * 
     * @param dbProductBatch 产品批次
     * @return 结果
     */
    public int updateDbProductBatch(DbProductBatch dbProductBatch);

    /**
     * 批量删除产品批次
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbProductBatchByIds(String ids);

    /**
     * 删除产品批次信息
     * 
     * @param id 产品批次ID
     * @return 结果
     */
    public int deleteDbProductBatchById(Long id);
}
