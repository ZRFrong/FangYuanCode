package com.ruoyi.fangyuanapi.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.fangyuanapi.mapper.DbUserDynamicMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserMapper;
import com.ruoyi.system.domain.DbUserDynamic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbGiveLikeMapper;
import com.ruoyi.system.domain.DbGiveLike;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 点赞Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbGiveLikeServiceImpl implements IDbGiveLikeService 
{
    @Autowired
    private DbGiveLikeMapper dbGiveLikeMapper;

    @Autowired
    private DbUserDynamicMapper dbUserDynamicMapper;

    @Autowired
    private DbUserMapper dbUserMapper;

    /**
     * 查询点赞
     * 
     * @param id 点赞ID
     * @return 点赞
     */
    @Override
    public DbGiveLike selectDbGiveLikeById(Long id)
    {
        return dbGiveLikeMapper.selectDbGiveLikeById(id);
    }

    /**
     * 查询点赞列表
     * 
     * @param dbGiveLike 点赞
     * @return 点赞
     */
    @Override
    public List<DbGiveLike> selectDbGiveLikeList(DbGiveLike dbGiveLike)
    {
        return dbGiveLikeMapper.selectDbGiveLikeList(dbGiveLike);
    }

    /**
     * 新增点赞
     * 
     * @param dbGiveLike 点赞
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDbGiveLike(DbGiveLike dbGiveLike)
    {
        return dbGiveLikeMapper.insertDbGiveLike(dbGiveLike);
    }

    /**
     * 修改点赞
     * 
     * @param dbGiveLike 点赞
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDbGiveLike(DbGiveLike dbGiveLike)
    {
        return dbGiveLikeMapper.updateDbGiveLike(dbGiveLike);
    }

    /**
     * 删除点赞对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbGiveLikeByIds(String ids)
    {
        return dbGiveLikeMapper.deleteDbGiveLikeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除点赞信息
     * 
     * @param id 点赞ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteDbGiveLikeById(Long id)
    {
        return dbGiveLikeMapper.deleteDbGiveLikeById(id);
    }

    /**
     * 我的赞
     * @param userId
     * @return
     */
    @Override
    public Integer selectUserGiveLikeNum(String userId) {
        return dbGiveLikeMapper.selectUserGiveLikeNum(Long.valueOf(userId));
    }

    /**
     * 用户给动态点赞接口
     * @param userId
     * @param dynamicId
     * @return
     */
    @Override
    @Transactional
    public boolean insertDbGiveLikeAndLikeNum(Long userId, Long dynamicId) {
        DbGiveLike giveLike = new DbGiveLike();
        giveLike.setDynamicId(dynamicId);
        giveLike.setGiveLikeTime(new Date());
        giveLike.setUserId(userId);
        int i = dbGiveLikeMapper.insertDbGiveLike(giveLike);
        DbUserDynamic dynamic = dbUserDynamicMapper.selectDbUserDynamicById(dynamicId);
        dynamic.setLikeNum(dynamic.getLikeNum()+1);
        dbUserDynamicMapper.updateDbUserDynamic(dynamic);//todo
        return true;
    }

    /**
     * 根据用户id和动态id查询
     * @param header
     * @param id
     * @return
     */
    @Override
    public DbGiveLike selectDbGiveLikeByUserIdAndDynamicId(String header, Long id) {
        return dbGiveLikeMapper.selectDbGiveLikeByUserIdAndDynamicId(Long.valueOf(header),id);
    }

}
