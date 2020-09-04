package com.ruoyi.fangyuantcp.service.impl;

import java.util.List;

import com.ruoyi.common.redis.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.common.core.text.Convert;

/**
 * tcp在线设备Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbTcpClientServiceImpl implements IDbTcpClientService 
{
    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;

    /**
     * 查询tcp在线设备
     * 
     * @param tcpClientId tcp在线设备ID
     * @return tcp在线设备
     */
    @Override
    public DbTcpClient selectDbTcpClientById(Long tcpClientId)
    {
        return dbTcpClientMapper.selectDbTcpClientById(tcpClientId);
    }

    /**
     * 查询tcp在线设备列表
     * 
     * @param dbTcpClient tcp在线设备
     * @return tcp在线设备
     */
    @Override
    public List<DbTcpClient> selectDbTcpClientList(DbTcpClient dbTcpClient)
    {
        return dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
    }

    /**
     * 新增tcp在线设备
     * 
     * @param dbTcpClient tcp在线设备
     * @return 结果
     */
    @Override
    public int insertDbTcpClient(DbTcpClient dbTcpClient)
    {
        return dbTcpClientMapper.insertDbTcpClient(dbTcpClient);
    }

    /**
     * 修改tcp在线设备
     * 
     * @param dbTcpClient tcp在线设备
     * @return 结果
     */
    @Override
    public int updateDbTcpClient(DbTcpClient dbTcpClient)
    {
        return dbTcpClientMapper.updateDbTcpClient(dbTcpClient);
    }

    /**
     * 删除tcp在线设备对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbTcpClientByIds(String ids)
    {
        return dbTcpClientMapper.deleteDbTcpClientByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除tcp在线设备信息
     * 
     * @param tcpClientId tcp在线设备ID
     * @return 结果
     */
    public int deleteDbTcpClientById(Long tcpClientId)
    {
        return dbTcpClientMapper.deleteDbTcpClientById(tcpClientId);
    }
}
