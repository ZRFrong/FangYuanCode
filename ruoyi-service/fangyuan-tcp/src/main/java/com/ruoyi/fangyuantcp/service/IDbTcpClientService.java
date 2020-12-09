package com.ruoyi.fangyuantcp.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpClient;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * tcp在线设备Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbTcpClientService 
{
    /**
     * 查询tcp在线设备
     * 
     * @param tcpClientId tcp在线设备ID
     * @return tcp在线设备
     */
    public DbTcpClient selectDbTcpClientById(Long tcpClientId);

    /**
     * 查询tcp在线设备列表
     * 
     * @param dbTcpClient tcp在线设备
     * @return tcp在线设备集合
     */
    public List<DbTcpClient> selectDbTcpClientList(DbTcpClient dbTcpClient);

    /**
     * 新增tcp在线设备
     * 
     * @param dbTcpClient tcp在线设备
     * @return 结果
     */
    public int insertDbTcpClient(DbTcpClient dbTcpClient);

    /**
     * 修改tcp在线设备
     * 
     * @param dbTcpClient tcp在线设备
     * @return 结果
     */
    public int updateDbTcpClient(DbTcpClient dbTcpClient);

    /**
     * 批量删除tcp在线设备
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbTcpClientByIds(String ids);

    /**
     * 删除tcp在线设备信息
     * 
     * @param tcpClientId tcp在线设备ID
     * @return 结果
     */
    public int deleteDbTcpClientById(Long tcpClientId);

    int heartbeatChoose(DbTcpClient dbTcpClient);

    int operation(DbOperationVo dbTcpClient);


    int query(DbOperationVo dbTcpClient);

    void updateByHeartbeatName(String heartbeatName);

    R operationList(List<DbOperationVo> dbOperationVo) throws ExecutionException, InterruptedException;

    int sinceOrHand();

    void deleteDbtcpHeartbeatName(String heartbeatName);
}
