package com.ruoyi.fangyuantcp.service.impl;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.fangyuantcp.utils.*;
import com.ruoyi.system.domain.*;
import com.ruoyi.fangyuantcp.mapper.DbStateRecordsMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
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
    private DbStateRecordsMapper dbStateRecordsMapper1;

    @Autowired
    private DbStateRecordsMapper dbStateRecordsMapper2;

    @Autowired
    private DbEquipmentMapper1 dbEquipmentMapper1;

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
    public void timingTongFengHand() throws ExecutionException, InterruptedException {
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
            SendCodeListUtils.queryIoListNoWait(list, OpcodeTextConf.OPCODE01);
        }

    }

    @Override
    public void timingTongFengType() throws ExecutionException, InterruptedException {
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
            SendCodeListUtils.queryIoListNoWait(list, OpcodeTextConf.OPCODE03);
        }

    }

    @Override
    public R stateAllQuery(List<DbOperationVo> dbOperationVo) throws ExecutionException, InterruptedException {
//      添加操作集
        List<DbOperationVo> dbOperationVoList = stateAll(dbOperationVo);


        //       根据心跳分组
        Map<String, List<DbOperationVo>> mps = dbOperationVoList.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
//         多个map依次执行（多线程）
        R r = SendCodeListUtils.queryIoList(mps, OpcodeTextConf.OPCODEEMPTY);


        return r;
    }

    private List<DbOperationVo> stateAll(List<DbOperationVo> dbOperationVo) {
        List<DbOperationVo> dbOperationVoList = new ArrayList<>();
        List<String> stateAllText = new ArrayList<>();
        stateAllText.add("01" + "," + "03," + TcpOrderTextConf.stateSave);
        stateAllText.add("01" + "," + "01," + TcpOrderTextConf.SinceOrhandTongFeng);
        stateAllText.add("01" + "," + "03," + TcpOrderTextConf.SinceOrhandTongFengType);
        stateAllText.add("01" + "," + "03," + TcpOrderTextConf.TaskOnline);
        for (String s : stateAllText) {
            for (DbOperationVo operationVo : dbOperationVo) {
                DbOperationVo dbOperationVo1 = new DbOperationVo();
                BeanUtils.copyProperties(operationVo, dbOperationVo1);
                dbOperationVo1.setOperationText(s);
                dbOperationVoList.add(dbOperationVo1);
            }
        }
        return dbOperationVoList;
    }


    @Override
    public void updateByHeartbeatOpen(String heartName) {
        heartName = "%" + heartName + "%";
        dbTcpTypeMapper.updateByHeartbeatOpen(heartName);
    }

    @Override
    public void updateByHeartbeat(String heartbeatText) {
        heartbeatText = "%" + heartbeatText + "%";
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
    public void timingTypeOnly(DbTcpClient dbTcpClient) throws ExecutionException, InterruptedException {
        DbOperationVo dbOperationVo = new DbOperationVo();
        List<DbOperationVo> list = new ArrayList<>();
        dbOperationVo.setHeartName(dbTcpClient.getHeartName());
        dbOperationVo.setFacility("01");
        dbOperationVo.setOperationText(TcpOrderTextConf.stateSave);
        list.add(dbOperationVo);
        //       根据心跳分组
        Map<String, List<DbOperationVo>> mps = list.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
        SendCodeListUtils.queryIoList(mps, OpcodeTextConf.OPCODE03);
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
    public void timingType() throws ExecutionException, InterruptedException {
        DbTcpClient dbTcpClient = new DbTcpClient();
        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);

        List<DbOperationVo> list = new ArrayList<>();
        if (dbTcpClients.size() > 0 && dbTcpClients != null) {
            for (DbTcpClient tcpClient : dbTcpClients) {
                DbOperationVo dbOperationVo = new DbOperationVo();
                dbOperationVo.setHeartName(tcpClient.getHeartName());
                dbOperationVo.setFacility("01");
                dbOperationVo.setOperationText(TcpOrderTextConf.stateSave);
                list.add(dbOperationVo);
            }
            //       根据心跳分组
            SendCodeListUtils.queryIoListNoWait(list, OpcodeTextConf.OPCODE03);
        }


    }

    /*
     * 更新状态
     * */
    @Override
    public int updateOrInstart(DbTcpType dbTcpType) {
//            新增
        return updateDbTcpType(dbTcpType);
    }

    private void insert(DbTcpType item) {
        DbStateRecords dbStateRecords = new DbStateRecords();
        dbStateRecords.setCodeOnly(item.getHeartName());
        dbStateRecords.setDemandTime(new Date());
        dbStateRecords.setStateJson(com.alibaba.fastjson.JSON.toJSONString(item));
//        判断是否设备绑定
        String[] split = item.getHeartName().split("_");
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(split[0]);
        dbEquipment.setEquipmentNo(Integer.valueOf(split[1]));
        List<DbEquipment> dbEquipments = dbEquipmentMapper1.selectDbEquipmentList(dbEquipment);
        if (!dbEquipments.isEmpty()) {
            dbStateRecords.setEquipmentId(dbEquipments.get(0).getEquipmentId());
            if (dbEquipments.get(0).getEquipmentId() % 3 == 0) {
                int i = dbStateRecordsMapper.insertDbStateRecords(dbStateRecords);
            } else if (dbEquipments.get(0).getEquipmentId() % 3 == 1) {
                int i = dbStateRecordsMapper1.insertDbStateRecords(dbStateRecords);
            } else if (dbEquipments.get(0).getEquipmentId() % 3 == 2) {
                int i = dbStateRecordsMapper2.insertDbStateRecords(dbStateRecords);

            }
        }
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
