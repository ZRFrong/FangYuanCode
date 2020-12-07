package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbOperationRecord;

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


    List<DbOperationRecord> listGroupDay(DbOperationRecord dbOperationRecord, Integer pageNum, Integer pageSize,Long userId);
}
