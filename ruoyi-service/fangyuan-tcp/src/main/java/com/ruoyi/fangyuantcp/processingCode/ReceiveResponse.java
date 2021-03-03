package com.ruoyi.fangyuantcp.processingCode;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisLockUtil;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.domain.DbTcpOrder;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.List;
import java.util.Set;

/*
 * 返回响应
 * */
@Log4j2
public class ReceiveResponse {

    private RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
    private RedisLockUtil redisLockUtil = SpringUtils.getBean(RedisLockUtil.class);
    private IDbTcpClientService tcpClientService = SpringUtils.getBean(IDbTcpClientService.class);

    /*
     * 操作响应
     * */
    public void stateRespond(ChannelHandlerContext ctx, String string) {
        String getname = getname(ctx);
        DbTcpClient dbTcpClient1 = new DbTcpClient();
        dbTcpClient1.setHeartName(getname);
        List<DbTcpClient> dbTcpClients = tcpClientService.selectDbTcpClientList(dbTcpClient1);
        if (null != dbTcpClients && dbTcpClients.size() != 0) {
            DbTcpClient dbTcpClient = dbTcpClients.get(0);
            dbTcpClient.setHeartbeatTime(new Date());
            tcpClientService.updateDbTcpClient(dbTcpClient);
        }

        /*
         * 处理收到信息
         * */
        String key = getRedisKey(string, getname);

        log.info("收到操作指令：" + key + "当前的时间毫秒值是：" + new Date().getTime());

//        加锁
        String s1 = String.valueOf(Thread.currentThread().getId());
        redisLockUtil.lock(key,s1,100);
//        更新心跳时间


//        从redis中拿到指定的数据

        DbTcpOrder dbTcpClient = null;
        try {
            dbTcpClient = redisUtils.get(key, DbTcpOrder.class);
            dbTcpClient.setResults(1);
            Long i = new Date().getTime() - dbTcpClient.getCreateTime().getTime();
            dbTcpClient.setWhenTime(i);
            dbTcpClient.setUpdateTime(new Date());
            dbTcpClient.setResultsText(string);
//       改变状态存储进去
            redisUtils.set(key, JSONArray.toJSONString(dbTcpClient));
        } catch (Exception e) {
            log.info("无反馈接收：" + key + "当前的时间毫秒值是：" + new Date().getTime());
        }


        //        解锁
        redisLockUtil.unLock(key, s1);
    }


    private String getRedisKey(String string, String getname) {
        String charStic2 = "";
        switch (string.substring(2, 4)) {
            case "01":
                charStic2 = string.substring(0, 6);
                break;
            case "03":
                charStic2 = string.substring(0, 6);
                break;
            case "05":
                charStic2 = string;
                break;
            case "06":
                charStic2 = string;
                break;

        }
        String charStic = string.substring(0, 2);

        return RedisKeyConf.HANDLE + ":" + getname + "_" + charStic + "_" + charStic2;
    }

    /*
     * 通过通道找到心跳名称
     * */
    public static String getname(ChannelHandlerContext ctx) {
        Set<String> strings = NettyServer.map.keySet();
        for (String string : strings) {
            if (NettyServer.map.get(string) == ctx) {
                return string;
            }
        }
        return null;
    }
}
