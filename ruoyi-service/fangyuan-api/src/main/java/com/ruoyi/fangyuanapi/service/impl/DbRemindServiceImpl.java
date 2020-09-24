package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbRemindMapper;
import com.ruoyi.system.domain.DbRemind;
import com.ruoyi.fangyuanapi.service.IDbRemindService;
import com.ruoyi.common.core.text.Convert;

/**
 * 提醒Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbRemindServiceImpl implements IDbRemindService 
{
    @Autowired
    private DbRemindMapper dbRemindMapper;

    /**
     * 查询提醒
     * 
     * @param id 提醒ID
     * @return 提醒
     */
    @Override
    public DbRemind selectDbRemindById(Long id)
    {
        return dbRemindMapper.selectDbRemindById(id);
    }

    /**
     * 查询提醒列表
     * 
     * @param dbRemind 提醒
     * @return 提醒
     */
    @Override
    public List<DbRemind> selectDbRemindList(DbRemind dbRemind)
    {
        return dbRemindMapper.selectDbRemindList(dbRemind);
    }

    /**
     * 新增提醒
     * 
     * @param dbRemind 提醒
     * @return 结果
     */
    @Override
    public int insertDbRemind(DbRemind dbRemind)
    {
        return dbRemindMapper.insertDbRemind(dbRemind);
    }

    /**
     * 修改提醒
     * 
     * @param dbRemind 提醒
     * @return 结果
     */
    @Override
    public int updateDbRemind(DbRemind dbRemind)
    {
        return dbRemindMapper.updateDbRemind(dbRemind);
    }

    /**
     * 删除提醒对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbRemindByIds(String ids)
    {
        return dbRemindMapper.deleteDbRemindByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除提醒信息
     * 
     * @param id 提醒ID
     * @return 结果
     */
    public int deleteDbRemindById(Long id)
    {
        return dbRemindMapper.deleteDbRemindById(id);
    }
}
