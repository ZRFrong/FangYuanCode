package com.ruoyi.fangyuantcp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpOrder;
import com.ruoyi.fangyuantcp.utils.SendCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.common.core.text.Convert;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

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


    private SendCodeUtils sendCodeUtils = new SendCodeUtils();


    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;


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
    public void updateByHeartbeatName(String heartbeatName) {
        dbTcpClientMapper.updateByHeartbeatName(heartbeatName);
    }

    /*
     * 循环执行请求
     * */
    @Override
    public int operationList(List<DbOperationVo> dbOperationVo) {
//       根据心跳分组
        Map<String, List<DbOperationVo>> mps = dbOperationVo.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
//         多个map依次执行（多线程）
        int query = sendCodeUtils.queryIoList(mps);


        return query;
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

    /*
     *在线设备手动  自动查询
     *
     * */
    @Override
    public int sinceOrHand() {

        DbTcpClient dbTcpClient = new DbTcpClient();
        dbTcpClient.setIsOnline(0);
        List<DbTcpClient> dbTcpClients = dbTcpClientMapper.selectDbTcpClientList(dbTcpClient);
        int i = 0;
        for (DbTcpClient tcpClient : dbTcpClients) {
            DbEquipment dbEquipment = new DbEquipment();
            dbEquipment.setHeartbeatText(tcpClient.getHeartName());
            for (DbEquipment equipment : dbEquipmentMapper.selectDbEquipmentList(dbEquipment)) {
                int i1 = sendCodeUtils.sinceOrHand(equipment);
                if (i1 != 0) {
                    i = 1;
                }
            }
        }
        return i;
    }

    /*
     *
     * 查询状态
     * */
    @Override
    public int query(DbOperationVo dbTcpClient) {
        //查询状态
        int querystate = sendCodeUtils.querystate03(dbTcpClient);
        return querystate;
    }

    /*
     * 操作设备
     * */
    @Override
    public int operation(DbOperationVo dbTcpClient) {
//        发送指令
        int query = sendCodeUtils.query(dbTcpClient);

        return query;
    }


    @Override
    public int heartbeatChoose(DbTcpClient dbTcpClient) {

            int i = 0;
        try {
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

            } else {
//            不存在新建
                dbTcpClient.setHeartbeatTime(new Date());
                dbTcpClient.setIsOnline(0);
                int i3 = dbTcpClientMapper.insertDbTcpClient(dbTcpClient);
                i = 1;
            }
            List<DbEquipment> dbEquipments = null;
            dbEquipments = dbEquipmentMapper.selectDbEquipmentList(dbEquipment);
            for (DbEquipment equipment : dbEquipments) {
                equipment.setIsFault(0);
                int i2 = dbEquipmentMapper.updateDbEquipment(equipment);
            }
        } catch (Exception e) {
            log.error(dbTcpClient.getHeartName()+":设备未绑定");
        }

        return i;
    }

    /**
     * 删除tcp在线设备信息
     *
     * @param tcpClientId tcp在线设备ID
     * @return 结果
     */
    public int deleteDbTcpClientById(Long tcpClientId) {
        return dbTcpClientMapper.deleteDbTcpClientById(tcpClientId);
    }


}
