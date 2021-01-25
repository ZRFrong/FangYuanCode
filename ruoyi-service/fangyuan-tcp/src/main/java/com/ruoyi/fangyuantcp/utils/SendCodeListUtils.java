package com.ruoyi.fangyuantcp.utils;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisLockUtil;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.abnormal.BusinessExceptionHandle;
import com.ruoyi.fangyuantcp.abnormal.FaultExceptions;
import com.ruoyi.fangyuantcp.abnormal.OperationExceptions;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpOrder;
import com.ruoyi.system.feign.RemoteApiService;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import jdk.nashorn.internal.objects.annotations.Where;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import sun.rmi.runtime.Log;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/*
 *
 * 多线程发送处理类*/
@Slf4j
public class SendCodeListUtils {
    /*
     * 多线程依次执行
     * */
    private static ExecutorService executorService = null;

    private static RemoteApiService remoteApiService = SpringUtils.getBean(RemoteApiService.class);
    //    在线设备map
    private static Map<String, ChannelHandlerContext> map = NettyServer.map;

    private static RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
//    private static RedisLockUtil redisLockUtil = SpringUtils.getBean(RedisLockUtil.class);


    public static R queryIoList(Map<String, List<DbOperationVo>> mps) throws ExecutionException, InterruptedException {
        Set<String> strings = mps.keySet();
        HashMap<String, String> stringStringHashMap1 = new HashMap<>();
//    新建几条线程
        executorService = ThreadUtil.newExecutor(strings.size());

        for (String string : strings) {
            Future<HashMap<String, String>> send = send(mps.get(string));
            if (send.get()!=null){
            HashMap<String, String> stringStringHashMap = send.get();
            stringStringHashMap1.putAll(stringStringHashMap);
            }
        }
        executorService.shutdown();

        while (!executorService.isTerminated()) {
//            等待执行完成再返回
        }
        if (stringStringHashMap1.size() > 0) {
//                有异常信息
            return R.error(502, JSON.toJSONString(stringStringHashMap1));
        } else {
            return R.ok("操作成功");
        }
    }


    private static Future<HashMap<String, String>> send(List<DbOperationVo> dbOperationVos) {


        Future<HashMap<String, String>> submit = executorService.submit(new Callable<HashMap<String, String>>() {

            @Override
            public HashMap<String, String> call() throws Exception {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                //                    循环list
//                    int query = query(dbOperationVo);
                for (int i = 0; i < dbOperationVos.size(); i++) {

                    String query = null;
                    try {
                        query = queryList(dbOperationVos.get(i));

                        log.info( "发送成功存进去了："+  query + "当前的时间毫秒值是："+new Date().getTime());
                        /*
                         * 建立监听返回的数据
                         * */
                        Thread.yield();
                        Thread.sleep(1000);
                        HeartbeatRunChildThread(query, dbOperationVos.get(i));
                    } catch (FaultExceptions e) {
                        DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
                        dbAbnormalInfo.setAlarmTime(new Date());
                        dbAbnormalInfo.setObjectType(dbOperationVos.get(i).getOperationName());
                        dbAbnormalInfo.setAlarmExplain(dbOperationVos.get(i).getHeartName());
                        dbAbnormalInfo.setFaultType(BusinessExceptionHandle.FAULT);
                        dbAbnormalInfo.setText(dbOperationVos.get(i).getOperationId());
                        remoteApiService.saveEquimentOperation(dbAbnormalInfo);
                        stringStringHashMap.put(dbOperationVos.get(i).getOperationName(), BusinessExceptionHandle.FAULT);


                    } catch (OperationExceptions e) {
                        DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
                        dbAbnormalInfo.setAlarmTime(new Date());
                        dbAbnormalInfo.setAlarmExplain(e.getMessage());
                        dbAbnormalInfo.setObjectType(dbOperationVos.get(i).getOperationName());
                        dbAbnormalInfo.setAlarmExplain(dbOperationVos.get(i).getHeartName());
                        dbAbnormalInfo.setText(dbOperationVos.get(i).getOperationId());
                        dbAbnormalInfo.setFaultType(BusinessExceptionHandle.OPERATIONEXCEPTIONS);


                        remoteApiService.saveEquimentOperation(dbAbnormalInfo);
                        stringStringHashMap.put(dbOperationVos.get(i).getOperationName(), BusinessExceptionHandle.OPERATIONEXCEPTIONS);
                    }
                }
                //                    等待500
                return stringStringHashMap;
            }
        });
        return submit;
    }

    /*
     * 普通操作指令发送子线程
     * */
    public static String queryList(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "05," + tcpOrder.getOperationText();
        return operateCodeList(text, tcpOrder);
    }

    public static String operateCodeList(String text, DbOperationVo tcpOrder) {

        String address = tcpOrder.getHeartName();
        ArrayList<String> strings1 = new ArrayList<>();
//          text处理
        String[] split3 = text.split(",");
        for (String s : split3) {
            strings1.add(s);
        }
        Object[] objects = strings1.toArray();
        String[] split = new String[objects.length];
        for (int i = 0; i < split.length; i++) {
            split[i] = objects[i].toString();
        }
        List<String> strings = new ArrayList<>();
        for (String s : split) {
            int i = Integer.parseInt(s);
            //            十六进制转成十进制
            String tmp = StringUtils.leftPad(Integer.toHexString(i).toUpperCase(), 4, '0');
            strings.add(tmp);
        }
        byte[] data=null;
        try {
            String[] split1 = strings.toArray(new String[strings.size()]);
            String[] bytes = Crc16Util.to_byte(split1);
            data = Crc16Util.getData(bytes);
            ChannelHandlerContext no1_1 = null;
            no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
        } catch (Exception e) {
//            抛出异常
            throw new FaultExceptions(tcpOrder.getHeartName(), tcpOrder.getOperationName(), tcpOrder.getFacility());
        }
        tcpOrder.setCreateTime(new Date());
//        存储操作信息到redis

        String operationText = tcpOrder.getOperationText();
        String facility = tcpOrder.getFacility();
        String heartName = tcpOrder.getHeartName();

        DbTcpOrder order = getOrder(tcpOrder);
        String str = Crc16Util.byteTo16String(data).toUpperCase();
        String str2 = str.replaceAll("\\s*", "");
        String s = RedisKeyConf.HANDLE + ":" + heartName + "_" + facility + "_" + str2;
        String s2 = JSONArray.toJSONString(order);
        redisUtils.set(s, s2);


        return s;

    }

    private static DbTcpOrder getOrder(DbOperationVo dbOperationVo) {
        DbTcpOrder dbTcpOrder = new DbTcpOrder();
        dbTcpOrder.setHeartName(dbOperationVo.getHeartName());
        dbTcpOrder.setResults(0);
        dbTcpOrder.setText(dbOperationVo.getFacility() + dbOperationVo.getOperationText());
        dbTcpOrder.setCreateTime(new Date());
        return dbTcpOrder;
    }

    /*
     * 暂时成功
     * */
    private static void HeartbeatRunChildThread(String text, DbOperationVo dbOperationVo) throws InterruptedException {

        String s1 = String.valueOf(Thread.currentThread().getId());
        try {
//            redisLockUtil.lock(text,s1,100);
        } catch (Exception e) {
            throw new OperationExceptions(dbOperationVo.getHeartName(), dbOperationVo.getOperationName(), dbOperationVo.getFacility());
        }
        String s = redisUtils.get(text);
        if (s.isEmpty()) {
            throw new OperationExceptions(dbOperationVo.getHeartName(), dbOperationVo.getOperationName(), dbOperationVo.getFacility());
        } else {
            DbTcpOrder dbTcpOrder = JSON.parseObject(s, DbTcpOrder.class);
            Integer results = dbTcpOrder.getResults();
            if (results == 0) {
                throw new OperationExceptions(dbOperationVo.getHeartName(), dbOperationVo.getOperationName(), dbOperationVo.getFacility());
            }
        }
//        redisLockUtil.unLock(text,s1);

    }
}
