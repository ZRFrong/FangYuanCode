package com.ruoyi.fangyuanapi.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.fangyuanapi.mapper.DbCategoryMapper;
import com.ruoyi.fangyuanapi.service.IDbCategoryService;
import com.ruoyi.system.domain.DbCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * db_categoryService业务层处理
 * 
 * @author zheng
 * @date 2020-09-30
 */
@Service
public class DbCategoryServiceImpl implements IDbCategoryService
{
    @Autowired
    private DbCategoryMapper dbCategoryMapper;

    /**
     * 查询db_category
     * 
     * @param id db_categoryID
     * @return db_category
     */
    @Override
    public DbCategory selectDbCategoryById(Long id)
    {
        return dbCategoryMapper.selectDbCategoryById(id);
    }

    /**
     * 查询db_category列表
     * 
     * @param dbCategory db_category
     * @return db_category
     */
    @Override
    public List<DbCategory> selectDbCategoryList(DbCategory dbCategory)
    {
        return dbCategoryMapper.selectDbCategoryList(dbCategory);
    }

    /**
     * 新增db_category
     * 
     * @param dbCategory db_category
     * @return 结果
     */
    @Override
    public int insertDbCategory(DbCategory dbCategory)
    {
        return dbCategoryMapper.insertDbCategory(dbCategory);
    }

    /**
     * 修改db_category
     * 
     * @param dbCategory db_category
     * @return 结果
     */
    @Override
    public int updateDbCategory(DbCategory dbCategory)
    {
        return dbCategoryMapper.updateDbCategory(dbCategory);
    }

    /**
     * 删除db_category对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbCategoryByIds(String ids)
    {
        return dbCategoryMapper.deleteDbCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除db_category信息
     * 
     * @param id db_categoryID
     * @return 结果
     */
    public int deleteDbCategoryById(Long id)
    {
        return dbCategoryMapper.deleteDbCategoryById(id);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<DbCategory> selectDbCategoryAll() {
        List<DbCategory> categories  =  dbCategoryMapper.selectDbCategoryAll();
        return categories;
    }
}
