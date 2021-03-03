package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbProblem;
import java.util.List;
import java.util.Map;

/**
 * 问题Service接口
 * 
 * @author zheng
 * @date 2021-01-18
 */
public interface IDbProblemService 
{
    /**·
     * 查询问题
     * 
     * @param id 问题ID
     * @return 问题
     */
    public Map<String,Object> selectDbProblemById(Long id);

    /**
     * 查询问题列表
     * 
     * @param dbProblem 问题
     * @return 问题集合
     */
    public List<DbProblem> selectDbProblemList(DbProblem dbProblem);

    /**
     * 新增问题
     * 
     * @param dbProblem 问题
     * @return 结果
     */
    public int insertDbProblem(DbProblem dbProblem);

    /**
     * 修改问题
     * 
     * @param dbProblem 问题
     * @return 结果
     */
    public int updateDbProblem(DbProblem dbProblem);

    /**
     * 批量删除问题
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbProblemByIds(String ids);

    /**
     * 删除问题信息
     * 
     * @param id 问题ID
     * @return 结果
     */
    public int deleteDbProblemById(Long id);

    List<Map<String,Object>> getProblemListByType(Integer problemType, Integer currPage,Integer pageSize);

    List<DbProblem> selectDbProblem(Integer currPage, Integer pageSize);

    Long selectDbProblemCount();

}
