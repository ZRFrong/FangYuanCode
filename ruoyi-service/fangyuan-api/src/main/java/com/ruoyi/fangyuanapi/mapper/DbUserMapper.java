package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.fangyuanapi.domain.DbUser;
import java.util.List;

/**
 * 前台用户Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbUserMapper 
{
    /**
     * 查询前台用户
     * 
     * @param id 前台用户ID
     * @return 前台用户
     */
    public DbUser selectDbUserById(Long id);

    /**
     * 查询前台用户列表
     * 
     * @param dbUser 前台用户
     * @return 前台用户集合
     */
    public List<DbUser> selectDbUserList(DbUser dbUser);

    /**
     * 新增前台用户
     * 
     * @param dbUser 前台用户
     * @return 结果
     */
    public int insertDbUser(DbUser dbUser);

    /**
     * 修改前台用户
     * 
     * @param dbUser 前台用户
     * @return 结果
     */
    public int updateDbUser(DbUser dbUser);

    /**
     * 删除前台用户
     * 
     * @param id 前台用户ID
     * @return 结果
     */
    public int deleteDbUserById(Long id);

    /**
     * 批量删除前台用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbUserByIds(String[] ids);

    /**
     * 根据手机号查找用户
     * @param phone
     * @return
     */
    DbUser selectDbUserByPhone(String phone);

    DbUser selectDbUserByOpenId(String openId);
}
