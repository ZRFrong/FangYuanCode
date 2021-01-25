package com.ruoyi.fangyuantcp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.utils.DateUtilLong;
import com.ruoyi.fangyuantcp.utils.TcpOrderTextConf;
import com.ruoyi.system.domain.*;
import com.ruoyi.fangyuantcp.utils.SendCodeUtils;
import com.ruoyi.fangyuantcp.mapper.DbStateRecordsMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpTypeMapper;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 设备状态Service业务层处理
 *
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
@Log4j2
public class DbTcpTypeServiceImpl implements IDbTcpTypeService {
    @Autowired
    private DbTcpTypeMapper dbTcpTypeMapper;

    @Autowired
    private DbStateRecordsMapper dbStateRecordsMapper;


    @Autowired
    private DbEquipmentMapper1 dbEquipmentMapper;

    @Autowired
    private RedisUtils redisUtils;


    private SendCodeUtils sendCodeUtils = new SendCodeUtils();

    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;

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
     * 信息状态同步
     * */
    @Override
    public void curingTypeTiming() {
        DbTcpType dbTcpType = new DbTcpType();
        List<DbTcpType> dbTcpTypes = selectDbTcpTypeList(dbTcpType);
        dbTcpTypes.forEach(item -> insert(item));

    }

    /*
     * 通风手动自动监测
     * */
    @Override
    public void timingTongFengHand() {
        DbTcpClient dbTcpClient = new DbTcpClient();
        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        DbOperationVo dbOperationVo = new DbOperationVo();
        List<DbOperationVo> list = new ArrayList<>();
        if (!dbTcpClients.isEmpty()) {
            for (DbTcpClient tcpClient : dbTcpClients) {
                dbOperationVo.setHeartName(tcpClient.getHeartName());
                dbOperationVo.setFacility("01");
                dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFeng);
                list.add(dbOperationVo);
            }
            int i = SendCodeUtils.timingTongFengHand(list);
        }
    }

    @Override
    public void timingTongFengType() {
        DbTcpClient dbTcpClient = new DbTcpClient();
        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        DbOperationVo dbOperationVo = new DbOperationVo();
        List<DbOperationVo> list = new ArrayList<>();
        if (!dbTcpClients.isEmpty()) {
            for (DbTcpClient tcpClient : dbTcpClients) {
                dbOperationVo.setHeartName(tcpClient.getHeartName());
                dbOperationVo.setFacility("01");
                dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFengType);
                list.add(dbOperationVo);
            }
            int i = SendCodeUtils.timingTongFengTypeList(list);
        }

    }

    @Override
    public void updateByHeartbeatOpen(String heartName) {
        heartName="%"+heartName+"%";
        dbTcpTypeMapper.updateByHeartbeatOpen(heartName);
    }

    @Override
    public void updateByHeartbeat(String heartbeatText) {
        heartbeatText="%"+heartbeatText+"%";
        dbTcpTypeMapper.updateByHeartbeat(heartbeatText);
    }

    @Override
    public void deleteTimingType() {
        /*
         * 查询所有列表检索出需要过期的状态信息
         * */
        DbTcpType dbTcpType = new DbTcpType();
        List<DbTcpType> list = dbTcpTypeMapper.selectDbTcpTypeList(dbTcpType);
        list.forEach(itm -> {
            Long minuteDiff = DateUtilLong.getMinuteDiff(itm.getUpdateTime(), new Date());
            if (minuteDiff > 10) {
                itm.setIsShow(1);
                dbTcpTypeMapper.updateDbTcpType(itm);
            }
        });

    }

    @Override
    public void deleteByHeartName(String heartbeatText) {
        dbTcpTypeMapper.deleteByHeartName(heartbeatText);
    }

    @Override
    public int operateTongFengHand(String heartbeatText, String equipmentNo, Integer i) {
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(heartbeatText);
        dbEquipment.setEquipmentNo(Integer.parseInt(equipmentNo));
        int i1 = sendCodeUtils.operateTongFengHand(dbEquipment, i);
        return i1;
    }

    @Override
    public int operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String temp) {
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(heartbeatText);
        dbEquipment.setEquipmentNo(Integer.parseInt(equipmentNo));
        int i1 = sendCodeUtils.operateTongFengType(dbEquipment, i, temp);
        return i1;
    }


    @Override
    public void timingTypeOnly(DbTcpClient dbTcpClient) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        List<DbOperationVo> list = new ArrayList<>();
        dbOperationVo.setHeartName(dbTcpClient.getHeartName());
        dbOperationVo.setFacility("01");
        dbOperationVo.setOperationText(TcpOrderTextConf.stateSave);
        list.add(dbOperationVo);
        SendCodeUtils.timingState(list);
    }

    /*
     *
     * 状态记录查询   指定时间段，指定时间间隔
     *
     * */
    @Override
    public List<DbStateRecords> intervalState(Date startTime, Date endTime, String iNterval, String hearName) {


        List<DbStateRecords> dbStateRecords = dbStateRecordsMapper.intervalState(startTime, endTime, hearName);
        List<DbStateRecords> dbStateRecords1 = new ArrayList<>();
        for (int i = 0; i < dbStateRecords.size(); i++) {
            if (i % (Integer.parseInt(iNterval) * (6)) == 0) {
                dbStateRecords1.add(dbStateRecords.get(i));
            }

        }


        return dbStateRecords1;
    }

    @Override
    public void timingType() {
        DbTcpClient dbTcpClient = new DbTcpClient();
        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        DbOperationVo dbOperationVo = new DbOperationVo();
        List<DbOperationVo> list = new ArrayList<>();
        if (!dbTcpClients.isEmpty()) {
            for (DbTcpClient tcpClient : dbTcpClients) {
                dbOperationVo.setHeartName(tcpClient.getHeartName());
                dbOperationVo.setFacility("01");
                dbOperationVo.setOperationText(TcpOrderTextConf.stateSave);
                list.add(dbOperationVo);
            }
            int i = SendCodeUtils.timingState(list);
        }

    }



    /*
     * 更新状态
     * */
    @Override
    public int updateOrInstart(DbTcpType dbTcpType) {
//            新增
        dbTcpType.setUpdateTime(new Date());
        return updateDbTcpType(dbTcpType);
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
