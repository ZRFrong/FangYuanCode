package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbForwardMapper;
import com.ruoyi.system.domain.DbForward;
import com.ruoyi.fangyuanapi.service.IDbForwardService;
import com.ruoyi.common.core.text.Convert;

/**
 * 转发Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbForwardServiceImpl implements IDbForwardService 
{
    @Autowired
    private DbForwardMapper dbForwardMapper;

    /**
     * 查询转发
     * 
     * @param id 转发ID
     * @return 转发
     */
    @Override
    public DbForward selectDbForwardById(Long id)
    {
        return dbForwardMapper.selectDbForwardById(id);
    }

    /**
     * 查询转发列表
     * 
     * @param dbForward 转发
     * @return 转发
     */
    @Override
    public List<DbForward> selectDbForwardList(DbForward dbForward)
    {
        return dbForwardMapper.selectDbForwardList(dbForward);
    }

    /**
     * 新增转发
     * 
     * @param dbForward 转发
     * @return 结果
     */
    @Override
    public int insertDbForward(DbForward dbForward)
    {
        return dbForwardMapper.insertDbForward(dbForward);
    }

    /**
     * 修改转发
     * 
     * @param dbForward 转发
     * @return 结果
     */
    @Override
    public int updateDbForward(DbForward dbForward)
    {
        return dbForwardMapper.updateDbForward(dbForward);
    }

    /**
     * 删除转发对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbForwardByIds(String ids)
    {
        return dbForwardMapper.deleteDbForwardByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除转发信息
     * 
     * @param id 转发ID
     * @return 结果
     */
    public int deleteDbForwardById(Long id)
    {
        return dbForwardMapper.deleteDbForwardById(id);
    }
}
