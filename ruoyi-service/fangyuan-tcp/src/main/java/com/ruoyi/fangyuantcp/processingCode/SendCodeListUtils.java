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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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


    public static R queryIoList(Map<String, List<DbOperationVo>> mps, int type) throws ExecutionException, InterruptedException {
        Set<String> strings = mps.keySet();
        HashMap<String, String> stringStringHashMap1 = new HashMap<>();
//    新建几条线程
        executorService = ThreadUtil.newExecutor(strings.size());

        for (String string : strings) {

            Future<HashMap<String, String>> send = send(mps.get(string), type);
            if (send.get() != null) {
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


    private static Future<HashMap<String, String>> send(List<DbOperationVo> dbOperationVos, int type) {


        Future<HashMap<String, String>> submit = executorService.submit(new Callable<HashMap<String, String>>() {

            @Override
            public HashMap<String, String> call() throws Exception {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                //                    循环list
//                    int query = query(dbOperationVo);
                for (int i = 0; i < dbOperationVos.size(); i++) {

                    String query = null;
                    try {


                        switch (type) {
                            case 1:
                                sendCodeUtils.query01(dbOperationVos.get(i));
                                break;
                            case  3:
                                sendCodeUtils.query03(dbOperationVos.get(i));
                                break;
                            case 5:
                                sendCodeUtils.query05(dbOperationVos.get(i));
                                break;
                            case 6:
                                sendCodeUtils.query06(dbOperationVos.get(i));
                                break;
                            case 0:
                                sendCodeUtils.query(dbOperationVos.get(i));
                                break;
                        }


                        log.info("发送成功存进去了：" + query + "当前的时间毫秒值是：" + new Date().getTime());
                        /*
                         * 建立监听返回的数据
                         * */
                        Thread.yield();
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



}
