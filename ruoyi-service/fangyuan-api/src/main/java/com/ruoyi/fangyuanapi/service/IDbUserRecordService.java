package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbUserRecord;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zheng
 * @date 2021-03-04
 */
public interface IDbUserRecordService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DbUserRecord selectDbUserRecordById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbUserRecord 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DbUserRecord> selectDbUserRecordList(DbUserRecord dbUserRecord);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbUserRecord 【请填写功能名称】
     * @return 结果
     */
    public int insertDbUserRecord(DbUserRecord dbUserRecord);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbUserRecord 【请填写功能名称】
     * @return 结果
     */
    public int updateDbUserRecord(DbUserRecord dbUserRecord);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbUserRecordByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbUserRecordById(Long id);
}
