package com.ruoyi.fangyuantcp.service.impl;

import com.ruoyi.common.core.text.Convert;
import com.ruoyi.fangyuantcp.aspect.FeedbackIntercept;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.system.domain.DbEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备Service业务层处理
 *
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbEquipmentServiceImpl implements IDbEquipmentService {
    @Autowired
    private DbEquipmentMapper1 dbEquipmentMapper;


    /**
     * 查询设备
     *
     * @param equipmentId 设备ID
     * @return 设备
     */
    @Override
    public DbEquipment selectDbEquipmentById(Long equipmentId) {
        return dbEquipmentMapper.selectDbEquipmentById(equipmentId);
    }

    /**
     * 查询设备列表
     *
     * @param dbEquipment 设备
     * @return 设备
     */
    @Override
    public List<DbEquipment> selectDbEquipmentList(DbEquipment dbEquipment) {
        return dbEquipmentMapper.selectDbEquipmentList(dbEquipment);
    }

    /**
     * 新增设备
     *
     * @param dbEquipment 设备
     * @return 结果
     */
    @Override
    public int insertDbEquipment(DbEquipment dbEquipment) {
        //dbEquipment.setCreateTime(DateUtils.getNowDate());
        return dbEquipmentMapper.insertDbEquipment(dbEquipment);
    }

    /**
     * 修改设备
     *
     * @param dbEquipment 设备
     * @return 结果
     */
    @Override
    public int updateDbEquipment(DbEquipment dbEquipment) {
        //dbEquipment.setUpdateTime(DateUtils.getNowDate());
        return dbEquipmentMapper.updateDbEquipment(dbEquipment);
    }

    @Override
    @FeedbackIntercept()
    public DbEquipment updateDbEquipmentFeedback(DbEquipment dbEquipment) {
        //dbEquipment.setUpdateTime(DateUtils.getNowDate());
        DbEquipment dbEquipment1 = dbEquipmentMapper.selectDbEquipmentById(dbEquipment.getEquipmentId());
        dbEquipmentMapper.updateDbEquipment(dbEquipment);
        return dbEquipment1;
    }

    /**
     * 删除设备对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentByIds(String ids) {
        return dbEquipmentMapper.deleteDbEquipmentByIds(Convert.toStrArray(ids));
    }


    /**
     * 删除设备信息
     *
     * @param equipmentId 设备ID
     * @return 结果
     */
    public int deleteDbEquipmentById(Long equipmentId) {
        return dbEquipmentMapper.deleteDbEquipmentById(equipmentId);
    }
}
