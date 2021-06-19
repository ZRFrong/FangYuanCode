package com.ruoyi.fangyuantcp.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.aspect.FeedbackIntercept;
import com.ruoyi.system.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 设备状态Service接口
 *
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
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


    public DbTcpType updateDbTcpTypeFeedback(DbTcpType dbTcpType);

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
    void curingTypeTiming(DbTcpType dbTcpType);


    void timingType() throws ExecutionException, InterruptedException;

    void timingTongFengHand() throws ExecutionException, InterruptedException;

    void timingTongFengType() throws ExecutionException, InterruptedException;





    List<DbStateRecords> intervalState(Date startTime, Date endTime, String iNterval,String hearName);

    void timingTypeOnly(DbTcpClient dbTcpClient);

    void deleteByHeartName(String heartbeatText);

    void deleteTimingType();

    void updateByHeartbeat(String heartbeatText);

    void updateByHeartbeatOpen(String heartName);

    R stateAllQuery(List<DbOperationVo> dbOperationVo);



    R querySync(List<DbOperationVo> dbOperationVoList);

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

    R getStateCurveData(String heartName, Integer strips, Integer curveType);
}
