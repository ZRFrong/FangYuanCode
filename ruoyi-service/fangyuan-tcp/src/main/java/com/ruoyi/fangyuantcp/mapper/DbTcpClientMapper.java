package com.ruoyi.fangyuantcp.mapper;

import com.ruoyi.system.domain.DbTcpClient;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * tcp在线设备Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbTcpClientMapper 
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
     * 删除tcp在线设备
     * 
     * @param tcpClientId tcp在线设备ID
     * @return 结果
     */
    public int deleteDbTcpClientById(Long tcpClientId);

    /**
     * 批量删除tcp在线设备
     * @param tcpClientIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbTcpClientByIds(String[] tcpClientIds);

    @Update("UPDATE db_tcp_client set is_online='1' WHERE heart_name=#{heartbeatName}")
    void updateByHeartbeatName(String heartbeatName);

    @Delete("delete from db_tcp_client where  heart_name=#{heartbeatName}")
    void deleteDbtcpHeartbeatName(String heartbeatName);

    @Select("SELECT heart_name from db_tcp_client h where heart_name  like #{heartBeat}")
    List<String> heartBeatDFuzzy(String heartBeat);

    /**
     * 按照心跳查询是否在线
     * @since: 2.0.0
     * @param heartName 心跳名
     * @return: java.lang.Integer
     * @author: ZHAOXIAOSI
     * @date: 2021/4/28 11:26
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    Integer queryOne(String heartName);

}
