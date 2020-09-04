package com.ruoyi.fangyuanapi.service;

import com.ruoyi.fangyuanapi.domain.DbUserDynamic;
import java.util.List;

/**
 * 动态Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbUserDynamicService 
{
    /**
     * 查询动态
     * 
     * @param id 动态ID
     * @return 动态
     */
    public DbUserDynamic selectDbUserDynamicById(Long id);

    /**
     * 查询动态列表
     * 
     * @param dbUserDynamic 动态
     * @return 动态集合
     */
    public List<DbUserDynamic> selectDbUserDynamicList(DbUserDynamic dbUserDynamic);

    /**
     * 新增动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    public int insertDbUserDynamic(DbUserDynamic dbUserDynamic);

    /**
     * 修改动态
     * 
     * @param dbUserDynamic 动态
     * @return 结果
     */
    public int updateDbUserDynamic(DbUserDynamic dbUserDynamic);

    /**
     * 批量删除动态
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbUserDynamicByIds(String ids);

    /**
     * 删除动态信息
     * 
     * @param id 动态ID
     * @return 结果
     */
    public int deleteDbUserDynamicById(Long id);
}
