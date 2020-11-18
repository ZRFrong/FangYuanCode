package com.ruoyi.fangyuantcp.utils;
import java.util.Date;


import cn.hutool.core.thread.ThreadUtil;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.system.domain.DbTcpClient;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;

/*
 * 硬件端发送指令工具类
 * */
public class SendCodeUtils {
    //    在线设备map
    private static Map<String, ChannelHandlerContext> map = NettyServer.map;

    /*
     * 普通操作指令发送
     * */
    public static int query(DbOperationVo tcpOrder) {
        String address = tcpOrder.getHeartName();
        try {
//          text处理
            ArrayList<String> strings1 = new ArrayList<>();
            String text = tcpOrder.getFacility() + "," + "05," + tcpOrder.getOperationText();
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
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();


        } catch (Exception e) {
//            删除心跳
            return 0;
        }
        return 1;
    }
    /*
     * 普通操作指令发送  06  自动状态设置更改
     * */
    public int query06(DbOperationVo tcpOrder) {
        String address = tcpOrder.getHeartName();
        try {
//          text处理
            ArrayList<String> strings1 = new ArrayList<>();
            String text = tcpOrder.getFacility() + "," + "06," + tcpOrder.getOperationText();
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
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();

            return 1;
        } catch (NumberFormatException e) {
//            删除心跳

            return 0;
        }
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
        String address = tcpOrder.getHeartName();
        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();
            String text = tcpOrder.getFacility() + "," + "03," + tcpOrder.getOperationText();
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
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();

            return 1;
        } catch (NumberFormatException e) {
//            删除心跳

            return 0;
        }
    }

    /*
    *状态查询指令发送01
    * */
    public static int querystate01(DbOperationVo tcpOrder) {
        String address = tcpOrder.getHeartName();
        try {
//        text处理
            ArrayList<String> strings1 = new ArrayList<>();
            String text = tcpOrder.getFacility() + "," + "01," + tcpOrder.getOperationText();
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
            ChannelHandlerContext no1_1 = map.get(address);
            Channel channel = no1_1.channel();
            channel.write(Unpooled.copiedBuffer(data));
            channel.flush();

            return 1;
        } catch (NumberFormatException e) {
//            删除心跳

            return 0;
        }
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
                    System.out.println(dbOperationVos.get(i) + "执行了");
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
    public void sinceOrHand(DbEquipment equipment) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhand);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = querystate03(dbOperationVo);
    }

    /*
    *通风手自状态查询
    * */
    public  void sinceOrHandTongFeng(DbEquipment equipment){
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFeng);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = querystate01(dbOperationVo);

    }

    /*
     *通风手自  开启关闭温度状态查询
     * */
    public void timingTongFengType(DbEquipment equipment) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(TcpOrderTextConf.SinceOrhandTongFengType);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = querystate01(dbOperationVo);

    }

    /*
     *通风手自  装态更改
     * */
    public void operateTongFengHand(DbEquipment equipment,int i) {
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(i==0?TcpOrderTextConf.operateTongFeng:TcpOrderTextConf.operateTongFengOver);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = querystate01(dbOperationVo);

    }

    /*
     *通风手自温度  装态更改
     * */
    public void operateTongFengType(DbEquipment equipment, int i,String type) {
        String temp = ReceiveUtil.getTemp(type);
        DbOperationVo dbOperationVo = new DbOperationVo();
        dbOperationVo.setHeartName(equipment.getHeartbeatText());
        dbOperationVo.setFacility(equipment.getEquipmentNo().toString());
        dbOperationVo.setOperationText(i==0?TcpOrderTextConf.operateTongFengType+temp:TcpOrderTextConf.operateTongFengOverType+temp);
        dbOperationVo.setIsTrue("1");
        dbOperationVo.setCreateTime(new Date());
        int querystate = query06(dbOperationVo);


    }
}
