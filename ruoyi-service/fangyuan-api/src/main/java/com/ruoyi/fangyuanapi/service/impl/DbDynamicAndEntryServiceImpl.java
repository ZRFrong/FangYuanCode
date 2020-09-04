package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbDynamicAndEntryMapper;
import com.ruoyi.fangyuanapi.domain.DbDynamicAndEntry;
import com.ruoyi.fangyuanapi.service.IDbDynamicAndEntryService;
import com.ruoyi.common.core.text.Convert;

/**
 * 动态和词条中间Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbDynamicAndEntryServiceImpl implements IDbDynamicAndEntryService 
{
    @Autowired
    private DbDynamicAndEntryMapper dbDynamicAndEntryMapper;

    /**
     * 查询动态和词条中间
     * 
     * @param id 动态和词条中间ID
     * @return 动态和词条中间
     */
    @Override
    public DbDynamicAndEntry selectDbDynamicAndEntryById(Long id)
    {
        return dbDynamicAndEntryMapper.selectDbDynamicAndEntryById(id);
    }

    /**
     * 查询动态和词条中间列表
     * 
     * @param dbDynamicAndEntry 动态和词条中间
     * @return 动态和词条中间
     */
    @Override
    public List<DbDynamicAndEntry> selectDbDynamicAndEntryList(DbDynamicAndEntry dbDynamicAndEntry)
    {
        return dbDynamicAndEntryMapper.selectDbDynamicAndEntryList(dbDynamicAndEntry);
    }

    /**
     * 新增动态和词条中间
     * 
     * @param dbDynamicAndEntry 动态和词条中间
     * @return 结果
     */
    @Override
    public int insertDbDynamicAndEntry(DbDynamicAndEntry dbDynamicAndEntry)
    {
        return dbDynamicAndEntryMapper.insertDbDynamicAndEntry(dbDynamicAndEntry);
    }

    /**
     * 修改动态和词条中间
     * 
     * @param dbDynamicAndEntry 动态和词条中间
     * @return 结果
     */
    @Override
    public int updateDbDynamicAndEntry(DbDynamicAndEntry dbDynamicAndEntry)
    {
        return dbDynamicAndEntryMapper.updateDbDynamicAndEntry(dbDynamicAndEntry);
    }

    /**
     * 删除动态和词条中间对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbDynamicAndEntryByIds(String ids)
    {
        return dbDynamicAndEntryMapper.deleteDbDynamicAndEntryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除动态和词条中间信息
     * 
     * @param id 动态和词条中间ID
     * @return 结果
     */
    public int deleteDbDynamicAndEntryById(Long id)
    {
        return dbDynamicAndEntryMapper.deleteDbDynamicAndEntryById(id);
    }
}
