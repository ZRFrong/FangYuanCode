package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbUserLogin;
import java.util.List;

/**
 * 登录日志Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbUserLoginService 
{
    /**
     * 查询登录日志
     * 
     * @param id 登录日志ID
     * @return 登录日志
     */
    public DbUserLogin selectDbUserLoginById(Long id);

    /**
     * 查询登录日志列表
     * 
     * @param dbUserLogin 登录日志
     * @return 登录日志集合
     */
    public List<DbUserLogin> selectDbUserLoginList(DbUserLogin dbUserLogin);

    /**
     * 新增登录日志
     * 
     * @param dbUserLogin 登录日志
     * @return 结果
     */
    public int insertDbUserLogin(DbUserLogin dbUserLogin);

    /**
     * 修改登录日志
     * 
     * @param dbUserLogin 登录日志
     * @return 结果
     */
    public int updateDbUserLogin(DbUserLogin dbUserLogin);

    /**
     * 批量删除登录日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbUserLoginByIds(String ids);

    /**
     * 删除登录日志信息
     * 
     * @param id 登录日志ID
     * @return 结果
     */
    public int deleteDbUserLoginById(Long id);

    /**
     * 根据userId查询登陆状态
     * @param id
     * @return
     */
    DbUserLogin selectDbUserLoginByUserId(Long id);

}
