package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbProductBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbProductBatchMapper;
import com.ruoyi.fangyuanapi.service.IDbProductBatchService;
import com.ruoyi.common.core.text.Convert;

/**
 * 产品批次Service业务层处理
 * 
 * @author zheng
 * @date 2021-04-08
 */
@Service
public class DbProductBatchServiceImpl implements IDbProductBatchService 
{
    @Autowired
    private DbProductBatchMapper dbProductBatchMapper;

    /**
     * 查询产品批次
     * 
     * @param id 产品批次ID
     * @return 产品批次
     */
    @Override
    public DbProductBatch selectDbProductBatchById(Long id)
    {
        return dbProductBatchMapper.selectDbProductBatchById(id);
    }

    /**
     * 查询产品批次列表
     * 
     * @param dbProductBatch 产品批次
     * @return 产品批次
     */
    @Override
    public List<DbProductBatch> selectDbProductBatchList(DbProductBatch dbProductBatch)
    {
        return dbProductBatchMapper.selectDbProductBatchList(dbProductBatch);
    }

    /**
     * 新增产品批次
     * 
     * @param dbProductBatch 产品批次
     * @return 结果
     */
    @Override
    public int insertDbProductBatch(DbProductBatch dbProductBatch)
    {
        dbProductBatch.setCreateTime(DateUtils.getNowDate());
        return dbProductBatchMapper.insertDbProductBatch(dbProductBatch);
    }

    /**
     * 修改产品批次
     * 
     * @param dbProductBatch 产品批次
     * @return 结果
     */
    @Override
    public int updateDbProductBatch(DbProductBatch dbProductBatch)
    {
        dbProductBatch.setUpdateTime(DateUtils.getNowDate());
        return dbProductBatchMapper.updateDbProductBatch(dbProductBatch);
    }

    /**
     * 删除产品批次对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbProductBatchByIds(String ids)
    {
        return dbProductBatchMapper.deleteDbProductBatchByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品批次信息
     * 
     * @param id 产品批次ID
     * @return 结果
     */
    public int deleteDbProductBatchById(Long id)
    {
        return dbProductBatchMapper.deleteDbProductBatchById(id);
    }
}
