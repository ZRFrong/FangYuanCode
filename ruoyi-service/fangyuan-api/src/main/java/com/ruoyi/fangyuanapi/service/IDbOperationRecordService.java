package com.ruoyi.fangyuanapi.service;

import com.ruoyi.fangyuanapi.dto.OperationDto;
import com.ruoyi.system.domain.DbOperationRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户操作记录Service接口
 * 
 * @author zheng
 * @date 2020-10-16
 */
public interface IDbOperationRecordService 
{
    /**
     * 查询用户操作记录
     * 
     * @param id 用户操作记录ID
     * @return 用户操作记录
     */
    public DbOperationRecord selectDbOperationRecordById(Long id);

    /**
     * 查询用户操作记录列表
     * 
     * @param dbOperationRecord 用户操作记录
     * @return 用户操作记录集合
     */
    public List<DbOperationRecord> selectDbOperationRecordList(DbOperationRecord dbOperationRecord);

    /**
     * 新增用户操作记录
     * 
     * @param dbOperationRecord 用户操作记录
     * @return 结果
     */
    public int insertDbOperationRecord(DbOperationRecord dbOperationRecord);

    /**
     * 修改用户操作记录
     * 
     * @param dbOperationRecord 用户操作记录
     * @return 结果
     */
    public int updateDbOperationRecord(DbOperationRecord dbOperationRecord);

    /**
     * 批量删除用户操作记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbOperationRecordByIds(String ids);

    /**
     * 删除用户操作记录信息
     * 
     * @param id 用户操作记录ID
     * @return 结果
     */
    public int deleteDbOperationRecordById(Long id);


    List<DbOperationRecord> listGroupDay(String operationText,String operationTime, Integer currPage, Integer pageSize, Long userId);


    /**
     * 根据userId和landId 返回指定条数得记录
     * @since: 2.0.0
     * @param landId
     * @param userId
     * @param currPage
     * @param pageSize
     * @return: java.util.ArrayList<com.ruoyi.fangyuanapi.dto.OperationDto>
     * @author: ZHAOXIAOSI
     * @date: 2021/5/6 21:25
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    ArrayList<OperationDto> selectDbOperationRecordByLandIdAndUserId(Long landId, String userId, Integer currPage, Integer pageSize);

    /**
     * 批量插入操作记录
     * @since: 2.0.0
     * @param records
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/5/6 21:25
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int batchInsert(ArrayList<DbOperationRecord> records);
}
