package com.ruoyi.fangyuantcp.processingCode;


/*
 * 各种指令基础发送类
 * */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisLockUtil;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.abnormal.FaultExceptions;
import com.ruoyi.fangyuantcp.abnormal.OperationExceptions;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.fangyuantcp.utils.Crc16Util;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpOrder;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
public class SendBasisUtils {

    private static RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
    //    操作记录
    private static IDbTcpOrderService tcpOrderService = SpringUtils.getBean(IDbTcpOrderService.class);

    //    在线设备map
    private static Map<String, ChannelHandlerContext> map = NettyServer.map;

    private static RedisLockUtil redisLockUtil = SpringUtils.getBean(RedisLockUtil.class);


    /*
     * 操作指令发送
     * */
    public static int operateCode(String text, DbOperationVo tcpOrder) {

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
        byte[] data = null;
        try {
            String[] split1 = strings.toArray(new String[strings.size()]);
            String[] bytes = Crc16Util.to_byte(split1);
            data = Crc16Util.getData(bytes);
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
        } catch (Exception e) {
//            抛出异常
            throw new FaultExceptions(tcpOrder.getHeartName(), tcpOrder.getOperationName(), tcpOrder.getFacility());
        }
//        "010300C80002 45 F5"

        tcpOrder.setCreateTime(new Date());
        String storage = storage(tcpOrder, data);
        /*

         * 建立监听返回的数据
         * */
        HeartbeatRun(storage, tcpOrder);
        return 1;
    }

    /*
     * 不同格式存储方式
     * */

    public static String storage(DbOperationVo dbOperationVo, byte[] data) {
//        存储操作信息到redis
        String facility = dbOperationVo.getFacility();
        String heartName = dbOperationVo.getHeartName();

        DbTcpOrder order = getOrder(dbOperationVo);
        String str = Crc16Util.byteTo16String(data).toUpperCase();
        String str2 = str.replaceAll("\\s*", "");
        order.setText(str2);
        String substring = "";
        switch (str2.substring(2, 4)) {
            case "01":
                //        010300C8000245F5
                substring = facility + str2.substring(2, 4) + str2.substring(10, 12);
                break;
            case "03":
//                01 03 0C 00 00 00 00 00 00 00 00 00 00 00 00 93 70
                String index = str2.substring(10, 12);
                int i = Integer.parseInt(index) * 2;
                substring = facility + str2.substring(2, 4) + StringUtils.leftPad(Integer.toHexString(i).toUpperCase(), 2, '0');
                break;
            case "05":
                substring = str2;
                break;
            case "06":
                substring = str2;
                break;
        }

        String s = RedisKeyConf.HANDLE + ":" + heartName + "_" + facility + "_" + substring;
        String s2 = JSONArray.toJSONString(order);
        redisUtils.set(s, s2);
        return s;

    }


    /*
     * 普通操作指令发送 通道
     * */
    public static int operateCodeCtx(ChannelHandlerContext ctx, String text) {

        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();
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
            String[] split1 = strings.toArray(new String[strings.size()]);
            String[] bytes = Crc16Util.to_byte(split1);
            byte[] data = Crc16Util.getData(bytes);
            Channel channel = ctx.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();

            return 1;
        } catch (NumberFormatException e) {
//            删除心跳
            return 0;
        }
    }


    private static DbTcpOrder getOrder(DbOperationVo dbOperationVo) {
        DbTcpOrder dbTcpOrder = new DbTcpOrder();
        dbTcpOrder.setHeartName(dbOperationVo.getHeartName());
        dbTcpOrder.setResults(0);
        dbTcpOrder.setText(dbOperationVo.getFacility() + dbOperationVo.getOperationText());
        dbTcpOrder.setCreateTime(new Date());
        return dbTcpOrder;
    }

    private static void HeartbeatRun(String text, DbOperationVo dbOperationVo) {
        try {
            Thread.sleep(1500);
            int tag = 0;
            //        加锁
            for (int i = 0; i < 15; i++) {

                String s1 = String.valueOf(Thread.currentThread().getId());
                redisLockUtil.lock(text, s1, 100);
                String s = redisUtils.get(text);

                if (StringUtils.isEmpty(s)) {
                    redisLockUtil.unLock(text, s1);
                    tag = 1;
                } else {
                    DbTcpOrder dbTcpOrder = JSON.parseObject(s, DbTcpOrder.class);
                    Integer results = dbTcpOrder.getResults();

                    //                    存储进入数据库
//
                    if (results == 0) {
                        redisLockUtil.unLock(text, s1);
                        tag = 1;
                    } else if (results == 1) {
                        tag = 0;
//                        if (text.split("_")[2].substring(2, 4).equals("03") || text.split("_")[2].substring(2, 2).equals("01")) {
//                        } else {
//                            log.info("查询指令返回，不记录数据库");
                            int i2 = tcpOrderService.insertDbTcpOrder(dbTcpOrder);
//                        }
                        redisLockUtil.unLock(text, s1);
                        redisUtils.delete(text);
                        break;
                    }
                }
                Thread.sleep(100);
            }
            if (tag == 1) {
                throw new OperationExceptions(dbOperationVo.getHeartName(), dbOperationVo.getOperationName(), dbOperationVo.getFacility());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void operateCodeNoWait(String text, DbOperationVo tcpOrder) {

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
        byte[] data = null;
        try {
            String[] split1 = strings.toArray(new String[strings.size()]);
            String[] bytes = Crc16Util.to_byte(split1);
            data = Crc16Util.getData(bytes);
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
        } catch (Exception e) {
//            抛出异常
            throw new FaultExceptions(tcpOrder.getHeartName(), tcpOrder.getOperationName(), tcpOrder.getFacility());
        }
    }
    private static void HeartbeatRuntest(String text, DbOperationVo dbOperationVo) {
        try {
            Thread.sleep(1500);
            int tag = 0;
            //        加锁
            for (int i = 0; i < 35; i++) {
                String s1 = String.valueOf(Thread.currentThread().getId());
                redisLockUtil.lock(text, s1, 100);
                String s = redisUtils.get(text);

                if (StringUtils.isEmpty(s)) {
                    redisLockUtil.unLock(text, s1);
                    tag = 1;
                } else {
                    DbTcpOrder dbTcpOrder = JSON.parseObject(s, DbTcpOrder.class);
                    Integer results = dbTcpOrder.getResults();

                    //                    存储进入数据库
                    if (results == 0) {
                        redisLockUtil.unLock(text, s1);
                        tag = 1;
                    } else if (results == 1) {
                        tag = 0;
                        int i2 = tcpOrderService.insertDbTcpOrder(dbTcpOrder);
//                        }
                        redisLockUtil.unLock(text, s1);
                        redisUtils.delete(text);
                        break;
                    }
                }
                Thread.sleep(100);
            }
            if (tag == 1) {
                throw new OperationExceptions(dbOperationVo.getHeartName(), dbOperationVo.getOperationName(), dbOperationVo.getFacility());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /*
     * 操作指令发送
     * */
    public static int operateCodetest(String text, DbOperationVo tcpOrder) {

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
        byte[] data = null;
        try {
            String[] split1 = strings.toArray(new String[strings.size()]);
            String[] bytes = Crc16Util.to_byte(split1);
            data = Crc16Util.getData(bytes);
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
        } catch (Exception e) {
//            抛出异常
            throw new FaultExceptions(tcpOrder.getHeartName(), tcpOrder.getOperationName(), tcpOrder.getFacility());
        }
//        "010300C80002 45 F5"

        tcpOrder.setCreateTime(new Date());
        String storage = storage(tcpOrder, data);
        /*

         * 建立监听返回的数据
         * */
        HeartbeatRuntest(storage, tcpOrder);
        return 1;
    }
}
