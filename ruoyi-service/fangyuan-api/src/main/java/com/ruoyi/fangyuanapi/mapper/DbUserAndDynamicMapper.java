package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbUserAndDynamic;
import java.util.List;

/**
 * 前台用户和动态中间Mapper接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface DbUserAndDynamicMapper 
{
    /**
     * 查询前台用户和动态中间
     * 
     * @param id 前台用户和动态中间ID
     * @return 前台用户和动态中间
     */
    public DbUserAndDynamic selectDbUserAndDynamicById(Long id);

    /**
     * 查询前台用户和动态中间列表
     * 
     * @param dbUserAndDynamic 前台用户和动态中间
     * @return 前台用户和动态中间集合
     */
    public List<DbUserAndDynamic> selectDbUserAndDynamicList(DbUserAndDynamic dbUserAndDynamic);

    /**
     * 新增前台用户和动态中间
     * 
     * @param dbUserAndDynamic 前台用户和动态中间
     * @return 结果
     */
    public int insertDbUserAndDynamic(DbUserAndDynamic dbUserAndDynamic);

    /**
     * 修改前台用户和动态中间
     * 
     * @param dbUserAndDynamic 前台用户和动态中间
     * @return 结果
     */
    public int updateDbUserAndDynamic(DbUserAndDynamic dbUserAndDynamic);

    /**
     * 删除前台用户和动态中间
     * 
     * @param id 前台用户和动态中间ID
     * @return 结果
     */
    public int deleteDbUserAndDynamicById(Long id);

    /**
     * 批量删除前台用户和动态中间
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbUserAndDynamicByIds(String[] ids);

    List<Long> selectDbUserAndDynamicByUserId(Long id);

    Integer selectDbUserAndDynamicCountByUserId(Long id);
}
