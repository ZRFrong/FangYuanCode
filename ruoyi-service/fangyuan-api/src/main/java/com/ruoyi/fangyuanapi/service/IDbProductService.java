package com.ruoyi.fangyuanapi.service;

import com.ruoyi.fangyuanapi.domain.DbProduct;
import java.util.List;

/**
 * 产品Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbProductService 
{
    /**
     * 查询产品
     * 
     * @param productId 产品ID
     * @return 产品
     */
    public DbProduct selectDbProductById(Long productId);

    /**
     * 查询产品列表
     * 
     * @param dbProduct 产品
     * @return 产品集合
     */
    public List<DbProduct> selectDbProductList(DbProduct dbProduct);

    /**
     * 新增产品
     * 
     * @param dbProduct 产品
     * @return 结果
     */
    public int insertDbProduct(DbProduct dbProduct);

    /**
     * 修改产品
     * 
     * @param dbProduct 产品
     * @return 结果
     */
    public int updateDbProduct(DbProduct dbProduct);

    /**
     * 批量删除产品
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbProductByIds(String ids);

    /**
     * 删除产品信息
     * 
     * @param productId 产品ID
     * @return 结果
     */
    public int deleteDbProductById(Long productId);
}
