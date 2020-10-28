package com.ruoyi.fangyuanapi.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.fangyuanapi.mapper.DbOrderMapper;
import com.ruoyi.fangyuanapi.service.IDbOrderService;
import com.ruoyi.system.domain.DbOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * db_orderService业务层处理
 * 
 * @author zheng
 * @date 2020-09-30
 */
@Service
public class DbOrderServiceImpl implements IDbOrderService 
{
    @Autowired
    private DbOrderMapper dbOrderMapper;

    /**
     * 查询db_order
     * 
     * @param id db_orderID
     * @return db_order
     */
    @Override
    public DbOrder selectDbOrderById(Long id)
    {
        return dbOrderMapper.selectDbOrderById(id);
    }

    /**
     * 查询db_order列表
     * 
     * @param dbOrder db_order
     * @return db_order
     */
    @Override
    public List<DbOrder> selectDbOrderList(DbOrder dbOrder)
    {
        return dbOrderMapper.selectDbOrderList(dbOrder);
    }

    /**
     * 新增db_order
     * 
     * @param dbOrder db_order
     * @return 结果
     */
    @Override
    public int insertDbOrder(DbOrder dbOrder)
    {
        dbOrder.setCreateTime(DateUtils.getNowDate());
        return dbOrderMapper.insertDbOrder(dbOrder);
    }

    /**
     * 修改db_order
     * 
     * @param dbOrder db_order
     * @return 结果
     */
    @Override
    public int updateDbOrder(DbOrder dbOrder)
    {
        dbOrder.setUpdateTime(DateUtils.getNowDate());
        return dbOrderMapper.updateDbOrder(dbOrder);
    }

    /**
     * 删除db_order对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbOrderByIds(String ids)
    {
        return dbOrderMapper.deleteDbOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除db_order信息
     * 
     * @param id db_orderID
     * @return 结果
     */
    public int deleteDbOrderById(Long id)
    {
        return dbOrderMapper.deleteDbOrderById(id);
    }
}
