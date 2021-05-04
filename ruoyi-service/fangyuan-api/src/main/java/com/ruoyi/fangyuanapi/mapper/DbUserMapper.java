package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据userid查询用户头像和
     * @param userId
     * @return
     */
    Map<String,String> selectDbUserNicknameAndAvatarById(Long userId);


    /**
     * 查询个人资料
     * @param userId
     * @return
     */
    DbUser selectUserData(Long userId);

    /**
     * 根据手机号修改用户密码
     * @param phone
     * @param password
     * @param uuid
     * @return
     */
    int updateUserPassword(String phone, String password, String uuid);

    DbUser selectDbUserByPhoneAndOpenId(@Param("phone") String phone,@Param("openId") String openId);

    String selectAvatarById(Long dbUserId);

}
