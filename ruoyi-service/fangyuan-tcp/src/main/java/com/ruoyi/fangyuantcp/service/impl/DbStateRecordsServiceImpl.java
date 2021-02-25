package com.ruoyi.fangyuantcp.service.impl;

import java.util.List;

import com.ruoyi.system.domain.DbStateRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbStateRecordsMapper;
import com.ruoyi.fangyuantcp.service.IDbStateRecordsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 状态记录Service业务层处理
 * 
 * @author 正
 * @date 2020-09-23
 */
@Service
public class DbStateRecordsServiceImpl implements IDbStateRecordsService 
{
    @Autowired
    private DbStateRecordsMapper dbStateRecordsMapper;

    /**
     * 查询状态记录
     * 
     * @param stateRecordsId 状态记录ID
     * @return 状态记录
     */
    @Override
    public DbStateRecords selectDbStateRecordsById(Long stateRecordsId)
    {
        return dbStateRecordsMapper.selectDbStateRecordsById(stateRecordsId);
    }

    /**
     * 查询状态记录列表
     * 
     * @param dbStateRecords 状态记录
     * @return 状态记录
     */
    @Override
    public List<DbStateRecords> selectDbStateRecordsList(DbStateRecords dbStateRecords)
    {
        return dbStateRecordsMapper.selectDbStateRecordsList(dbStateRecords);
    }

    /**
     * 新增状态记录
     * 
     * @param dbStateRecords 状态记录
     * @return 结果
     */
    @Override
    public int insertDbStateRecords(DbStateRecords dbStateRecords)
    {

        return dbStateRecordsMapper.insertDbStateRecords(dbStateRecords);
    }

    /**
     * 修改状态记录
     * 
     * @param dbStateRecords 状态记录
     * @return 结果
     */
    @Override
    public int updateDbStateRecords(DbStateRecords dbStateRecords)
    {
        return dbStateRecordsMapper.updateDbStateRecords(dbStateRecords);
    }

    /**
     * 删除状态记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbStateRecordsByIds(String ids)
    {
        return dbStateRecordsMapper.deleteDbStateRecordsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除状态记录信息
     * 
     * @param stateRecordsId 状态记录ID
     * @return 结果
     */
    public int deleteDbStateRecordsById(Long stateRecordsId)
    {
        return dbStateRecordsMapper.deleteDbStateRecordsById(stateRecordsId);
    }
}
