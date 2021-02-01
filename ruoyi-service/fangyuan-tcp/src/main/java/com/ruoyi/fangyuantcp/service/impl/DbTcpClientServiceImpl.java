package com.ruoyi.fangyuantcp.service.impl;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.utils.SendCodeListUtils;
import com.ruoyi.fangyuantcp.utils.TcpOrderTextConf;
import com.ruoyi.system.domain.*;
import com.ruoyi.fangyuantcp.utils.SendCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
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

    @Autowired
    private IDbTcpTypeService dbTcpTypeService;


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

    @Override
    public int heartbeatUpdate(DbTcpClient dbTcpClient) {
        dbTcpClient.setHeartbeatTime(new Date());
        dbTcpClientMapper.updateDbTcpClient(dbTcpClient);
        return 0;
    }

    /*
     * 循环执行请求
     * */
    @Override
    public R operationList(List<DbOperationVo> dbOperationVo) throws ExecutionException, InterruptedException {
//       根据心跳分组
        Map<String, List<DbOperationVo>> mps = dbOperationVo.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
//         多个map依次执行（多线程）
        R r = SendCodeListUtils.queryIoList(mps);


        return r;
    }


    /*
    * 远程，本地检测
    * */
    @Override
    public void TaskOnline(DbTcpClient tcpClient) {
        String heartName = tcpClient.getHeartName();
       List<String>  integers= dbEquipmentMapper.selectByHeartNameToEqumentNo(heartName);

       if (integers.isEmpty()){
          List<DbOperationVo> list = new ArrayList<>();
           for (String integer : integers) {
               DbOperationVo dbOperationVo = new DbOperationVo();
                   dbOperationVo.setHeartName(tcpClient.getHeartName());
                   if (Integer.parseInt(integer)<10){

                   dbOperationVo.setFacility("0"+integer);
                   }else {
                       dbOperationVo.setFacility(integer);
                   }
                   dbOperationVo.setOperationText(TcpOrderTextConf.TaskOnline);
                   list.add(dbOperationVo);
               int i = SendCodeUtils.TaskOnline(list);

           }
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
                dbTcpTypeService.updateByHeartbeatOpen(dbTcpClient.getHeartName());
            } else {
//            不存在新建
                dbTcpClient.setHeartbeatTime(new Date());
                dbTcpClient.setIsOnline(0);
                int i3 = dbTcpClientMapper.insertDbTcpClient(dbTcpClient);
                i = 1;
//                查询温度
                dbTcpTypeService.timingTypeOnly(dbTcpClient);

            }

            List<DbEquipment>   dbEquipments = dbEquipmentMapper.selectDbEquipmentList(dbEquipment);
            for (DbEquipment equipment : dbEquipments) {
                equipment.setIsFault(0);
                equipment.setIsOnline(0);
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
