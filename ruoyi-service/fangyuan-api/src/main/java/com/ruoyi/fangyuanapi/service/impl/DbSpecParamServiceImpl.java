package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import java.util.Set;

import com.ruoyi.system.domain.DbSpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbSpecParamMapper;
import com.ruoyi.fangyuanapi.service.IDbSpecParamService;
import com.ruoyi.common.core.text.Convert;

/**
 * db_spec_paramService业务层处理
 * 
 * @author zheng
 * @date 2020-09-30
 */
@Service
public class DbSpecParamServiceImpl implements IDbSpecParamService 
{
    @Autowired
    private DbSpecParamMapper dbSpecParamMapper;

    /**
     * 查询db_spec_param
     * 
     * @param id db_spec_paramID
     * @return db_spec_param
     */
    @Override
    public DbSpecParam selectDbSpecParamById(Long id)
    {
        return dbSpecParamMapper.selectDbSpecParamById(id);
    }

    /**
     * 查询db_spec_param列表
     * 
     * @param dbSpecParam db_spec_param
     * @return db_spec_param
     */
    @Override
    public List<DbSpecParam> selectDbSpecParamList(DbSpecParam dbSpecParam)
    {
        return dbSpecParamMapper.selectDbSpecParamList(dbSpecParam);
    }

    /**
     * 新增db_spec_param
     * 
     * @param dbSpecParam db_spec_param
     * @return 结果
     */
    @Override
    public int insertDbSpecParam(DbSpecParam dbSpecParam)
    {
        return dbSpecParamMapper.insertDbSpecParam(dbSpecParam);
    }

    /**
     * 修改db_spec_param
     * 
     * @param dbSpecParam db_spec_param
     * @return 结果
     */
    @Override
    public int updateDbSpecParam(DbSpecParam dbSpecParam)
    {
        return dbSpecParamMapper.updateDbSpecParam(dbSpecParam);
    }

    /**
     * 删除db_spec_param对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbSpecParamByIds(String ids)
    {
        return dbSpecParamMapper.deleteDbSpecParamByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除db_spec_param信息
     * 
     * @param id db_spec_paramID
     * @return 结果
     */
    @Override
    public int deleteDbSpecParamById(Long id)
    {
        return dbSpecParamMapper.deleteDbSpecParamById(id);
    }

    /**
     * 查询用户下单的pram
     * @param ids
     * @return
     */
    @Override
    public List<DbSpecParam> selectDbSpecParamByIds(Set<Long> ids) {
        List<DbSpecParam> params = dbSpecParamMapper.selectDbSpecParamByIds(ids);
        return params;
    }
}
