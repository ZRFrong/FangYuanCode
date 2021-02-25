package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbOperationRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户操作记录Mapper接口
 * 
 * @author zheng
 * @date 2020-10-16
 */
public interface DbOperationRecordMapper1
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
     * 删除用户操作记录
     *
     * @param id 用户操作记录ID
     * @return 结果
     */
    public int deleteDbOperationRecordById(Long id);

    /**
     * 批量删除用户操作记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbOperationRecordByIds(String[] ids);


    List<DbOperationRecord> listGroupDay(@Param("operationText") String operationText, @Param("operationTime") String operationTime, @Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize, @Param("dbUserId") Long dbUserId);
}
