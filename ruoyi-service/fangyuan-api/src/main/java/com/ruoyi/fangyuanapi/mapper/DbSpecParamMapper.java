package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbSpecParam;

import java.util.List;
import java.util.Set;

/**
 * db_spec_paramMapper接口
 * 
 * @author zheng
 * @date 2020-09-30
 */
public interface DbSpecParamMapper 
{
    /**
     * 查询db_spec_param
     * 
     * @param id db_spec_paramID
     * @return db_spec_param
     */
    public DbSpecParam selectDbSpecParamById(Long id);

    /**
     * 查询db_spec_param列表
     * 
     * @param dbSpecParam db_spec_param
     * @return db_spec_param集合
     */
    public List<DbSpecParam> selectDbSpecParamList(DbSpecParam dbSpecParam);

    /**
     * 新增db_spec_param
     * 
     * @param dbSpecParam db_spec_param
     * @return 结果
     */
    public int insertDbSpecParam(DbSpecParam dbSpecParam);

    /**
     * 修改db_spec_param
     * 
     * @param dbSpecParam db_spec_param
     * @return 结果
     */
    public int updateDbSpecParam(DbSpecParam dbSpecParam);

    /**
     * 删除db_spec_param
     * 
     * @param id db_spec_paramID
     * @return 结果
     */
    public int deleteDbSpecParamById(Long id);

    /**
     * 批量删除db_spec_param
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbSpecParamByIds(String[] ids);

    /**
     * 查询prams
     * @param ids
     * @return
     */
    List<DbSpecParam> selectDbSpecParamByIds(Set<Long> ids);
}
