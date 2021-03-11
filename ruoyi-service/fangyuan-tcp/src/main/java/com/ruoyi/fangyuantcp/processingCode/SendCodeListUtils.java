package com.ruoyi.fangyuantcp.processingCode;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.abnormal.BusinessExceptionHandle;
import com.ruoyi.fangyuantcp.abnormal.FaultExceptions;
import com.ruoyi.fangyuantcp.abnormal.OperationExceptions;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.fangyuantcp.utils.Crc16Util;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpOrder;
import com.ruoyi.system.feign.RemoteApiService;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.*;

/*
 * 单设备多指令
 * 多线程发送处理类*/
@Slf4j
public class SendCodeListUtils {


    public static SendCodeUtils sendCodeUtils = new SendCodeUtils();

    /*
     * 多线程依次执行
     * */
    private static ExecutorService executorService = null;

    private static RemoteApiService remoteApiService = SpringUtils.getBean(RemoteApiService.class);
    //    在线设备map
    private static Map<String, ChannelHandlerContext> map = NettyServer.map;


    public static R queryIoList(Map<String, List<DbOperationVo>> mps)  {
        Set<String> strings = mps.keySet();
        HashMap<String, String> stringStringHashMap1 = new HashMap<>();
//    新建几条线程

        executorService = ThreadUtil.newExecutor(strings.size() * 2);

        for (String string : strings) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, String> send = send(mps.get(string));
                    if (send != null) {
                        stringStringHashMap1.putAll(send);
                    }
                }
            });
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


    private static ExecutorService EXECUTOR_SERVICE = null;

    public static HashMap<String, String> send(List<DbOperationVo> dbOperationVos) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        //                    循环list
//                    int query = query(dbOperationVo);
        EXECUTOR_SERVICE = ThreadUtil.newExecutor();
        for (int i = 0; i < dbOperationVos.size(); i++) {
            int finalI = i;
            EXECUTOR_SERVICE.execute(new Runnable() {
                @Override
                public void run() {
                    String query = null;
                    try {

                        if (dbOperationVos.get(finalI).getOperationTextType().equals("0") ) {
                            sendCodeUtils.query(dbOperationVos.get(finalI));
                        }else if (dbOperationVos.get(finalI).getOperationTextType().equals("9")){
                            sendCodeUtils.queryText(dbOperationVos.get(finalI));
                        }
                        else {
                            sendCodeUtils.queryType(dbOperationVos.get(finalI));
                        }


                        log.info("发送成功存进去了：" + query + "当前的时间毫秒值是：" + new Date().getTime());

                    } catch (FaultExceptions e) {
                        DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
                        dbAbnormalInfo.setAlarmTime(new Date());
                        dbAbnormalInfo.setObjectType(dbOperationVos.get(finalI).getOperationName());
                        dbAbnormalInfo.setAlarmExplain(dbOperationVos.get(finalI).getHeartName());
                        dbAbnormalInfo.setFaultType(BusinessExceptionHandle.FAULT);
                        dbAbnormalInfo.setText(dbOperationVos.get(finalI).getOperationId());
                        remoteApiService.saveEquimentOperation(dbAbnormalInfo);
                        stringStringHashMap.put(dbOperationVos.get(finalI).getOperationName(), BusinessExceptionHandle.FAULT);
                    } catch (OperationExceptions e) {
                        DbAbnormalInfo dbAbnormalInfo = new DbAbnormalInfo();
                        dbAbnormalInfo.setAlarmTime(new Date());
                        dbAbnormalInfo.setAlarmExplain(e.getMessage());
                        dbAbnormalInfo.setObjectType(dbOperationVos.get(finalI).getOperationName());
                        dbAbnormalInfo.setAlarmExplain(dbOperationVos.get(finalI).getHeartName());
                        dbAbnormalInfo.setText(dbOperationVos.get(finalI).getOperationId());
                        dbAbnormalInfo.setFaultType(BusinessExceptionHandle.OPERATIONEXCEPTIONS);
                        remoteApiService.saveEquimentOperation(dbAbnormalInfo);
                        stringStringHashMap.put(dbOperationVos.get(finalI).getOperationName(), BusinessExceptionHandle.OPERATIONEXCEPTIONS);
                    }
                }
            });
            long t1 = System.currentTimeMillis();
            while(true){
//                获取当前毫秒
                long t2 = System.currentTimeMillis();
                if(t2-t1 >500){
                    break;
                }else{

                }
            }

        }
        EXECUTOR_SERVICE.shutdown();
        while (!EXECUTOR_SERVICE.isTerminated()) {
//            等待执行完成再返回
        }
        //                    等待500
        return stringStringHashMap;

    }


    public static void queryIoListNoWait(List<DbOperationVo> lists){
        for (DbOperationVo list : lists) {
            sendCodeUtils.queryNoWait(list);
        }


    }
}
