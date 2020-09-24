package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbEntryMapper;
import com.ruoyi.system.domain.DbEntry;
import com.ruoyi.fangyuanapi.service.IDbEntryService;
import com.ruoyi.common.core.text.Convert;

/**
 * 词条Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbEntryServiceImpl implements IDbEntryService 
{
    @Autowired
    private DbEntryMapper dbEntryMapper;

    /**
     * 查询词条
     * 
     * @param id 词条ID
     * @return 词条
     */
    @Override
    public DbEntry selectDbEntryById(Long id)
    {
        return dbEntryMapper.selectDbEntryById(id);
    }

    /**
     * 查询词条列表
     * 
     * @param dbEntry 词条
     * @return 词条
     */
    @Override
    public List<DbEntry> selectDbEntryList(DbEntry dbEntry)
    {
        return dbEntryMapper.selectDbEntryList(dbEntry);
    }

    /**
     * 新增词条
     * 
     * @param dbEntry 词条
     * @return 结果
     */
    @Override
    public int insertDbEntry(DbEntry dbEntry)
    {
        return dbEntryMapper.insertDbEntry(dbEntry);
    }

    /**
     * 修改词条
     * 
     * @param dbEntry 词条
     * @return 结果
     */
    @Override
    public int updateDbEntry(DbEntry dbEntry)
    {
        return dbEntryMapper.updateDbEntry(dbEntry);
    }

    /**
     * 删除词条对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbEntryByIds(String ids)
    {
        return dbEntryMapper.deleteDbEntryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除词条信息
     * 
     * @param id 词条ID
     * @return 结果
     */
    public int deleteDbEntryById(Long id)
    {
        return dbEntryMapper.deleteDbEntryById(id);
    }
}
