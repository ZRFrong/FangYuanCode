package com.ruoyi.fangyuantcp.mapper;

import com.ruoyi.system.domain.DbStateRecords;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 状态记录Mapper接口
 * 
 * @author 正
 * @date 2020-09-23
 */
public interface DbStateRecordsMapper2
{
    /**
     * 查询状态记录
     *
     * @param stateRecordsId 状态记录ID
     * @return 状态记录
     */
    public DbStateRecords selectDbStateRecordsById(Long stateRecordsId);

    /**
     * 查询状态记录列表
     *
     * @param dbStateRecords 状态记录
     * @return 状态记录集合
     */
    public List<DbStateRecords> selectDbStateRecordsList(DbStateRecords dbStateRecords);

    /**
     * 新增状态记录
     *
     * @param dbStateRecords 状态记录
     * @return 结果
     */
    public int insertDbStateRecords(DbStateRecords dbStateRecords);

    /**
     * 修改状态记录
     *
     * @param dbStateRecords 状态记录
     * @return 结果
     */
    public int updateDbStateRecords(DbStateRecords dbStateRecords);

    /**
     * 删除状态记录
     *
     * @param stateRecordsId 状态记录ID
     * @return 结果
     */
    public int deleteDbStateRecordsById(Long stateRecordsId);

    /**
     * 批量删除状态记录
     *
     * @param stateRecordsIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbStateRecordsByIds(String[] stateRecordsIds);

    /*指定时间段内的数据*/

    List<DbStateRecords> intervalState(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("hearName") String hearName);
}
