package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;

import com.ruoyi.fangyuanapi.service.IDbCropInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbCropInfoMapper;
import com.ruoyi.system.domain.DbCropInfo;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author zheng
 * @date 2021-06-08
 */
@Service
public class DbCropInfoServiceImpl implements IDbCropInfoService
{
    @Autowired
    private DbCropInfoMapper dbCropInfoMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public DbCropInfo selectDbCropInfoById(Long id)
    {
        return dbCropInfoMapper.selectDbCropInfoById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbCropInfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DbCropInfo> selectDbCropInfoList(DbCropInfo dbCropInfo)
    {
        return dbCropInfoMapper.selectDbCropInfoList(dbCropInfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbCropInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDbCropInfo(DbCropInfo dbCropInfo)
    {
        return dbCropInfoMapper.insertDbCropInfo(dbCropInfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbCropInfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateDbCropInfo(DbCropInfo dbCropInfo)
    {
        return dbCropInfoMapper.updateDbCropInfo(dbCropInfo);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbCropInfoByIds(String ids)
    {
        return dbCropInfoMapper.deleteDbCropInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDbCropInfoById(Long id)
    {
        return dbCropInfoMapper.deleteDbCropInfoById(id);
    }

    @Override
    public DbCropInfo selectDbCropInfoByTagId(Long landId) {
        return dbCropInfoMapper.selectDbCropInfoByTagId(landId);
    }
}
