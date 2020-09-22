package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbUserAndDynamicMapper;
import com.ruoyi.fangyuanapi.domain.DbUserAndDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserAndDynamicService;
import com.ruoyi.common.core.text.Convert;

/**
 * 前台用户和动态中间Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbUserAndDynamicServiceImpl implements IDbUserAndDynamicService 
{
    @Autowired
    private DbUserAndDynamicMapper dbUserAndDynamicMapper;

    /**
     * 查询前台用户和动态中间
     * 
     * @param id 前台用户和动态中间ID
     * @return 前台用户和动态中间
     */
    @Override
    public DbUserAndDynamic selectDbUserAndDynamicById(Long id)
    {
        return dbUserAndDynamicMapper.selectDbUserAndDynamicById(id);
    }

    /**
     * 查询前台用户和动态中间列表
     * 
     * @param dbUserAndDynamic 前台用户和动态中间
     * @return 前台用户和动态中间
     */
    @Override
    public List<DbUserAndDynamic> selectDbUserAndDynamicList(DbUserAndDynamic dbUserAndDynamic)
    {
        return dbUserAndDynamicMapper.selectDbUserAndDynamicList(dbUserAndDynamic);
    }

    /**
     * 新增前台用户和动态中间
     * 
     * @param dbUserAndDynamic 前台用户和动态中间
     * @return 结果
     */
    @Override
    public int insertDbUserAndDynamic(DbUserAndDynamic dbUserAndDynamic)
    {
        return dbUserAndDynamicMapper.insertDbUserAndDynamic(dbUserAndDynamic);
    }

    /**
     * 修改前台用户和动态中间
     * 
     * @param dbUserAndDynamic 前台用户和动态中间
     * @return 结果
     */
    @Override
    public int updateDbUserAndDynamic(DbUserAndDynamic dbUserAndDynamic)
    {
        return dbUserAndDynamicMapper.updateDbUserAndDynamic(dbUserAndDynamic);
    }

    /**
     * 删除前台用户和动态中间对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbUserAndDynamicByIds(String ids)
    {
        return dbUserAndDynamicMapper.deleteDbUserAndDynamicByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除前台用户和动态中间信息
     * 
     * @param id 前台用户和动态中间ID
     * @return 结果
     */
    public int deleteDbUserAndDynamicById(Long id)
    {
        return dbUserAndDynamicMapper.deleteDbUserAndDynamicById(id);
    }

    /**
     * 查询用户所发布动态的id1
     * @param userId
     * @return
     */
    @Override
    public List<Long> selectDbUserAndDynamicByUserId(Long userId) {
        List<Long> list = dbUserAndDynamicMapper.selectDbUserAndDynamicByUserId(userId);
        return list;
    }
}
