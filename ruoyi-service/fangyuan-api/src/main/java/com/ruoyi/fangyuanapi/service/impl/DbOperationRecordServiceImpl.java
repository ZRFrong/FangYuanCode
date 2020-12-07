package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;

import com.ruoyi.system.domain.DbOperationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbOperationRecordMapper;
import com.ruoyi.fangyuanapi.service.IDbOperationRecordService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户操作记录Service业务层处理
 * 
 * @author zheng
 * @date 2020-10-16
 */
@Service
public class DbOperationRecordServiceImpl implements IDbOperationRecordService 
{
    @Autowired
    private DbOperationRecordMapper dbOperationRecordMapper;

    /**
     * 查询用户操作记录
     * 
     * @param id 用户操作记录ID
     * @return 用户操作记录
     */
    @Override
    public DbOperationRecord selectDbOperationRecordById(Long id)
    {
        return dbOperationRecordMapper.selectDbOperationRecordById(id);
    }

    /**
     * 查询用户操作记录列表
     * 
     * @param dbOperationRecord 用户操作记录
     * @return 用户操作记录
     */
    @Override
    public List<DbOperationRecord> selectDbOperationRecordList(DbOperationRecord dbOperationRecord)
    {
        return dbOperationRecordMapper.selectDbOperationRecordList(dbOperationRecord);
    }

    /**
     * 新增用户操作记录
     * 
     * @param dbOperationRecord 用户操作记录
     * @return 结果
     */
    @Override
    public int insertDbOperationRecord(DbOperationRecord dbOperationRecord)
    {
        return dbOperationRecordMapper.insertDbOperationRecord(dbOperationRecord);
    }

    /**
     * 修改用户操作记录
     * 
     * @param dbOperationRecord 用户操作记录
     * @return 结果
     */
    @Override
    public int updateDbOperationRecord(DbOperationRecord dbOperationRecord)
    {
        return dbOperationRecordMapper.updateDbOperationRecord(dbOperationRecord);
    }

    /**
     * 删除用户操作记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbOperationRecordByIds(String ids)
    {
        return dbOperationRecordMapper.deleteDbOperationRecordByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<DbOperationRecord> listGroupDay(DbOperationRecord dbOperationRecord, Integer pageNum1, Integer pageSize1, Long aLong) {
//        处理id替换为地块或者土地名称

        return dbOperationRecordMapper.listGroupDay(dbOperationRecord.getOperationText(),dbOperationRecord.getOperationTime(),pageNum1,pageSize1,aLong);
    }

    /**
     * 删除用户操作记录信息
     * 
     * @param id 用户操作记录ID
     * @return 结果
     */
    public int deleteDbOperationRecordById(Long id)
    {
        return dbOperationRecordMapper.deleteDbOperationRecordById(id);
    }
}
