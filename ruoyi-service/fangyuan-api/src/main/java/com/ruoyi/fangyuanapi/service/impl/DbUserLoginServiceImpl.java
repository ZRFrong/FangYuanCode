package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbUserLoginMapper;
import com.ruoyi.system.domain.DbUserLogin;
import com.ruoyi.fangyuanapi.service.IDbUserLoginService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录日志Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbUserLoginServiceImpl implements IDbUserLoginService 
{
    @Autowired
    private DbUserLoginMapper dbUserLoginMapper;

    /**
     * 查询登录日志
     * 
     * @param id 登录日志ID
     * @return 登录日志
     */
    @Override
    public DbUserLogin selectDbUserLoginById(Long id)
    {
        return dbUserLoginMapper.selectDbUserLoginById(id);
    }

    /**
     * 查询登录日志列表
     * 
     * @param dbUserLogin 登录日志
     * @return 登录日志
     */
    @Override
    public List<DbUserLogin> selectDbUserLoginList(DbUserLogin dbUserLogin)
    {
        return dbUserLoginMapper.selectDbUserLoginList(dbUserLogin);
    }

    /**
     * 新增登录日志
     * 
     * @param dbUserLogin 登录日志
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDbUserLogin(DbUserLogin dbUserLogin)
    {
        return dbUserLoginMapper.insertDbUserLogin(dbUserLogin);
    }

    /**
     * 修改登录日志
     * 
     * @param dbUserLogin 登录日志
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDbUserLogin(DbUserLogin dbUserLogin)
    {
        return dbUserLoginMapper.updateDbUserLogin(dbUserLogin);
    }

    /**
     * 删除登录日志对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDbUserLoginByIds(String ids)
    {
        return dbUserLoginMapper.deleteDbUserLoginByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除登录日志信息
     * 
     * @param id 登录日志ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDbUserLoginById(Long id)
    {
        return dbUserLoginMapper.deleteDbUserLoginById(id);
    }

    /**
     * 根据userId查询登陆状态
     * @param id
     * @return
     */
    @Override
    public DbUserLogin selectDbUserLoginByUserId(Long id) {
        return dbUserLoginMapper.selectDbUserLoginByUserId(id);
    }
}
