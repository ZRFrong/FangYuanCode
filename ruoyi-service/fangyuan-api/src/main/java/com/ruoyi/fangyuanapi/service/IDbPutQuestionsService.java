package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbPutQuestions;
import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zheng
 * @date 2021-01-18
 */
public interface IDbPutQuestionsService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public DbPutQuestions selectDbPutQuestionsById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbPutQuestions 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<DbPutQuestions> selectDbPutQuestionsList(DbPutQuestions dbPutQuestions);

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbPutQuestions 【请填写功能名称】
     * @return 结果
     */
    public int insertDbPutQuestions(DbPutQuestions dbPutQuestions);

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbPutQuestions 【请填写功能名称】
     * @return 结果
     */
    public int updateDbPutQuestions(DbPutQuestions dbPutQuestions);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbPutQuestionsByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbPutQuestionsById(Long id);
}
