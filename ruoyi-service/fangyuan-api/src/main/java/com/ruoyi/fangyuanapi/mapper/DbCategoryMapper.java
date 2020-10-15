package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * db_categoryMapper接口
 * 
 * @author zheng
 * @date 2020-09-30
 */

@Mapper
public interface DbCategoryMapper 
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
     * 删除db_category
     * 
     * @param id db_categoryID
     * @return 结果
     */
    public int deleteDbCategoryById(Long id);

    /**
     * 批量删除db_category
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbCategoryByIds(String[] ids);

    @Select("select *  from db_category ")
    List<DbCategory> selectDbCategoryAll();
}
