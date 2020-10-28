package com.ruoyi.fangyuantcp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ruoyi.system.domain.DbStateRecords;
import com.ruoyi.fangyuantcp.utils.SendCodeUtils;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.fangyuantcp.mapper.DbStateRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备状态Service业务层处理
 *
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbTcpTypeServiceImpl implements IDbTcpTypeService {
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
    public DbTcpType selectDbTcpTypeById(Long tcpTypeId) {
        return dbTcpTypeMapper.selectDbTcpTypeById(tcpTypeId);
    }

    /**
     * 查询设备状态列表
     *
     * @param dbTcpType 设备状态
     * @return 设备状态
     */
    @Override
    public List<DbTcpType> selectDbTcpTypeList(DbTcpType dbTcpType) {
        return dbTcpTypeMapper.selectDbTcpTypeList(dbTcpType);
    }

    /**
     * 新增设备状态
     *
     * @param dbTcpType 设备状态
     * @return 结果
     */
    @Override
    public int insertDbTcpType(DbTcpType dbTcpType) {
        return dbTcpTypeMapper.insertDbTcpType(dbTcpType);
    }

    /**
     * 修改设备状态
     *
     * @param dbTcpType 设备状态
     * @return 结果
     */
    @Override
    public int updateDbTcpType(DbTcpType dbTcpType) {
        return dbTcpTypeMapper.updateDbTcpType(dbTcpType);
    }

    /**
     * 删除设备状态对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbTcpTypeByIds(String ids) {
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
        dbTcpTypes.forEach(item -> insert(item));

    }

    @Override
    public void timingType() {
        DbTcpType dbTcpType = new DbTcpType();
        List<DbTcpType> dbTcpTypes = selectDbTcpTypeList(dbTcpType);
        DbOperationVo dbOperationVo = new DbOperationVo();
        List<DbOperationVo> list=new ArrayList<>();
        for (DbTcpType tcpType : dbTcpTypes) {
            String[] split = tcpType.getHeartName().split(",");
            dbOperationVo.setHeartName(split[0]);
            dbOperationVo.setFacility(split[1]);
            list.add(dbOperationVo);
        }
        int i = SendCodeUtils.timingState(list);

    }

    /*
     * 更新状态
     * */
    @Override
    public int updateOrInstart(DbTcpType dbTcpType) {
        List<DbTcpType> dbTcpTypes = selectDbTcpTypeList(dbTcpType);
        if (dbTcpTypes.size() == 0 && dbTcpTypes == null) {
//            新增
            dbTcpType.setUpdateTime(new Date());
//
            dbTcpType.setIsShow(0);
            return insertDbTcpType(dbTcpType);
        } else {
            dbTcpType.setUpdateTime(new Date());
            return updateDbTcpType(dbTcpType);
        }
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
    public int deleteDbTcpTypeById(Long tcpTypeId) {
        return dbTcpTypeMapper.deleteDbTcpTypeById(tcpTypeId);
    }
}
