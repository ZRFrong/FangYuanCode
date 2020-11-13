package com.ruoyi.fangyuanapi.service.impl;

import java.util.*;

import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.fangyuanapi.mapper.DbUserMapper;
import com.ruoyi.system.domain.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbAttentionMapper;
import com.ruoyi.system.domain.DbAttention;
import com.ruoyi.fangyuanapi.service.IDbAttentionService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 关注和被关注Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbAttentionServiceImpl implements IDbAttentionService 
{
    @Autowired
    private DbAttentionMapper dbAttentionMapper;

    @Autowired
    private DbUserMapper dbUserMapper;

    /**
     * 查询关注和被关注
     * 
     * @param id 关注和被关注ID
     * @return 关注和被关注
     */
    @Override
    public DbAttention selectDbAttentionById(Long id)
    {
        return dbAttentionMapper.selectDbAttentionById(id);
    }

    /**
     * 查询关注和被关注列表
     * 
     * @param dbAttention 关注和被关注
     * @return 关注和被关注
     */
    @Override
    public List<DbAttention> selectDbAttentionList(DbAttention dbAttention)
    {
        return dbAttentionMapper.selectDbAttentionList(dbAttention);
    }

    /**
     * 新增关注和被关注
     * 
     * @param dbAttention 关注和被关注
     * @return 结果
     */
    @Override
    public int insertDbAttention(DbAttention dbAttention)
    {
        return dbAttentionMapper.insertDbAttention(dbAttention);
    }

    /**
     * 修改关注和被关注
     * 
     * @param dbAttention 关注和被关注
     * @return 结果
     */
    @Override
    public int updateDbAttention(DbAttention dbAttention)
    {
        return dbAttentionMapper.updateDbAttention(dbAttention);
    }

    /**
     * 删除关注和被关注对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbAttentionByIds(String ids)
    {
        return dbAttentionMapper.deleteDbAttentionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除关注和被关注信息
     * 
     * @param id 关注和被关注ID
     * @return 结果
     */
    public int deleteDbAttentionById(Long id)
    {
        return dbAttentionMapper.deleteDbAttentionById(id);
    }

    /***
     * 通过userId查询关注的好友
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, String>> selectDbAttentionByUserId(String userId,Integer currPage) {
        List<Long> ids = dbAttentionMapper.selectDbAttentionByUserId(Long.valueOf(userId),currPage,PageConf.pageSize);
        if (ids == null || ids.size() <= 0){
            return null;
        }
        ArrayList<Map<String, String>> result = new ArrayList<>();
        ids.forEach(e -> {
            Map<String,String> user =dbUserMapper.selectDbUserNicknameAndAvatarById(e);
            result.add(user);
        });
        return result;
    }

    /**
     * 查询我关注全部人
     * @param userId
     * @return 关注的人id集合
     */
    @Override
    public List<Long> selectDbAttentionByUserId(String userId) {
        return dbAttentionMapper.selectReplyAttentionUserIds(Long.valueOf(userId));
    }

    /**
     * 取消关注
     * @param loginUserId
     * @param userId
     * @return boolean
     */
    @Override
    @Transactional
    public boolean deleteAttention(String loginUserId, Long userId) {
        DbAttention dbAttention = dbAttentionMapper.selectDbAttentionByUserIdAndReplyAttentionUserId(Long.valueOf(loginUserId),userId);
        if (dbAttention == null){
            return true;
        }
        int i = dbAttentionMapper.deleteDbAttentionById(dbAttention.getId());
        if (i>0){//维护用户表中关注和粉丝数量
            DbUser dbUser = dbUserMapper.selectDbUserById(Long.valueOf(loginUserId));
            dbUser.setAttentionNum(dbUser.getAttentionNum()-1);//关注减一
            dbUserMapper.updateDbUser(dbUser);//关注减一

            DbUser user = dbUserMapper.selectDbUserById(userId);
            user.setReplyAttentionUserNum(user.getReplyAttentionUserNum()-1);//粉丝减一
            dbUserMapper.updateDbUser(user);
            return true;
        }
        return false;
    }

    /**
     * 查询粉丝列表
     * @param userId
     * @param currPage
     * @return
     */
    @Override
    public List<Map<String, String>> getFans(String userId, Integer currPage) {
        List<Long>  ids = dbAttentionMapper.selectDbAttentionByReplyAttentionUserId(userId,currPage,PageConf.pageSize);
        if (ids == null || ids.size() <= 0){
            return null;
        }
        ArrayList<Map<String, String>> list = new ArrayList<>();
        ids.forEach(e -> {
            DbUser dbUser = dbUserMapper.selectDbUserById(e);
            HashMap<String, String> map = new HashMap<>();
            map.put("id",e+"");
            map.put("avatar",dbUser.getAvatar());
            map.put("nickname",dbUser.getNickname());
            list.add(map);
        });
        return list;
    }

    /**
     * 查询关注的人的id
     * @param userId 当前用户id
     * @return ids
     */
    @Override
    public List<Long> selectReplyAttentionUserIds(String userId) {
        List<Long> ids = dbAttentionMapper.selectReplyAttentionUserIdsByUserId(userId);
        return ids;
    }

    @Override
    @Transactional
    public DbAttention insertDbAttention(Long userId, Long attentionUserId) {
        DbAttention attention = dbAttentionMapper.selectDbAttentionByUserIdAndReplyAttentionUserId(userId, attentionUserId);
        if (attention == null){
            attention.setUserId(userId);
            attention.setReplyAttentionUserId(attentionUserId);
            int i = dbAttentionMapper.insertDbAttention(attention);
            return attention;
        }
        return null;
    }


}
