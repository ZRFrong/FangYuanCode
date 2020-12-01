package com.ruoyi.fangyuanapi.service.impl;

import java.util.*;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DbUserDynamic;
import com.ruoyi.fangyuanapi.dto.DynamicDto;
import com.ruoyi.fangyuanapi.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.DbUser;
import com.ruoyi.fangyuanapi.service.IDbUserService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private DbUserDynamicMapper dbUserDynamicMapper;

    @Autowired
    private DbUserAndDynamicMapper dbUserAndDynamicMapper;

    @Autowired
    private DbEntryMapper dbEntryMapper;

    @Autowired
    private DbForwardMapper dbForwardMapper;

    @Autowired
    private DbCommentMapper dbCommentMapper;

    @Autowired
    private DbGiveLikeMapper dbGiveLikeMapper;

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
    @Transactional
    public int insertDbUser(DbUser dbUser)
    {
        dbUser.setCreated(new Date());
        //dbUser.setPhoneIsVerify(1);
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

    /**
     * 通过手机号查用户是否存在
     * @param dbUser
     * @return
     */
    @Override
    public DbUser selectDbUserByPhone(DbUser dbUser) {
        DbUser result =  dbUserMapper.selectDbUserByPhone(dbUser.getPhone());
        return result;
    }

    @Override
    public Map<String, Integer> userIsRegister(String openId) {
        DbUser dbUser = dbUserMapper.selectDbUserByOpenId(openId);
        Long id = dbUser.getId();
        if (null == id){
            return null;
        }
        //返回 动态 关注 粉丝 数量

        return null;
    }

    @Override
    public DbUser selectDbUserByOpenId(String openId) {
        DbUser user = dbUserMapper.selectDbUserByOpenId(openId);
        return user;
    }

    @Override
    public List<DynamicDto> getUserDynamic(DbUser user,Integer currPage,Integer pageSize) {
        DynamicDto dynamicDto = new DynamicDto();
        dynamicDto.setAvatar(user.getAvatar());
        //dynamicDto.setNickname(user.getNickname());
        Integer count = dbUserAndDynamicMapper.selectDbUserAndDynamicCountByUserId(user.getId());
        currPage = pageSize > count ? count : pageSize;
        List<DbUserDynamic> dbUserDynamics =dbUserDynamicMapper.selectDbUserDynamicByUserId(user.getId(),currPage,pageSize);
        ArrayList<DynamicDto> dto = new ArrayList<>();
        for (DbUserDynamic dbUserDynamic : dbUserDynamics) {
            dynamicDto.setContent(dbUserDynamic.getContent());
            //dynamicDto.setResource(dbUserDynamic.getResource());
            dynamicDto.setCreatedTime(dbUserDynamic.getCreatedTime());
            List<String> relSet = dbEntryMapper.selectDbEntrys(dbUserDynamic.getId());
            dynamicDto.setRelSet(relSet);//词条集合
            Integer forwardSum = dbForwardMapper.selectDbForwardSumByUserId(user.getId());
            dynamicDto.setForwardSum(forwardSum);//转发数量 通过user_id 查
            Integer commentSum = dbCommentMapper.selectDbCommentSumByDynamicId();
            dynamicDto.setCommentSum(commentSum);//评论数量 通过动态id查
            Integer giveLikeSum = dbGiveLikeMapper.selectGiveLikeSumByDynamicId();
            dynamicDto.setLiveGiveSum(giveLikeSum);//动态对应的点赞数量
            dto.add(dynamicDto);
        }
        return dto;
    }

    @Override
    public Map<String, String> getUserData(Long userId) {
        Map<String,String> map = new HashMap<>();
        DbUser user = dbUserMapper.selectUserData(userId);
        map.put("id",user.getId()+"");
        map.put("avatar",user.getAvatar()+"");
        map.put("user_name",user.getUserName()+"");
        map.put("nickname",user.getNickname()+"");
        map.put("gender",user.getGender()+"");
        map.put("age",user.getAge()+"");
        map.put("signature",user.getSignature()+"");
        return map;
    }

    @Override
    @Transactional
    public DbUser wxRegister(String phone,String openId,String nickname,String avatar) {
        DbUser user = dbUserMapper.selectDbUserByPhone(phone);
        if (user != null ){
            if (openId.equals(user.getOpenId()+"")){
                return user;
            }
            user.setOpenId(openId);
            dbUserMapper.updateDbUser(user);
        }
        user = new DbUser();
        user.setOpenId(openId);
        user.setNickname(nickname);
        user.setAvatar(avatar);
        user.setPhone(phone);
        user.setPhoneIsVerify(1);
        user.setCreateTime(new Date());
        user.setUserFrom(0);
        dbUserMapper.insertDbUser(user);
        return user;
    }

    @Override
    @Transactional
    public int updateUserPassword(String phone, String s, String uuid) {
        int i = dbUserMapper.updateUserPassword(phone,s,uuid);
        return 0;
    }

    @Override
    public DbUser selectDbUserByPhoneAndOpenId(String phone, String openId) {
        return dbUserMapper.selectDbUserByPhoneAndOpenId(phone,openId);
    }

    public static void main(String[] args){
        Date date = new Date();
        System.out.println(date);
    }
}
