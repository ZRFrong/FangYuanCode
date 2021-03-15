package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbUserRecordMapper;
import com.ruoyi.system.domain.DbUserRecord;
import com.ruoyi.fangyuanapi.service.IDbUserRecordService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zheng
 * @date 2021-03-04
 */
@Service
public class DbUserRecordServiceImpl implements IDbUserRecordService 
{
    @Autowired
    private DbUserRecordMapper dbUserRecordMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public DbUserRecord selectDbUserRecordById(Long id)
    {
        return dbUserRecordMapper.selectDbUserRecordById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbUserRecord 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DbUserRecord> selectDbUserRecordList(DbUserRecord dbUserRecord)
    {
        return dbUserRecordMapper.selectDbUserRecordList(dbUserRecord);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbUserRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDbUserRecord(DbUserRecord dbUserRecord)
    {
        dbUserRecord.setCreateTime(DateUtils.getNowDate());
        return dbUserRecordMapper.insertDbUserRecord(dbUserRecord);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbUserRecord 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDbUserRecord(DbUserRecord dbUserRecord)
    {
        dbUserRecord.setUpdateTime(DateUtils.getNowDate());
        return dbUserRecordMapper.updateDbUserRecord(dbUserRecord);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbUserRecordByIds(String ids)
    {
        return dbUserRecordMapper.deleteDbUserRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbUserRecordById(Long id)
    {
        return dbUserRecordMapper.deleteDbUserRecordById(id);
    }
}
