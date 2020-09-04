package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbUserDynamicMapper;
import com.ruoyi.fangyuanapi.domain.DbUserDynamic;
import com.ruoyi.fangyuanapi.service.IDbUserDynamicService;
import com.ruoyi.common.core.text.Convert;

/**
 * 动态Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbUserDynamicServiceImpl implements IDbUserDynamicService 
{
    @Autowired
    private DbUserDynamicMapper dbUserDynamicMapper;

    /**
     * 查询动态
     * 
     * @param id 动态ID
     * @return 动态
     */
    @Override
    public DbUserDynamic selectDbUserDynamicById(Long id)
    {
        return dbUserDynamicMapper.selectDbUserDynamicById(id);
    }

    /**
     * 查询动态列表
     * 
     * @param dbUserDynamic 动态
     * @return 动态
     */
    @Override
    public List<DbUserDynamic> selectDbUserDynamicList(DbUserDynamic dbUserDynamic)
    {
        return dbUserDynamicMapper.selectDbUserDynamicList(dbUserDynamic);
    }

    /**
     * 新增动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    @Override
    public int insertDbUserDynamic(DbUserDynamic dbUserDynamic)
    {
        return dbUserDynamicMapper.insertDbUserDynamic(dbUserDynamic);
    }

    /**
     * 修改动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    @Override
    public int updateDbUserDynamic(DbUserDynamic dbUserDynamic)
    {
        return dbUserDynamicMapper.updateDbUserDynamic(dbUserDynamic);
    }

    /**
     * 删除动态对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbUserDynamicByIds(String ids)
    {
        return dbUserDynamicMapper.deleteDbUserDynamicByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除动态信息
     * 
     * @param id 动态ID
     * @return 结果
     */
    public int deleteDbUserDynamicById(Long id)
    {
        return dbUserDynamicMapper.deleteDbUserDynamicById(id);
    }
}
