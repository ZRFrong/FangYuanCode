package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbInformationMapper;
import com.ruoyi.system.domain.DbInformation;
import com.ruoyi.fangyuanapi.service.IDbInformationService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zheng
 * @date 2021-04-08
 */
@Service
public class DbInformationServiceImpl implements IDbInformationService 
{
    @Autowired
    private DbInformationMapper dbInformationMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public DbInformation selectDbInformationById(Long id)
    {
        return dbInformationMapper.selectDbInformationById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbInformation 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DbInformation> selectDbInformationList(DbInformation dbInformation)
    {
        return dbInformationMapper.selectDbInformationList(dbInformation);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbInformation 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDbInformation(DbInformation dbInformation)
    {
        dbInformation.setCreateTime(DateUtils.getNowDate());
        return dbInformationMapper.insertDbInformation(dbInformation);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbInformation 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDbInformation(DbInformation dbInformation)
    {
        dbInformation.setUpdateTime(DateUtils.getNowDate());
        return dbInformationMapper.updateDbInformation(dbInformation);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbInformationByIds(String ids)
    {
        return dbInformationMapper.deleteDbInformationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteDbInformationById(Long id)
    {
        return dbInformationMapper.deleteDbInformationById(id);
    }
}
