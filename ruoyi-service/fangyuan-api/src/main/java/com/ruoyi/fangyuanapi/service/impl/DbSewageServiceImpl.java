package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbSewage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbSewageMapper;
import com.ruoyi.fangyuanapi.service.IDbSewageService;
import com.ruoyi.common.core.text.Convert;

/**
 * 水肥Service业务层处理
 * 
 * @author zheng
 * @date 2020-11-23
 */
@Service
public class DbSewageServiceImpl implements IDbSewageService 
{
    @Autowired
    private DbSewageMapper dbSewageMapper;

    /**
     * 查询水肥
     * 
     * @param id 水肥ID
     * @return 水肥
     */
    @Override
    public DbSewage selectDbSewageById(Long id)
    {
        return dbSewageMapper.selectDbSewageById(id);
    }

    /**
     * 查询水肥列表
     * 
     * @param dbSewage 水肥
     * @return 水肥
     */
    @Override
    public List<DbSewage> selectDbSewageList(DbSewage dbSewage)
    {
        return dbSewageMapper.selectDbSewageList(dbSewage);
    }

    /**
     * 新增水肥
     * 
     * @param dbSewage 水肥
     * @return 结果
     */
    @Override
    public int insertDbSewage(DbSewage dbSewage)
    {
        dbSewage.setCreateTime(DateUtils.getNowDate());
        return dbSewageMapper.insertDbSewage(dbSewage);
    }

    /**
     * 修改水肥
     * 
     * @param dbSewage 水肥
     * @return 结果
     */
    @Override
    public int updateDbSewage(DbSewage dbSewage)
    {
        dbSewage.setUpdateTime(DateUtils.getNowDate());
        return dbSewageMapper.updateDbSewage(dbSewage);
    }

    /**
     * 删除水肥对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbSewageByIds(String ids)
    {
        return dbSewageMapper.deleteDbSewageByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除水肥信息
     * 
     * @param id 水肥ID
     * @return 结果
     */
    public int deleteDbSewageById(Long id)
    {
        return dbSewageMapper.deleteDbSewageById(id);
    }
}



