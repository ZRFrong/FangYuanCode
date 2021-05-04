package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbEquipmentComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentComponentMapper;
import com.ruoyi.fangyuanapi.service.IDbEquipmentComponentService;
import com.ruoyi.common.core.text.Convert;

/**
 * 版本加功能Service业务层处理
 * 
 * @author zheng
 * @date 2021-04-08
 */
@Service
public class DbEquipmentComponentServiceImpl implements IDbEquipmentComponentService 
{
    @Autowired
    private DbEquipmentComponentMapper dbEquipmentComponentMapper;

    /**
     * 查询版本加功能
     * 
     * @param id 版本加功能ID
     * @return 版本加功能
     */
    @Override
    public DbEquipmentComponent selectDbEquipmentComponentById(Long id)
    {
        return dbEquipmentComponentMapper.selectDbEquipmentComponentById(id);
    }

    /**
     * 查询版本加功能列表
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 版本加功能
     */
    @Override
    public List<DbEquipmentComponent> selectDbEquipmentComponentList(DbEquipmentComponent dbEquipmentComponent)
    {
        return dbEquipmentComponentMapper.selectDbEquipmentComponentList(dbEquipmentComponent);
    }

    /**
     * 新增版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    @Override
    public int insertDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent)
    {
        dbEquipmentComponent.setCreateTime(DateUtils.getNowDate());
        return dbEquipmentComponentMapper.insertDbEquipmentComponent(dbEquipmentComponent);
    }

    /**
     * 修改版本加功能
     * 
     * @param dbEquipmentComponent 版本加功能
     * @return 结果
     */
    @Override
    public int updateDbEquipmentComponent(DbEquipmentComponent dbEquipmentComponent)
    {
        return dbEquipmentComponentMapper.updateDbEquipmentComponent(dbEquipmentComponent);
    }

    /**
     * 删除版本加功能对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentComponentByIds(String ids)
    {
        return dbEquipmentComponentMapper.deleteDbEquipmentComponentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除版本加功能信息
     * 
     * @param id 版本加功能ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentComponentById(Long id)
    {
        return dbEquipmentComponentMapper.deleteDbEquipmentComponentById(id);
    }

    @Override
    public List<DbEquipmentComponent> selectDbEquipmentComponentByIds(String[] split) {

        return dbEquipmentComponentMapper.selectDbEquipmentComponentByIds(split);
    }
}
