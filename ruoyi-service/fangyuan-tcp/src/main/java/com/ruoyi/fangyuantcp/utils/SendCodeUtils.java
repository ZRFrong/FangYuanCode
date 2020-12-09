package com.ruoyi.fangyuantcp.utils;

import java.util.Date;


import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.abnormal.FaultExceptions;
import com.ruoyi.fangyuantcp.abnormal.OperationExceptions;
import com.ruoyi.fangyuantcp.timing.TaskTcpOrder;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.system.domain.DbTcpClient;
import com.ruoyi.system.domain.DbTcpOrder;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ExecutorService;

/*
 * 硬件端发送指令工具类
 * */
public class SendCodeUtils {
    //    在线设备map
    private static Map<String, ChannelHandlerContext> map = NettyServer.map;

    private static RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);


    /*
     * 普通操作指令发送
     * */
    public static int query(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "05," + tcpOrder.getOperationText();
        return operateCode(text, tcpOrder);
    }

    /*
     * 普通操作指令发送  06  自动状态设置更改
     * */
    public static int query06(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "06," + tcpOrder.getOperationText();
        return operateCode(text, tcpOrder);
    }

    /*
     * 批量状态查询
     * */
    public static int timingState(List<DbOperationVo> list) {
        list.forEach(ite -> querystate03(ite));
        return 1;
    }

    /*
     * 状态查询指令发送03
     * */
    public static int querystate03(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "03," + tcpOrder.getOperationText();
        return operateCode(text, tcpOrder);
    }


    /*
     * 状态查询指令发送03
     * */
    public static int querystate03Ctx(ChannelHandlerContext ctx) {
        String text = "01" + "," + "03," + TcpOrderTextConf.stateSave;
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

    /*
     *状态操作指令发送01
     * */
    public static int querystate01(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "01," + tcpOrder.getOperationText();
        return operateCode(text, tcpOrder);
    }


    private static ExecutorService executorService = null;

    /*
     * 多线程依次执行
     * */
    public static int queryIoList(Map<String, List<DbOperationVo>> mps) {
        Set<String> strings = mps.keySet();
//    新建几条线程
        executorService = ThreadUtil.newExecutor(strings.size());
        try {
            strings.forEach(ite -> send(mps.get(ite)));
            executorService.shutdown();
            while (!executorService.isTerminated()) {
//            等待执行完成再返回
            }
            return 1;
        } catch (Exception e) {
            executorService.shutdown();
            e.printStackTrace();
            return 0;
        }

    }


    private static void send(List<DbOperationVo> dbOperationVos) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
//                    循环list
//                    int query = query(dbOperationVo);
                for (int i = 0; i < dbOperationVos.size(); i++) {
                    int query = query(dbOperationVos.get(i));
//                    线程礼让让其他的先执行
                    if (i < dbOperationVos.size()) {
                        try {
                            Thread.sleep(500);
                            Thread.yield();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


    }


    /*
     * 手动自动状态查询
     * */
    public int sinceOrHand(DbEquipment equipment) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhand);
        dbOperationVo.setOperationName("手动自动状态查询");
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = querystate03(dbOperationVo);
        return querystate;
    }

    /*
     *通风手自状态查询
     * */
    public int sinceOrHandTongFeng(DbEquipment equipment) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFeng);
        dbOperationVo.setOperationName("查询自动通风是否开启");
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());

        int querystate = querystate01(dbOperationVo);
        return querystate;

    }

    /*
     *通风手自  开启关闭温度状态查询
     * */
    public int timingTongFengType(DbEquipment equipment) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo());
        dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFengType);
        dbOperationVo.setOperationName("查询当前自动通风开始和关闭的温度");
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = querystate01(dbOperationVo);
        return querystate;
    }

    /*
     *通风手自  装态更改
     * */
    public int operateTongFengHand(DbEquipment equipment, int i) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo());
        dbOperationVo.setOperationText(i == 0 ? TcpOrderTextConf.operateTongFeng : TcpOrderTextConf.operateTongFengOver);
        dbOperationVo.setOperationName(i == 0 ? "开启自动通风" : "关闭自动通风");
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = query(dbOperationVo);
        return querystate;
    }

    /*
     *通风手自温度  装态更改
     * */
    public int operateTongFengType(DbEquipment equipment, int i, String type) {
        int i2 = Integer.parseInt(type, 16);
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(i == 0 ? TcpOrderTextConf.operateTongFengType + "," + i2 : TcpOrderTextConf.operateTongFengOverType + "," + i2);
        dbOperationVo.setOperationName(i == 0 ? "更改开启自动通风温度为" + i2 : "更改关闭自动通风温度为" + i2);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = query06(dbOperationVo);
        return querystate;


    }


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
        try {
            String[] split1 = strings.toArray(new String[strings.size()]);
            String[] bytes = Crc16Util.to_byte(split1);
            byte[] data = Crc16Util.getData(bytes);
            ChannelHandlerContext no1_1 = null;
            no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();
        } catch (Exception e) {
//            抛出异常
            throw new FaultExceptions( tcpOrder.getHeartName() , tcpOrder.getOperationName(),tcpOrder.getFacility());
        }

            tcpOrder.setCreateTime(new Date());
//        存储操作信息到redis
            String operationText = tcpOrder.getOperationText();
            String facility = tcpOrder.getFacility();
            String heartName = tcpOrder.getHeartName();

            DbTcpOrder order = getOrder(tcpOrder);
            String s = RedisKeyConf.HANDLE + ":" + heartName + "_" + facility + "_" + operationText;
            String s2 = JSONArray.toJSONString(order);
            redisUtils.set(s, s2);
            /*
             * 建立监听返回的数据
             * */
            HeartbeatRun(s,tcpOrder);

            return 1;



    }


    private static DbTcpOrder getOrder(DbOperationVo dbOperationVo) {
        DbTcpOrder dbTcpOrder = new DbTcpOrder();
        dbTcpOrder.setHeartName(dbOperationVo.getHeartName());
        dbTcpOrder.setResults(0);
        dbTcpOrder.setText(dbOperationVo.getFacility() + dbOperationVo.getOperationText());
        dbTcpOrder.setCreateTime(new Date());
        return dbTcpOrder;
    }

    private  static  void HeartbeatRun(String text, DbOperationVo dbOperationVo)  {
        try {
        Thread.sleep(1000);
            String s = redisUtils.get(text);
            if (s.isEmpty()){
                throw  new OperationExceptions(dbOperationVo.getHeartName(),dbOperationVo.getOperationName(),dbOperationVo.getFacility());
            }else {
                DbTcpOrder dbTcpOrder = JSON.parseObject(s, DbTcpOrder.class);
                Integer results = dbTcpOrder.getResults();
                if (results==0){
                    throw  new OperationExceptions(dbOperationVo.getHeartName(),dbOperationVo.getOperationName(),dbOperationVo.getFacility());
                }
            }
        } catch (OperationExceptions operationExceptions) {
            throw  new OperationExceptions(dbOperationVo.getHeartName(),dbOperationVo.getOperationName(),dbOperationVo.getFacility());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
