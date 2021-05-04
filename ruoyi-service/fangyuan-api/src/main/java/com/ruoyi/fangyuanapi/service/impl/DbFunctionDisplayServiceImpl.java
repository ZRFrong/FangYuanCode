package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbFunctionDisplay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbFunctionDisplayMapper;
import com.ruoyi.fangyuanapi.service.IDbFunctionDisplayService;
import com.ruoyi.common.core.text.Convert;

/**
 * 页面功能显示Service业务层处理
 * 
 * @author zheng
 * @date 2021-04-08
 */
@Service
public class DbFunctionDisplayServiceImpl implements IDbFunctionDisplayService 
{
    @Autowired
    private DbFunctionDisplayMapper dbFunctionDisplayMapper;

    /**
     * 查询页面功能显示
     * 
     * @param id 页面功能显示ID
     * @return 页面功能显示
     */
    @Override
    public DbFunctionDisplay selectDbFunctionDisplayById(Long id)
    {
        return dbFunctionDisplayMapper.selectDbFunctionDisplayById(id);
    }

    /**
     * 查询页面功能显示列表
     * 
     * @param dbFunctionDisplay 页面功能显示
     * @return 页面功能显示
     */
    @Override
    public List<DbFunctionDisplay> selectDbFunctionDisplayList(DbFunctionDisplay dbFunctionDisplay)
    {
        return dbFunctionDisplayMapper.selectDbFunctionDisplayList(dbFunctionDisplay);
    }

    /**
     * 新增页面功能显示
     * 
     * @param dbFunctionDisplay 页面功能显示
     * @return 结果
     */
    @Override
    public int insertDbFunctionDisplay(DbFunctionDisplay dbFunctionDisplay)
    {
        dbFunctionDisplay.setCreateTime(DateUtils.getNowDate());
        return dbFunctionDisplayMapper.insertDbFunctionDisplay(dbFunctionDisplay);
    }

    /**
     * 修改页面功能显示
     * 
     * @param dbFunctionDisplay 页面功能显示
     * @return 结果
     */
    @Override
    public int updateDbFunctionDisplay(DbFunctionDisplay dbFunctionDisplay)
    {
        dbFunctionDisplay.setUpdateTime(DateUtils.getNowDate());
        return dbFunctionDisplayMapper.updateDbFunctionDisplay(dbFunctionDisplay);
    }

    /**
     * 删除页面功能显示对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbFunctionDisplayByIds(String ids)
    {
        return dbFunctionDisplayMapper.deleteDbFunctionDisplayByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除页面功能显示信息
     * 
     * @param id 页面功能显示ID
     * @return 结果
     */
    public int deleteDbFunctionDisplayById(Long id)
    {
        return dbFunctionDisplayMapper.deleteDbFunctionDisplayById(id);
    }
}
