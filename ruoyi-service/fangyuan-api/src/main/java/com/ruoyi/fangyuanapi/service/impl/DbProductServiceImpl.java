package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbProductMapper;
import com.ruoyi.fangyuanapi.domain.DbProduct;
import com.ruoyi.fangyuanapi.service.IDbProductService;
import com.ruoyi.common.core.text.Convert;

/**
 * 产品Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbProductServiceImpl implements IDbProductService 
{
    @Autowired
    private DbProductMapper dbProductMapper;

    /**
     * 查询产品
     * 
     * @param productId 产品ID
     * @return 产品
     */
    @Override
    public DbProduct selectDbProductById(Long productId)
    {
        return dbProductMapper.selectDbProductById(productId);
    }

    /**
     * 查询产品列表
     * 
     * @param dbProduct 产品
     * @return 产品
     */
    @Override
    public List<DbProduct> selectDbProductList(DbProduct dbProduct)
    {
        return dbProductMapper.selectDbProductList(dbProduct);
    }

    /**
     * 新增产品
     * 
     * @param dbProduct 产品
     * @return 结果
     */
    @Override
    public int insertDbProduct(DbProduct dbProduct)
    {
        dbProduct.setCreateTime(DateUtils.getNowDate());
        return dbProductMapper.insertDbProduct(dbProduct);
    }

    /**
     * 修改产品
     * 
     * @param dbProduct 产品
     * @return 结果
     */
    @Override
    public int updateDbProduct(DbProduct dbProduct)
    {
        dbProduct.setUpdateTime(DateUtils.getNowDate());
        return dbProductMapper.updateDbProduct(dbProduct);
    }

    /**
     * 删除产品对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbProductByIds(String ids)
    {
        return dbProductMapper.deleteDbProductByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品信息
     * 
     * @param productId 产品ID
     * @return 结果
     */
    public int deleteDbProductById(Long productId)
    {
        return dbProductMapper.deleteDbProductById(productId);
    }
}
