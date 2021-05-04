package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbUser;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import org.apache.ibatis.annotations.Param;

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

    DbUser selectDbUserByPhone(DbUser dbUser);

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


    /**
     * 获取个人资料接口
     * @param userId
     * @return
     */
    Map<String,String> getUserData(Long userId);

    DbUser wxRegister(String phone,String openId,String nickname,String avatar);

    /**
     * 根据手机号修改用户密码
     * @param phone
     * @param s
     * @param uuid
     * @return
     */
    int updateUserPassword(String phone, String s, String uuid);

    /**
     * 根据手机号和openid查询User
     * @param phone
     * @param openId
     * @return
     */
    DbUser selectDbUserByPhoneAndOpenId(@Param(value = "phone") String phone,@Param(value = "openId") String openId);

    String selectAvatarById(Long dbUserId);
}
