package com.ruoyi.fangyuanapi.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbBannerMapper;
import com.ruoyi.fangyuanapi.service.IDbBannerService;
import com.ruoyi.common.core.text.Convert;

import java.util.List;

/**
 * 轮播图Service业务层处理
 * 
 * @author zheng
 * @date 2020-11-12
 */
@Service
public class DbBannerServiceImpl implements IDbBannerService 
{
    @Autowired
    private DbBannerMapper dbBannerMapper;

    /**
     * 查询轮播图
     * 
     * @param id 轮播图ID
     * @return 轮播图
     */
    @Override
    public DbBanner selectDbBannerById(Long id)
    {
        return dbBannerMapper.selectDbBannerById(id);
    }

    /**
     * 查询轮播图列表
     * 
     * @param dbBanner 轮播图
     * @return 轮播图
     */
    @Override
    public List<DbBanner> selectDbBannerList(DbBanner dbBanner)
    {
        return dbBannerMapper.selectDbBannerList(dbBanner);
    }

    /**
     * 新增轮播图
     * 
     * @param dbBanner 轮播图
     * @return 结果
     */
    @Override
    public int insertDbBanner(DbBanner dbBanner)
    {
        dbBanner.setCreateTime(DateUtils.getNowDate());
        return dbBannerMapper.insertDbBanner(dbBanner);
    }

    /**
     * 修改轮播图
     * 
     * @param dbBanner 轮播图
     * @return 结果
     */
    @Override
    public int updateDbBanner(DbBanner dbBanner)
    {
        return dbBannerMapper.updateDbBanner(dbBanner);
    }

    /**
     * 删除轮播图对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbBannerByIds(String ids)
    {
        return dbBannerMapper.deleteDbBannerByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除轮播图信息
     * 
     * @param id 轮播图ID
     * @return 结果
     */
    public int deleteDbBannerById(Long id)
    {
        return dbBannerMapper.deleteDbBannerById(id);
    }

    @Override
    public List<DbBanner> getBannerList(Integer bannerType) {
        return dbBannerMapper.getBannerList(bannerType);
    }
}
