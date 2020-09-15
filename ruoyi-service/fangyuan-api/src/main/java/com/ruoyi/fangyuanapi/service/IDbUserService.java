package com.ruoyi.fangyuanapi.service;

import com.ruoyi.fangyuanapi.domain.DbUser;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 前台用户Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbUserService
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
     * 批量删除前台用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbUserByIds(String ids);

    /**
     * 删除前台用户信息
     * 
     * @param id 前台用户ID
     * @return 结果
     */
    public int deleteDbUserById(Long id);

    boolean selectDbUserByPhone(DbUser dbUser);

    /**
     * 根据openId查询用户是否注册
     * @param openId
     * @return
     */
    Map<String,Integer> userIsRegister(String openId);

    /**
     * 根据openId查询用户
     * @param openId
     * @return
     */
    DbUser selectDbUserByOpenId(String openId);

    /**
     * 获取
     * @param user
     * @param currPage
     * @param pageSize
     * @return
     */
    List<DynamicDto> getUserDynamic(DbUser user,Integer currPage,Integer pageSize);

}
