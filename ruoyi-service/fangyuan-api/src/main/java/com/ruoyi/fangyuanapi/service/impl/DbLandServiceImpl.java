package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbLandMapper;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.common.core.text.Convert;

/**
 * 土地Service业务层处理
 * 
 * @author zheng
 * @date 2020-09-24
 */
@Service
public class DbLandServiceImpl implements IDbLandService 
{
    @Autowired
    private DbLandMapper dbLandMapper;

    /**
     * 查询土地
     * 
     * @param landId 土地ID
     * @return 土地
     */
    @Override
    public DbLand selectDbLandById(Long landId)
    {
        return dbLandMapper.selectDbLandById(landId);
    }

    /**
     * 查询土地列表
     * 
     * @param dbLand 土地
     * @return 土地
     */
    @Override
    public List<DbLand> selectDbLandList(DbLand dbLand)
    {
        return dbLandMapper.selectDbLandList(dbLand);
    }

    /**
     * 新增土地
     * 
     * @param dbLand 土地
     * @return 结果
     */
    @Override
    public int insertDbLand(DbLand dbLand)
    {
        dbLand.setCreateTime(DateUtils.getNowDate());
        return dbLandMapper.insertDbLand(dbLand);
    }

    /**
     * 修改土地
     * 
     * @param dbLand 土地
     * @return 结果
     */
    @Override
    public int updateDbLand(DbLand dbLand)
    {
        dbLand.setUpdateTime(DateUtils.getNowDate());
        return dbLandMapper.updateDbLand(dbLand);
    }

    /**
     * 删除土地对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbLandByIds(String ids)
    {
        return dbLandMapper.deleteDbLandByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除土地信息
     * 
     * @param landId 土地ID
     * @return 结果
     */
    public int deleteDbLandById(Long landId)
    {
        return dbLandMapper.deleteDbLandById(landId);
    }

    /**
     * 查询土地树列表
     * 
     * @return 所有土地信息
     */
    @Override
    public List<Ztree> selectDbLandTree()
    {
        List<DbLand> dbLandList = dbLandMapper.selectDbLandList(new DbLand());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (DbLand dbLand : dbLandList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(dbLand.getLandId());
            ztree.setpId(dbLand.getSiteId());
            ztree.setName(dbLand.getNickName());
            ztree.setTitle(dbLand.getNickName());
            ztrees.add(ztree);
        }
        return ztrees;
    }
}
