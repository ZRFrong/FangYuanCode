package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.page.PageConf;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbProblemMapper;
import com.ruoyi.system.domain.DbProblem;
import com.ruoyi.fangyuanapi.service.IDbProblemService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zheng
 * @date 2021-01-18
 */
@Service
public class DbProblemServiceImpl implements IDbProblemService 
{
    @Autowired
    private DbProblemMapper dbProblemMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    @Transactional
    public Map<String,Object> selectDbProblemById(Long id)
    {
        Map<String,Object> result = dbProblemMapper.selectDbProblemAnswerTextById(id);
        dbProblemMapper.updateProblemHot(id);
        return result;
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbProblem 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DbProblem> selectDbProblemList(DbProblem dbProblem)
    {
        return dbProblemMapper.selectDbProblemList(dbProblem);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbProblem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDbProblem(DbProblem dbProblem)
    {
        return dbProblemMapper.insertDbProblem(dbProblem);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbProblem 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDbProblem(DbProblem dbProblem)
    {
        return dbProblemMapper.updateDbProblem(dbProblem);
    }

    /**
     * 删除【请填写功能名称】对象
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbProblemByIds(String ids)
    {
        return dbProblemMapper.deleteDbProblemByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除问题记录信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbProblemById(Long id)
    {
        return dbProblemMapper.deleteDbProblemById(id);
    }

    @Override
    public List<Map<String, Object>> getProblemListByType(Integer problemType, Integer currPage,Integer pageSize) {
        return dbProblemMapper.getProblemListByType(problemType,currPage,pageSize);
    }

    @Override
    public List<DbProblem> selectDbProblem(Integer currPage, Integer pageSize) {

        return dbProblemMapper.selectDbProblem(currPage,pageSize);
    }


}
