package com.ruoyi.fangyuantcp.service;

import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbStateRecords;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.domain.DbTcpType;

import java.util.Date;
import java.util.List;

/**
 * 设备状态Service接口
 *
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbTcpTypeService {
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
     * 批量删除设备状态
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbTcpTypeByIds(String ids);

    /**
     * 删除设备状态信息
     *
     * @param tcpTypeId 设备状态ID
     * @return 结果
     */
    public int deleteDbTcpTypeById(Long tcpTypeId);


     /*
     *定时状态存储
     * */
    void curingTypeTiming();

    int updateOrInstart(DbTcpType dbTcpType);

    void timingType();

    int timingTongFengHand();

    int timingTongFengType();



    int operateTongFengHand(String heartbeatText, String equipmentNo, Integer i);

    int operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String temp);

    List<DbStateRecords> intervalState(Date startTime, Date endTime, String iNterval,String hearName);

    void timingTypeOnly(DbTcpClient dbTcpClient);

    void deleteByHeartName(String heartbeatText);

    void deleteTimingType();
}
