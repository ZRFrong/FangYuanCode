package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbProblem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author zheng
 * @date 2021-01-18
 */
public interface DbProblemMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DbProblem selectDbProblemById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbProblem 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DbProblem> selectDbProblemList(DbProblem dbProblem);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbProblem 【请填写功能名称】
     * @return 结果
     */
    public int insertDbProblem(DbProblem dbProblem);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbProblem 【请填写功能名称】
     * @return 结果
     */
    public int updateDbProblem(DbProblem dbProblem);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbProblemById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbProblemByIds(String[] ids);

    List<Map<String,Object>> getProblemListByType(@Param("problemType") Integer problemType,@Param("currPage") Integer currPage,@Param("pageSize") Integer pageSize);

    Map<String,Object> selectDbProblemAnswerTextById(Long id);

    void updateProblemHot(Long id);

    List<DbProblem> selectDbProblem(@Param("currPage") Integer currPage, @Param("pageSize") Integer pageSize);

    Long selectDbProblemCount();

}
