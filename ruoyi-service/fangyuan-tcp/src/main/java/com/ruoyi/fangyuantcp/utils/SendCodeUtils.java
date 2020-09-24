package com.ruoyi.fangyuantcp.utils;


import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            String text = tcpOrder.getFacility()+","+"05,"+tcpOrder.getOperationText();
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
            String text = tcpOrder.getFacility()+","+"03,"+tcpOrder.getOperationText();
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




}
