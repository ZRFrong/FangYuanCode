package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbEntry;
import java.util.List;

/**
 * 词条Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbEntryMapper 
{
    /**
     * 查询词条
     * 
     * @param id 词条ID
     * @return 词条
     */
    public DbEntry selectDbEntryById(Long id);

    /**
     * 查询词条列表
     * 
     * @param dbEntry 词条
     * @return 词条集合
     */
    public List<DbEntry> selectDbEntryList(DbEntry dbEntry);

    /**
     * 新增词条
     * 
     * @param dbEntry 词条
     * @return 结果
     */
    public int insertDbEntry(DbEntry dbEntry);

    /**
     * 修改词条
     * 
     * @param dbEntry 词条
     * @return 结果
     */
    public int updateDbEntry(DbEntry dbEntry);

    /**
     * 删除词条
     * 
     * @param id 词条ID
     * @return 结果
     */
    public int deleteDbEntryById(Long id);

    /**
     * 批量删除词条
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbEntryByIds(String[] ids);

    List<String> selectDbEntrys(Long id);
}
