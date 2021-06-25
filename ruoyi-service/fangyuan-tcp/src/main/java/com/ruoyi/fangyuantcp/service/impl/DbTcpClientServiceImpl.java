package com.ruoyi.fangyuantcp.service.impl;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.ruoyi.common.constant.MessageReturnTypeConstant;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.TcpOrderTextConf;
import com.ruoyi.fangyuantcp.utils.SendSocketMsgUtils;
import com.ruoyi.system.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.util.CollectionUtils;


/**
 * tcp在线设备Service业务层处理
 *
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
@Slf4j
public class DbTcpClientServiceImpl implements IDbTcpClientService {


    @Autowired
    private DbEquipmentMapper1 dbEquipmentMapper;


    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;

    @Autowired
    private IDbTcpTypeService dbTcpTypeService;

    @Autowired
    private SendSocketMsgUtils sendSocketMsgUtils;

    /**
     * 查询tcp在线设备
     *
     * @param tcpClientId tcp在线设备ID
     * @return tcp在线设备
     */
    @Override
    public DbTcpClient selectDbTcpClientById(Long tcpClientId) {
        return dbTcpClientMapper.selectDbTcpClientById(tcpClientId);
    }

    /**
     * 查询tcp在线设备列表
     *
     * @param dbTcpClient tcp在线设备
     * @return tcp在线设备
     */
    @Override
    public List<DbTcpClient> selectDbTcpClientList(DbTcpClient dbTcpClient) {
        return dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
    }

    /**
     * 新增tcp在线设备
     *
     * @param dbTcpClient tcp在线设备
     * @return 结果
     */
    @Override
    public int insertDbTcpClient(DbTcpClient dbTcpClient) {
        return dbTcpClientMapper.insertDbTcpClient(dbTcpClient);
    }

    /**
     * 修改tcp在线设备
     *
     * @param dbTcpClient tcp在线设备
     * @return 结果
     */
    @Override
    public int updateDbTcpClient(DbTcpClient dbTcpClient) {
        return dbTcpClientMapper.updateDbTcpClient(dbTcpClient);
    }

    @Override
    public List<String> heartBeatDFuzzy(String heartBeat) {
        String heartBeat1 = "%" + heartBeat + "%";
        return dbTcpClientMapper.heartBeatDFuzzy(heartBeat1);
    }

    /**
     * 删除tcp在线设备对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbTcpClientByIds(String ids) {
        return dbTcpClientMapper.deleteDbTcpClientByIds(Convert.toStrArray(ids));
    }


    @Override
    public int heartbeatUpdate(DbTcpClient dbTcpClient) {
        List<DbTcpClient> list = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        if (CollectionUtils.isEmpty(list)){
            return 0;
        }
        DbTcpClient dbTcpClient1 = list.get(0);
        dbTcpClient1.setHeartbeatTime(new Date());
        dbTcpClientMapper.updateDbTcpClient(dbTcpClient1);
        return 0;
    }


    /*
     * 远程，本地检测
     * */
    @Override
    public void TaskOnline(DbTcpClient tcpClient)  {
        String heartName = tcpClient.getHeartName();
        List<String> integers = dbEquipmentMapper.selectByHeartNameToEqumentNo(heartName);

        if (integers.isEmpty()) {
            List<DbOperationVo> list = new ArrayList<>();
            for (String integer : integers) {
                DbOperationVo dbOperationVo = new DbOperationVo();
                dbOperationVo.setHeartName(tcpClient.getHeartName());
                if (Integer.parseInt(integer) < 10) {

                    dbOperationVo.setFacility("0" + integer);
                    dbOperationVo.setOperationTextType(OpcodeTextConf.OPCODE03);
                } else {
                    dbOperationVo.setFacility(integer);
                }
                dbOperationVo.setOperationText(TcpOrderTextConf.TaskOnline);
                list.add(dbOperationVo);
            }
            //       根据心跳分组
            SendCodeListUtils.queryIoListNoWait(list);
        }

    }

    @Override
    public void deleteDbtcpHeartbeatName(String heartbeatName) {
        /*
         * 设备装填修改
         * */
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(heartbeatName);
        for (DbEquipment equipment : dbEquipmentMapper.selectDbEquipmentList(dbEquipment)) {
            equipment.setIsFault(1);
            dbEquipmentMapper.updateDbEquipment(equipment);
        }


        dbTcpClientMapper.deleteDbtcpHeartbeatName(heartbeatName);
    }


    @Override
    public int heartbeatChoose(DbTcpClient dbTcpClient) {

        int i = 0;

        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        /*
         * 设备在线
         * */
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(dbTcpClient.getHeartName());


        if (dbTcpClients.size() > 0 && dbTcpClients != null) {
//            存在更新
            dbTcpClients.get(0).setHeartbeatTime(new Date());
            dbTcpClients.get(0).setIsOnline(0);
            int i1 = dbTcpClientMapper.updateDbTcpClient(dbTcpClients.get(0));
            dbTcpTypeService.updateByHeartbeatOpen(dbTcpClient.getHeartName());
        } else {
//            不存在新建
            dbTcpClient.setHeartbeatTime(new Date());
            dbTcpClient.setIsOnline(0);
            int i3 = dbTcpClientMapper.insertDbTcpClient(dbTcpClient);
            i = 1;
            ArrayList<String> list = new ArrayList<>();
            list.add(dbTcpClient.getHeartName());
            sendSocketMsgUtils.onlineState(list,MessageReturnTypeConstant.ONLINE);
        }
        if (dbEquipment != null) {
            List<DbEquipment> dbEquipments = dbEquipmentMapper.selectDbEquipmentList(dbEquipment);
            for (DbEquipment equipment : dbEquipments) {
                equipment.setIsFault(0);
                int i2 = dbEquipmentMapper.updateDbEquipment(equipment);
            }
        }else{
            log.warn(dbTcpClient.getHeartName()+":设备未绑定");
        }


        return i;
    }

    /**
     * 删除tcp在线设备信息
     *
     * @param tcpClientId tcp在线设备ID
     * @return 结果
     */
    @Override
    public int deleteDbTcpClientById(Long tcpClientId) {
        return dbTcpClientMapper.deleteDbTcpClientById(tcpClientId);
    }

    @Override
    public Integer queryOne(String heartName) {
        Integer integer = dbTcpClientMapper.queryOne(heartName);
        System.out.println(heartName + "  === " + integer);
        return dbTcpClientMapper.queryOne(heartName);
    }

    @Override
    public int updateHeartbeatTime(DbTcpClient client) {
        return dbTcpClientMapper.updateHeartbeatTime(client);
    }
}
