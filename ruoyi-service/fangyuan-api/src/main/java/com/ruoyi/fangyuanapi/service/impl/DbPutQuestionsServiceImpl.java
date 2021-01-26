package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbPutQuestionsMapper;
import com.ruoyi.system.domain.DbPutQuestions;
import com.ruoyi.fangyuanapi.service.IDbPutQuestionsService;
import com.ruoyi.common.core.text.Convert;

/**
 * 提问Service业务层处理
 * 
 * @author zheng
 * @date 2021-01-18
 */
@Service
public class DbPutQuestionsServiceImpl implements IDbPutQuestionsService 
{
    @Autowired
    private DbPutQuestionsMapper dbPutQuestionsMapper;

    /**
     * 查询提问
     * 
     * @param id 提问ID
     * @return 提问
     */
    @Override
    public DbPutQuestions selectDbPutQuestionsById(Long id)
    {
        return dbPutQuestionsMapper.selectDbPutQuestionsById(id);
    }

    /**
     * 查询提问列表
     * 
     * @param dbPutQuestions 提问
     * @return 提问
     */
    @Override
    public List<DbPutQuestions> selectDbPutQuestionsList(DbPutQuestions dbPutQuestions)
    {
        return dbPutQuestionsMapper.selectDbPutQuestionsList(dbPutQuestions);
    }

    /**
     * 新增提问
     * 
     * @param dbPutQuestions 提问
     * @return 结果
     */
    @Override
    public int insertDbPutQuestions(DbPutQuestions dbPutQuestions)
    {
        dbPutQuestions.setCreateTime(DateUtils.getNowDate());
        return dbPutQuestionsMapper.insertDbPutQuestions(dbPutQuestions);
    }

    /**
     * 修改提问
     * 
     * @param dbPutQuestions 提问
     * @return 结果
     */
    @Override
    public int updateDbPutQuestions(DbPutQuestions dbPutQuestions)
    {

        dbPutQuestions.setUpdateTime(DateUtils.getNowDate());
        return dbPutQuestionsMapper.updateDbPutQuestions(dbPutQuestions);
    }

    /**
     * 删除提问对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbPutQuestionsByIds(String ids)
    {
        return dbPutQuestionsMapper.deleteDbPutQuestionsByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除提问信息
     * 
     * @param id 提问ID
     * @return 结果
     */
    public int deleteDbPutQuestionsById(Long id)
    {
        return dbPutQuestionsMapper.deleteDbPutQuestionsById(id);
    }
}
