package com.ruoyi.fangyuanapi.service;

import com.ruoyi.fangyuanapi.domain.DbForward;
import java.util.List;

/**
 * 转发Service接口
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
public interface IDbForwardService 
{
    /**
     * 查询转发
     * 
     * @param id 转发ID
     * @return 转发
     */
    public DbForward selectDbForwardById(Long id);

    /**
     * 查询转发列表
     * 
     * @param dbForward 转发
     * @return 转发集合
     */
    public List<DbForward> selectDbForwardList(DbForward dbForward);

    /**
     * 新增转发
     * 
     * @param dbForward 转发
     * @return 结果
     */
    public int insertDbForward(DbForward dbForward);

    /**
     * 修改转发
     * 
     * @param dbForward 转发
     * @return 结果
     */
    public int updateDbForward(DbForward dbForward);

    /**
     * 批量删除转发
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbForwardByIds(String ids);

    /**
     * 删除转发信息
     * 
     * @param id 转发ID
     * @return 结果
     */
    public int deleteDbForwardById(Long id);
}
