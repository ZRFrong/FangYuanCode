package com.ruoyi.fangyuantcp.service.impl;

import java.util.List;
import java.util.Set;

import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpOrderMapper;
import com.ruoyi.system.domain.DbTcpOrder;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;
import com.ruoyi.common.core.text.Convert;

/**
 * 操作记录Service业务层处理
 *
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbTcpOrderServiceImpl implements IDbTcpOrderService {
    @Autowired
    private DbTcpOrderMapper dbTcpOrderMapper;


    @Autowired
    private RedisUtils redisUtils;



    /**
     * 查询操作记录
     *
     * @param tcpOrderId 操作记录ID
     * @return 操作记录
     */
    @Override
    public DbTcpOrder selectDbTcpOrderById(Long tcpOrderId) {
        return dbTcpOrderMapper.selectDbTcpOrderById(tcpOrderId);
    }

    /**
     * 查询操作记录列表
     *
     * @param dbTcpOrder 操作记录
     * @return 操作记录
     */
    @Override
    public List<DbTcpOrder> selectDbTcpOrderList(DbTcpOrder dbTcpOrder) {
        return dbTcpOrderMapper.selectDbTcpOrderList(dbTcpOrder);
    }

    /**
     * 新增操作记录
     *
     * @param dbTcpOrder 操作记录
     * @return 结果
     */
    @Override
    public int insertDbTcpOrder(DbTcpOrder dbTcpOrder) {
        dbTcpOrder.setCreateTime(DateUtils.getNowDate());
        return dbTcpOrderMapper.insertDbTcpOrder(dbTcpOrder);
    }

    /**
     * 修改操作记录
     *
     * @param dbTcpOrder 操作记录
     * @return 结果
     */
    @Override
    public int updateDbTcpOrder(DbTcpOrder dbTcpOrder) {
        dbTcpOrder.setUpdateTime(DateUtils.getNowDate());
        return dbTcpOrderMapper.updateDbTcpOrder(dbTcpOrder);
    }

    /**
     * 删除操作记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbTcpOrderByIds(String ids) {
        return dbTcpOrderMapper.deleteDbTcpOrderByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除操作记录信息
     *
     * @param tcpOrderId 操作记录ID
     * @return 结果
     */
    public int deleteDbTcpOrderById(Long tcpOrderId) {
        return dbTcpOrderMapper.deleteDbTcpOrderById(tcpOrderId);
    }


    /*
     *定时调用  redis中的数据固化
     * */
    @Override
    public void curingTiming() {
        Set<String> keys = redisUtils.keys(RedisKeyConf.HANDLE.toString());
        for (String key : keys) {
            int i = insertDbTcpOrder(redisUtils.get(key, DbTcpOrder.class));
            if (i>0){
                redisUtils.delete(key);
            }
        }
    }




}
