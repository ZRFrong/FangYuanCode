package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbFunctionDisplay;

import java.util.List;

/**
 * 页面功能显示Mapper接口
 * 
 * @author zheng
 * @date 2021-04-08
 */
public interface DbFunctionDisplayMapper 
{
    /**
     * 查询页面功能显示
     * 
     * @param id 页面功能显示ID
     * @return 页面功能显示
     */
    public DbFunctionDisplay selectDbFunctionDisplayById(Long id);

    /**
     * 查询页面功能显示列表
     * 
     * @param dbFunctionDisplay 页面功能显示
     * @return 页面功能显示集合
     */
    public List<DbFunctionDisplay> selectDbFunctionDisplayList(DbFunctionDisplay dbFunctionDisplay);

    /**
     * 新增页面功能显示
     * 
     * @param dbFunctionDisplay 页面功能显示
     * @return 结果
     */
    public int insertDbFunctionDisplay(DbFunctionDisplay dbFunctionDisplay);

    /**
     * 修改页面功能显示
     * 
     * @param dbFunctionDisplay 页面功能显示
     * @return 结果
     */
    public int updateDbFunctionDisplay(DbFunctionDisplay dbFunctionDisplay);

    /**
     * 删除页面功能显示
     * 
     * @param id 页面功能显示ID
     * @return 结果
     */
    public int deleteDbFunctionDisplayById(Long id);

    /**
     * 批量删除页面功能显示
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbFunctionDisplayByIds(String[] ids);
}
