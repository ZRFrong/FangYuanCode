package com.ruoyi.fangyuantcp.utils;


import cn.hutool.core.thread.ThreadUtil;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
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
    private Map<String, ChannelHandlerContext> map = NettyServer.map;

    /*
     * 普通操作指令发送
     * */
    public int query(DbOperationVo tcpOrder) {
        String address = tcpOrder.getHeartName();
        try {
//        text处理
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

            return 1;
        } catch (NumberFormatException e) {
//            删除心跳

            return 0;
        }
    }

    /*
     * 状态查询指令发送
     * */
    public int querystate(DbOperationVo tcpOrder) {
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
            while (!executorService.isTerminated()){
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
                    System.out.println(dbOperationVos.get(i) + "执行了");
//                    线程礼让让其他的先执行
                    if (i < dbOperationVos.size()) {
                        Thread.yield();
                    }
                }
            }
        });


    }

    public static void main(String[] args) {
        Map<String, List<DbOperationVo>> mps = new HashMap<>();
        List<DbOperationVo> vos = new ArrayList<>();
        vos.add(new DbOperationVo("小明", "1", "115,25,16", "0", new Date()));
        vos.add(new DbOperationVo("小明", "2", "115,25,777", "0", new Date()));
        mps.put("小明", vos);
        List<DbOperationVo> vos2 = new ArrayList<>();
        vos2.add(new DbOperationVo("小红", "1", "115,25,16", "0", new Date()));
        vos2.add(new DbOperationVo("小红", "2", "115,25,777", "0", new Date()));
        mps.put("小红", vos2);
        int i = queryIoList(mps);
        System.out.println(i);

    }

}
