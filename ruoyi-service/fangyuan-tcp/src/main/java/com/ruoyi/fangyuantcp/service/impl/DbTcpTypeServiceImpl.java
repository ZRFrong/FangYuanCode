package com.ruoyi.fangyuantcp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.utils.TcpOrderTextConf;
import com.ruoyi.system.domain.*;
import com.ruoyi.fangyuantcp.utils.SendCodeUtils;
import com.ruoyi.fangyuantcp.mapper.DbStateRecordsMapper;
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
public class DbTcpTypeServiceImpl implements IDbTcpTypeService {
    @Autowired
    private DbTcpTypeMapper dbTcpTypeMapper;

    @Autowired
    private DbStateRecordsMapper dbStateRecordsMapper;


    @Autowired
    private DbEquipmentMapper dbEquipmentMapper;

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
    public int timingTongFengHand() {

        DbTcpClient dbTcpClient = new DbTcpClient();
        dbTcpClient.setIsOnline(0);
        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        int i1 = 0;
        for (DbTcpClient tcpClient : dbTcpClients) {
            DbEquipment dbEquipment = new DbEquipment();
            dbEquipment.setHeartbeatText(tcpClient.getHeartName());
            for (DbEquipment equipment : dbEquipmentMapper.selectDbEquipmentList(dbEquipment)) {
                int i = sendCodeUtils.sinceOrHandTongFeng(equipment);
                if (i != 0) {
                    i1 = 1;
                }
            }

        }

        return i1;
    }

    @Override
    public int timingTongFengType() {
        DbTcpClient dbTcpClient = new DbTcpClient();
        dbTcpClient.setIsOnline(0);
        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        int i1 = 0;
        for (DbTcpClient tcpClient : dbTcpClients) {

            DbEquipment dbEquipment = new DbEquipment();
            dbEquipment.setHeartbeatText(tcpClient.getHeartName());
            for (DbEquipment equipment : dbEquipmentMapper.selectDbEquipmentList(dbEquipment)) {
                int i = sendCodeUtils.timingTongFengType(equipment);
                if (i != 0) {
                    i1 = 1;
                }
            }
        }

        return 0;
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


    /*
     *
     * 状态记录查询   指定时间段，指定时间间隔
     *
     * */
    @Override
    public List<DbStateRecords> intervalState(Date startTime, Date endTime, Integer iNterval) {

        List<DbStateRecords> dbStateRecords = dbStateRecordsMapper.intervalState(startTime, endTime);
//        获取符合条件的数据的间隔秒数
        Long betweenTime = ((dbStateRecords.get(dbStateRecords.size() - 1).getDemandTime().getTime() - dbStateRecords.get(0).getDemandTime().getTime()) / 1000);
//        根据根据数据长度判断每条数据的时间间隔
        Long i=  (betweenTime/dbStateRecords.size());




        return null;
    }

    @Override
    public void timingType() {
        DbTcpType dbTcpType = new DbTcpType();
        List<DbTcpType> dbTcpTypes = selectDbTcpTypeList(dbTcpType);
        DbOperationVo dbOperationVo = new DbOperationVo();
        List<DbOperationVo> list = new ArrayList<>();
        if (!dbTcpTypes.isEmpty()) {
            for (DbTcpType tcpType : dbTcpTypes) {
                String[] split = tcpType.getHeartName().split("_");
                dbOperationVo.setHeartName(split[0]);
                dbOperationVo.setFacility(split[1]);
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
