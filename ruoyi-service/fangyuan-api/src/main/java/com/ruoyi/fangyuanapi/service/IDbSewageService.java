package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbSewage;

import java.util.List;

/**
 * 水肥Service接口
 * 
 * @author zheng
 * @date 2020-11-23
 */
public interface IDbSewageService 
{
    /**
     * 查询水肥
     * 
     * @param id 水肥ID
     * @return 水肥.
     *
     */
    public DbSewage selectDbSewageById(Long id);

    /**
     * 查询水肥列表
     * 
     * @param dbSewage 水肥
     * @return 水肥集合
     */
    public List<DbSewage> selectDbSewageList(DbSewage dbSewage);

    /**
     * 新增水肥
     * 
     * @param dbSewage 水肥
     * @return 结果
     */
    public int insertDbSewage(DbSewage dbSewage);

    /**
     * 修改水肥
     * 
     * @param dbSewage 水肥
     * @return 结果
     */
    public int updateDbSewage(DbSewage dbSewage);

    /**
     * 批量删除水肥
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbSewageByIds(String ids);

    /**
     * 删除水肥信息
     * 
     * @param id 水肥ID
     * @return 结果
     */
    public int deleteDbSewageById(Long id);
}
