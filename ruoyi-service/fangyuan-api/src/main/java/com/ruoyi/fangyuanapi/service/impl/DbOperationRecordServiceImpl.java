package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;

import com.ruoyi.fangyuanapi.mapper.DbOperationRecordMapper1;
import com.ruoyi.fangyuanapi.mapper.DbOperationRecordMapper2;
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
public class DbOperationRecordServiceImpl implements IDbOperationRecordService {
    @Autowired
    private DbOperationRecordMapper dbOperationRecordMapper;
    @Autowired
    private DbOperationRecordMapper1 dbOperationRecordMapper1;
    @Autowired
    private DbOperationRecordMapper2 dbOperationRecordMapper2;

    /**
     * 查询用户操作记录
     *
     * @param id 用户操作记录ID
     * @return 用户操作记录
     */
    @Override
    public DbOperationRecord selectDbOperationRecordById(Long id) {
        return dbOperationRecordMapper.selectDbOperationRecordById(id);
    }

    /**
     * 查询用户操作记录列表
     *
     * @param dbOperationRecord 用户操作记录
     * @return 用户操作记录
     */
    @Override
    public List<DbOperationRecord> selectDbOperationRecordList(DbOperationRecord dbOperationRecord) {
        if (dbOperationRecord.getDbUserId() % 3 == 0) {
            return dbOperationRecordMapper.selectDbOperationRecordList(dbOperationRecord);
        } else if (dbOperationRecord.getDbUserId() % 3 == 1) {
            return dbOperationRecordMapper1.selectDbOperationRecordList(dbOperationRecord);
        } else if (dbOperationRecord.getDbUserId() % 3 == 2) {
            return dbOperationRecordMapper2.selectDbOperationRecordList(dbOperationRecord);
        }
        return null;
    }

    /**
     * 新增用户操作记录
     *
     * @param dbOperationRecord 用户操作记录
     * @return 结果
     */
    @Override
    public int insertDbOperationRecord(DbOperationRecord dbOperationRecord) {
        if (dbOperationRecord.getDbUserId() % 3 == 0) {
            return dbOperationRecordMapper.insertDbOperationRecord(dbOperationRecord);
        } else if (dbOperationRecord.getDbUserId() % 3 == 1) {
            return dbOperationRecordMapper1.insertDbOperationRecord(dbOperationRecord);
        } else if (dbOperationRecord.getDbUserId() % 3 == 2) {
            return dbOperationRecordMapper2.insertDbOperationRecord(dbOperationRecord);
        }
        return 0;
    }

    /**
     * 修改用户操作记录
     *
     * @param dbOperationRecord 用户操作记录
     * @return 结果
     */
    @Override
    public int updateDbOperationRecord(DbOperationRecord dbOperationRecord) {
        if (dbOperationRecord.getDbUserId() % 3 == 0) {
            return dbOperationRecordMapper.updateDbOperationRecord(dbOperationRecord);
        } else if (dbOperationRecord.getDbUserId() % 3 == 1) {
            return dbOperationRecordMapper1.updateDbOperationRecord(dbOperationRecord);
        } else if (dbOperationRecord.getDbUserId() % 3 == 2) {
            return dbOperationRecordMapper2.updateDbOperationRecord(dbOperationRecord);
        }
        return 0;
    }

    /**
     * 删除用户操作记录对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbOperationRecordByIds(String ids) {
        return dbOperationRecordMapper.deleteDbOperationRecordByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<DbOperationRecord> listGroupDay(String operationText, String operationTime, Integer currPage, Integer pageSize, Long dbUserId) {
//        处理id替换为地块或者土地名称

        return dbOperationRecordMapper.listGroupDay(operationText, operationTime, currPage, pageSize, dbUserId);
    }

    /**
     * 删除用户操作记录信息
     *
     * @param id 用户操作记录ID
     * @return 结果
     */
    public int deleteDbOperationRecordById(Long id) {
        return dbOperationRecordMapper.deleteDbOperationRecordById(id);
    }
}
