package com.ruoyi.fangyuantcp.mapper;

import com.ruoyi.system.domain.DbTcpOrder;
import java.util.List;

/**
 * 操作记录Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbTcpOrderMapper 
{
    /**
     * 查询操作记录
     * 
     * @param tcpOrderId 操作记录ID
     * @return 操作记录
     */
    public DbTcpOrder selectDbTcpOrderById(Long tcpOrderId);

    /**
     * 查询操作记录列表
     * 
     * @param dbTcpOrder 操作记录
     * @return 操作记录集合
     */
    public List<DbTcpOrder> selectDbTcpOrderList(DbTcpOrder dbTcpOrder);

    /**
     * 新增操作记录
     * 
     * @param dbTcpOrder 操作记录
     * @return 结果
     */
    public int insertDbTcpOrder(DbTcpOrder dbTcpOrder);

    /**
     * 修改操作记录
     * 
     * @param dbTcpOrder 操作记录
     * @return 结果
     */
    public int updateDbTcpOrder(DbTcpOrder dbTcpOrder);

    /**
     * 删除操作记录
     * 
     * @param tcpOrderId 操作记录ID
     * @return 结果
     */
    public int deleteDbTcpOrderById(Long tcpOrderId);

    /**
     * 批量删除操作记录
     * 
     * @param tcpOrderIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbTcpOrderByIds(String[] tcpOrderIds);
}
