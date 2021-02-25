package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import java.util.Map;
import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbProblemTypeMapper;
import com.ruoyi.system.domain.DbProblemType;
import com.ruoyi.fangyuanapi.service.IDbProblemTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 问题类型Service业务层处理
 * 
 * @author zheng
 * @date 2021-01-19
 */
@Service
public class DbProblemTypeServiceImpl implements IDbProblemTypeService 
{
    @Autowired
    private DbProblemTypeMapper dbProblemTypeMapper;

    /**
     * 查询问题类型
     * 
     * @param id 问题类型ID
     * @return 问题类型
     */
    @Override
    public DbProblemType selectDbProblemTypeById(Long id)
    {
        return dbProblemTypeMapper.selectDbProblemTypeById(id);
    }

    /**
     * 查询问题类型列表
     * 
     * @param dbProblemType 问题类型
     * @return 问题类型
     */
    @Override
    public List<DbProblemType> selectDbProblemTypeList(DbProblemType dbProblemType)
    {
        return dbProblemTypeMapper.selectDbProblemTypeList(dbProblemType);
    }

    /**
     * 新增问题类型
     * 
     * @param dbProblemType 问题类型
     * @return 结果
     */
    @Override
    public int insertDbProblemType(DbProblemType dbProblemType)
    {
        dbProblemType.setCreateTime(DateUtils.getNowDate());
        return dbProblemTypeMapper.insertDbProblemType(dbProblemType);
    }

    /**
     * 修改问题类型
     * 
     * @param dbProblemType 问题类型
     * @return 结果
     */
    @Override
    public int updateDbProblemType(DbProblemType dbProblemType)
    {
        dbProblemType.setUpdateTime(DateUtils.getNowDate());
        return dbProblemTypeMapper.updateDbProblemType(dbProblemType);
    }

    /**
     * 删除问题类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbProblemTypeByIds(String ids)
    {
        return dbProblemTypeMapper.deleteDbProblemTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除问题类型信息
     * 
     * @param id 问题类型ID
     * @return 结果
     */
    public int deleteDbProblemTypeById(Long id)
    {
        return dbProblemTypeMapper.deleteDbProblemTypeById(id);
    }

    @Override
    public List<Map<String,Object>> selectDbProblemList(Integer type, Integer currPage) {
        return dbProblemTypeMapper.selectDbProblemList(type,currPage,PageConf.pageSize);
    }
}
