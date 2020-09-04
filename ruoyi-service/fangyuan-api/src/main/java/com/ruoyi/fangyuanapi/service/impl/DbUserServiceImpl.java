package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbUserMapper;
import com.ruoyi.fangyuanapi.domain.DbUser;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 前台用户Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbUserServiceImpl implements IDbUserService 
{
    @Autowired
    private DbUserMapper dbUserMapper;

    /**
     * 查询前台用户
     * 
     * @param id 前台用户ID
     * @return 前台用户
     */
    @Override
    public DbUser selectDbUserById(Long id)
    {
        return dbUserMapper.selectDbUserById(id);
    }

    /**
     * 查询前台用户列表
     * 
     * @param dbUser 前台用户
     * @return 前台用户
     */
    @Override
    public List<DbUser> selectDbUserList(DbUser dbUser)
    {
        return dbUserMapper.selectDbUserList(dbUser);
    }

    /**
     * 新增前台用户
     * 
     * @param dbUser 前台用户
     * @return 结果
     */
    @Override
    public int insertDbUser(DbUser dbUser)
    {
        return dbUserMapper.insertDbUser(dbUser);
    }

    /**
     * 修改前台用户
     * 
     * @param dbUser 前台用户
     * @return 结果
     */
    @Override
    public int updateDbUser(DbUser dbUser)
    {
        return dbUserMapper.updateDbUser(dbUser);
    }

    /**
     * 删除前台用户对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbUserByIds(String ids)
    {
        return dbUserMapper.deleteDbUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除前台用户信息
     * 
     * @param id 前台用户ID
     * @return 结果
     */
    public int deleteDbUserById(Long id)
    {
        return dbUserMapper.deleteDbUserById(id);
    }
}
