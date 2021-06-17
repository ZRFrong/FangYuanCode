package com.ruoyi.fangyuantcp.mapper;

import com.ruoyi.system.domain.DbTcpType;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 设备状态Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbTcpTypeMapper 
{
    /**
     * 查询设备状态
     * 
     * @param tcpTypeId 设备状态ID
     * @return 设备状态
     */
    public DbTcpType selectDbTcpTypeById(Long tcpTypeId);

    /**
     * 查询设备状态列表
     * 
     * @param dbTcpType 设备状态
     * @return 设备状态集合
     */
    public List<DbTcpType> selectDbTcpTypeList(DbTcpType dbTcpType);

    /**
     * 新增设备状态
     * 
     * @param dbTcpType 设备状态
     * @return 结果
     */
    public int insertDbTcpType(DbTcpType dbTcpType);

    /**
     * 修改设备状态
     * 
     * @param dbTcpType 设备状态
     * @return 结果
     */

    public int updateDbTcpType(DbTcpType dbTcpType);

    /**
     * 删除设备状态
     * 
     * @param tcpTypeId 设备状态ID
     * @return 结果
     */
    public int deleteDbTcpTypeById(Long tcpTypeId);

    /**
     * 批量删除设备状态
     * 
     * @param tcpTypeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbTcpTypeByIds(String[] tcpTypeIds);

    void deleteByHeartName(String heartbeatText);


    @Update("UPDATE  db_tcp_type set is_show =1 where heart_name like #{heartbeatText}  ")
    void updateByHeartbeat(String heartbeatText);

    @Update("UPDATE  db_tcp_type set is_show =0 where heart_name like #{heartbeatText}  ")
    void updateByHeartbeatOpen(String heartName);

    /**
     * 根据设备主动上传的数据修改大棚环境传感器数据值
     * @since: 2.0.0
     * @param dbTcpType
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/5/20 16:52
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int updateDbTcpTypeSensorData(DbTcpType dbTcpType);

    DbTcpType selectDbTcpTypeByHeartName(String heartbeatText);

    /**
     * 批量删除
     * @since: 2.0.0
     * @param list
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/6/9 14:42
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int batchDeleteDbTcpTypeById(List<Long> list);

}
