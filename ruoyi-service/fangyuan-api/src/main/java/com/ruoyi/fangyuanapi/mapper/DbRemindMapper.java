package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.fangyuanapi.domain.DbRemind;
import java.util.List;

/**
 * 提醒Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbRemindMapper 
{
    /**
     * 查询提醒
     * 
     * @param id 提醒ID
     * @return 提醒
     */
    public DbRemind selectDbRemindById(Long id);

    /**
     * 查询提醒列表
     * 
     * @param dbRemind 提醒
     * @return 提醒集合
     */
    public List<DbRemind> selectDbRemindList(DbRemind dbRemind);

    /**
     * 新增提醒
     * 
     * @param dbRemind 提醒
     * @return 结果
     */
    public int insertDbRemind(DbRemind dbRemind);

    /**
     * 修改提醒
     * 
     * @param dbRemind 提醒
     * @return 结果
     */
    public int updateDbRemind(DbRemind dbRemind);

    /**
     * 删除提醒
     * 
     * @param id 提醒ID
     * @return 结果
     */
    public int deleteDbRemindById(Long id);

    /**
     * 批量删除提醒
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbRemindByIds(String[] ids);
}
