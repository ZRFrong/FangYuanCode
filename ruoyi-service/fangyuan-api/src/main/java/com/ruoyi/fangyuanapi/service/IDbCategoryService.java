package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbCategory;

import java.util.List;

/**
 * db_categoryService接口
 * 
 * @author zheng
 * @date 2020-09-30
 */
public interface IDbCategoryService 
{
    /**
     * 查询db_category
     * 
     * @param id db_categoryID
     * @return db_category
     */
    public DbCategory selectDbCategoryById(Long id);

    /**
     * 查询db_category列表
     * 
     * @param dbCategory db_category
     * @return db_category集合
     */
    public List<DbCategory> selectDbCategoryList(DbCategory dbCategory);

    /**
     * 新增db_category
     * 
     * @param dbCategory db_category
     * @return 结果
     */
    public int insertDbCategory(DbCategory dbCategory);

    /**
     * 修改db_category
     * 
     * @param dbCategory db_category
     * @return 结果
     */
    public int updateDbCategory(DbCategory dbCategory);

    /**
     * 批量删除db_category
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbCategoryByIds(String ids);

    /**
     * 删除db_category信息
     * 
     * @param id db_categoryID
     * @return 结果
     */
    public int deleteDbCategoryById(Long id);

    /**
     * 查询所有
     * @return
     */
    List<DbCategory> selectDbCategoryAll();
}
