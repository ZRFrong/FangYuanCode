package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbOrder;

import java.util.List;

/**
 * db_orderService接口
 * 
 * @author zheng
 * @date 2020-09-30
 */
public interface IDbOrderService 
{
    /**
     * 查询db_order
     * 
     * @param id db_orderID
     * @return db_order
     */
    public DbOrder selectDbOrderById(Long id);

    /**
     * 查询db_order列表
     * 
     * @param dbOrder db_order
     * @return db_order集合
     */
    public List<DbOrder> selectDbOrderList(DbOrder dbOrder);

    /**
     * 新增db_order
     * 
     * @param dbOrder db_order
     * @return 结果
     */
    public int insertDbOrder(DbOrder dbOrder);

    /**
     * 修改db_order
     * 
     * @param dbOrder db_order
     * @return 结果
     */
    public int updateDbOrder(DbOrder dbOrder);

    /**
     * 批量删除db_order
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbOrderByIds(String ids);

    /**
     * 删除db_order信息
     * 
     * @param id db_orderID
     * @return 结果
     */
    public int deleteDbOrderById(Long id);
}
