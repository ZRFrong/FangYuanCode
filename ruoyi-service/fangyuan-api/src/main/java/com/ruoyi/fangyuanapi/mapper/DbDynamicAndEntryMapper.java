package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.common.core.dao.BaseMapper;
import com.ruoyi.fangyuanapi.domain.DbDynamicAndEntry;
import java.util.List;

/**
 * 动态和词条中间Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbDynamicAndEntryMapper
{
    /**
     * 查询动态和词条中间
     * 
     * @param id 动态和词条中间ID
     * @return 动态和词条中间
     */
    public DbDynamicAndEntry selectDbDynamicAndEntryById(Long id);

    /**
     * 查询动态和词条中间列表
     * 
     * @param dbDynamicAndEntry 动态和词条中间
     * @return 动态和词条中间集合
     */
    public List<DbDynamicAndEntry> selectDbDynamicAndEntryList(DbDynamicAndEntry dbDynamicAndEntry);

    /**
     * 新增动态和词条中间
     * 
     * @param dbDynamicAndEntry 动态和词条中间
     * @return 结果
     */
    public int insertDbDynamicAndEntry(DbDynamicAndEntry dbDynamicAndEntry);

    /**
     * 修改动态和词条中间
     * 
     * @param dbDynamicAndEntry 动态和词条中间
     * @return 结果
     */
    public int updateDbDynamicAndEntry(DbDynamicAndEntry dbDynamicAndEntry);

    /**
     * 删除动态和词条中间
     * 
     * @param id 动态和词条中间ID
     * @return 结果
     */
    public int deleteDbDynamicAndEntryById(Long id);

    /**
     * 批量删除动态和词条中间
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbDynamicAndEntryByIds(String[] ids);
}
