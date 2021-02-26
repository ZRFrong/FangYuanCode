package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbProblemType;
import java.util.List;
import java.util.Map;

/**
 * 问题类型Mapper接口
 * 
 * @author zheng
 * @date 2021-01-19
 */
public interface DbProblemTypeMapper 
{
    /**
     * 查询问题类型
     * 
     * @param id 问题类型ID
     * @return 问题类型
     */
    public DbProblemType selectDbProblemTypeById(Long id);

    /**
     * 查询问题类型列表
     * 
     * @param dbProblemType 问题类型
     * @return 问题类型集合
     */
    public List<DbProblemType> selectDbProblemTypeList(DbProblemType dbProblemType);

    /**
     * 新增问题类型
     * 
     * @param dbProblemType 问题类型
     * @return 结果
     */
    public int insertDbProblemType(DbProblemType dbProblemType);

    /**
     * 修改问题类型
     * 
     * @param dbProblemType 问题类型
     * @return 结果
     */
    public int updateDbProblemType(DbProblemType dbProblemType);

    /**
     * 删除问题类型
     * 
     * @param id 问题类型ID
     * @return 结果
     */
    public int deleteDbProblemTypeById(Long id);

    /**
     * 批量删除问题类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbProblemTypeByIds(String[] ids);

    List<Map<String,Object>> selectDbProblemList(Integer type, Integer currPage,Integer pageSize);

    List<Map<String,Object>> getAllProblemType();

}
