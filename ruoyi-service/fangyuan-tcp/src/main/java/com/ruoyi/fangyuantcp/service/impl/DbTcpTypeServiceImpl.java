package com.ruoyi.fangyuantcp.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.json.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.fangyuantcp.domain.DbStateRecords;
import com.ruoyi.fangyuantcp.mapper.DbStateRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.fangyuantcp.domain.DbTcpType;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备状态Service业务层处理
 * 
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbTcpTypeServiceImpl implements IDbTcpTypeService 
{
    @Autowired
    private DbTcpTypeMapper dbTcpTypeMapper;

    @Autowired
    private DbStateRecordsMapper dbStateRecordsMapper;

    /**
     * 查询设备状态
     * 
     * @param tcpTypeId 设备状态ID
     * @return 设备状态
     */
    @Override
    public DbTcpType selectDbTcpTypeById(Long tcpTypeId)
    {
        return dbTcpTypeMapper.selectDbTcpTypeById(tcpTypeId);
    }

    /**
     * 查询设备状态列表
     * 
     * @param dbTcpType 设备状态
     * @return 设备状态
     */
    @Override
    public List<DbTcpType> selectDbTcpTypeList(DbTcpType dbTcpType)
    {
        return dbTcpTypeMapper.selectDbTcpTypeList(dbTcpType);
    }

    /**
     * 新增设备状态
     * 
     * @param dbTcpType 设备状态
     * @return 结果
     */
    @Override
    public int insertDbTcpType(DbTcpType dbTcpType)
    {
        return dbTcpTypeMapper.insertDbTcpType(dbTcpType);
    }

    /**
     * 修改设备状态
     * 
     * @param dbTcpType 设备状态
     * @return 结果
     */
    @Override
    public int updateDbTcpType(DbTcpType dbTcpType)
    {
        return dbTcpTypeMapper.updateDbTcpType(dbTcpType);
    }

    /**
     * 删除设备状态对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbTcpTypeByIds(String ids)
    {
        return dbTcpTypeMapper.deleteDbTcpTypeByIds(Convert.toStrArray(ids));
    }

    /*
    *
    * 信息状态同步
    * */
    @Override
    public void curingTypeTiming() {
        DbTcpType dbTcpType = new DbTcpType();
        List<DbTcpType> dbTcpTypes = selectDbTcpTypeList(dbTcpType);
        dbTcpTypes.forEach(item->insert(item));

    }

    private void insert(DbTcpType item) {
        DbStateRecords dbStateRecords = new DbStateRecords();
        dbStateRecords.setCodeOnly(item.getHeartName());
        dbStateRecords.setDemandTime(new Date());
        dbStateRecords.setStateJson(com.alibaba.fastjson.JSON.toJSONString(item));
        int i = dbStateRecordsMapper.insertDbStateRecords(dbStateRecords);
    }


    /**
     * 删除设备状态信息
     * 
     * @param tcpTypeId 设备状态ID
     * @return 结果
     */
    public int deleteDbTcpTypeById(Long tcpTypeId)
    {
        return dbTcpTypeMapper.deleteDbTcpTypeById(tcpTypeId);
    }
}
