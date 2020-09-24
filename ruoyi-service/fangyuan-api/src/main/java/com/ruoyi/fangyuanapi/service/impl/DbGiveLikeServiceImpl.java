package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbGiveLikeMapper;
import com.ruoyi.system.domain.DbGiveLike;
import com.ruoyi.fangyuanapi.service.IDbGiveLikeService;
import com.ruoyi.common.core.text.Convert;

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
}
