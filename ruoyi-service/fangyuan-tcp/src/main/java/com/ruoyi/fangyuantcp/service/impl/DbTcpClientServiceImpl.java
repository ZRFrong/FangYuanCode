package com.ruoyi.fangyuantcp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.json.JSON;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.fangyuantcp.domain.DbOperationVo;
import com.ruoyi.fangyuantcp.domain.DbTcpOrder;
import com.ruoyi.fangyuantcp.utils.SendCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuantcp.mapper.DbTcpClientMapper;
import com.ruoyi.fangyuantcp.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.common.core.text.Convert;

/**
 * tcp在线设备Service业务层处理
 *
 * @author fangyuan
 * @date 2020-09-01
 */
@Service
public class DbTcpClientServiceImpl implements IDbTcpClientService {

    @Autowired
    private  RedisUtils redisUtils;


    private SendCodeUtils sendCodeUtils=new SendCodeUtils();

    @Autowired
    private DbTcpClientMapper dbTcpClientMapper;


    @Value("${person.redis-key}")
    private String record;

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
    * 操作设备
    * */
    @Override
    public int operation(DbOperationVo dbTcpClient) {
//        发送指令
        int query = sendCodeUtils.query(dbTcpClient);
//        存储操作信息到redis
        String operationText = dbTcpClient.getOperationText();
        String facility = dbTcpClient.getFacility();
        String heartName = dbTcpClient.getHeartName();
        String s=record+":"+heartName+facility+operationText;
        DbTcpOrder order = getOrder(dbTcpClient);
        String s2 = JSONArray.toJSONString(order);
        redisUtils.set(s,s2);
        return query;
    }

    private DbTcpOrder getOrder(DbOperationVo dbOperationVo){
        DbTcpOrder dbTcpOrder = new DbTcpOrder();
        dbTcpOrder.setHeartName(dbOperationVo.getHeartName());
        dbTcpOrder.setResults(0);
        dbTcpOrder.setText(dbOperationVo.getFacility()+dbOperationVo.getOperationText());
        dbTcpOrder.setCreateTime(new Date());
        return dbTcpOrder;
    }

    @Override
    public int heartbeatChoose(DbTcpClient dbTcpClient) {
        List<DbTcpClient> dbTcpClients = selectDbTcpClientList(dbTcpClient);
        int i = 0;
        if (dbTcpClients.size() > 0 && dbTcpClients != null) {
            //            存在更新
            dbTcpClient.setHeartbeatTime(new Date());
            dbTcpClient.setIsOnline(0);
            updateDbTcpClient(dbTcpClient);

        } else {
//            不存在新建
            dbTcpClient.setHeartbeatTime(new Date());
            dbTcpClient.setIsOnline(1);
            i = insertDbTcpClient(dbTcpClient);
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
