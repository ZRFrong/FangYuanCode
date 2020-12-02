package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;

import com.ruoyi.system.domain.DbAbnormalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbAbnormalInfoMapper;
import com.ruoyi.fangyuanapi.service.IDbAbnormalInfoService;
import com.ruoyi.common.core.text.Convert;

/**
 * 报警信息Service业务层处理
 * 
 * @author zheng
 * @date 2020-12-02
 */
@Service
public class DbAbnormalInfoServiceImpl implements IDbAbnormalInfoService 
{
    @Autowired
    private DbAbnormalInfoMapper dbAbnormalInfoMapper;

    /**
     * 查询报警信息
     * 
     * @param id 报警信息ID
     * @return 报警信息
     */
    @Override
    public DbAbnormalInfo selectDbAbnormalInfoById(Long id)
    {
        return dbAbnormalInfoMapper.selectDbAbnormalInfoById(id);
    }

    /**
     * 查询报警信息列表
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 报警信息
     */
    @Override
    public List<DbAbnormalInfo> selectDbAbnormalInfoList(DbAbnormalInfo dbAbnormalInfo)
    {
        return dbAbnormalInfoMapper.selectDbAbnormalInfoList(dbAbnormalInfo);
    }

    /**
     * 新增报警信息
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 结果
     */
    @Override
    public int insertDbAbnormalInfo(DbAbnormalInfo dbAbnormalInfo)
    {
        return dbAbnormalInfoMapper.insertDbAbnormalInfo(dbAbnormalInfo);
    }

    /**
     * 修改报警信息
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 结果
     */
    @Override
    public int updateDbAbnormalInfo(DbAbnormalInfo dbAbnormalInfo)
    {
        return dbAbnormalInfoMapper.updateDbAbnormalInfo(dbAbnormalInfo);
    }

    /**
     * 删除报警信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbAbnormalInfoByIds(String ids)
    {
        return dbAbnormalInfoMapper.deleteDbAbnormalInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除报警信息信息
     * 
     * @param id 报警信息ID
     * @return 结果
     */
    public int deleteDbAbnormalInfoById(Long id)
    {
        return dbAbnormalInfoMapper.deleteDbAbnormalInfoById(id);
    }
}
