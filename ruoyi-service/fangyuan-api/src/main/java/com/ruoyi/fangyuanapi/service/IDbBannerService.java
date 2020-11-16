package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbBanner;

import java.util.List;

/**
 * 轮播图Service接口
 * 
 * @author zheng
 * @date 2020-11-12
 */
public interface IDbBannerService 
{
    /**
     * 查询轮播图
     * 
     * @param id 轮播图ID
     * @return 轮播图
     */
    public DbBanner selectDbBannerById(Long id);

    /**
     * 查询轮播图列表
     * 
     * @param dbBanner 轮播图
     * @return 轮播图集合
     */
    public List<DbBanner> selectDbBannerList(DbBanner dbBanner);

    /**
     * 新增轮播图
     * 
     * @param dbBanner 轮播图
     * @return 结果
     */
    public int insertDbBanner(DbBanner dbBanner);

    /**
     * 修改轮播图
     * 
     * @param dbBanner 轮播图
     * @return 结果
     */
    public int updateDbBanner(DbBanner dbBanner);

    /**
     * 批量删除轮播图
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbBannerByIds(String ids);

    /**
     * 删除轮播图信息
     * 
     * @param id 轮播图ID
     * @return 结果
     */
    public int deleteDbBannerById(Long id);

    /**
     * 获取首页轮播图
     * @return
     */
    List<DbBanner> getBannerList(Integer bannerType);

}
