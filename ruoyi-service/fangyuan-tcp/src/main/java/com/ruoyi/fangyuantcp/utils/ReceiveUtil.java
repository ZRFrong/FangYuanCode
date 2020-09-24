package com.ruoyi.fangyuantcp.utils;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.json.JSON;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.domain.DbOperationVo;
import com.ruoyi.fangyuantcp.domain.DbTcpClient;
import com.ruoyi.fangyuantcp.domain.DbTcpOrder;
import com.ruoyi.fangyuantcp.domain.DbTcpType;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.DecimalFormat;
import java.util.*;

/*
 *接收信息处理
 * */


@Log4j2
public class ReceiveUtil {
    private IDbTcpClientService tcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbTcpOrderService tcpOrderService = SpringUtils.getBean(IDbTcpOrderService.class);
    private IDbTcpTypeService tcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);

    /*
     * 心跳添加或者更改状态处理
     * */
    public void heartbeatChoose(DbTcpClient dbTcpClient, ChannelHandlerContext ctx) {
        int i = tcpClientService.heartbeatChoose(dbTcpClient);
        if (i == 1) {
//            不存在 新建，添加map管理
            NettyServer.map.put(dbTcpClient.getHeartName(), ctx);
        }
    }

    /*
     *传感器状态接收处理
     * */
    public void stateRead(String type, ChannelHandlerContext ctx) {
        /*
            状态码 01 03   0A代码后边有10位
         *01 03 0A 00 FD 01 09 01 06 00 01 00 C1 39 6F
         *5组   空气温度 湿度   土壤温度 湿度   光照
         * */
        List<String> arr = getCharToArr(type);
        String s = arr.get(2);
        long l = Long.parseLong(s, 16);
        int i1 = Integer.parseInt(Long.toString(l));
        DbTcpType dbTcpType = new DbTcpType();
        for (int i = 0; i < i1; i++) {
            if (i == 0) {
                String s2 = arr.get(2 + i + 1);
//                温度
                String temp = getTemp(s2);

            }

        }
    }

    private List<String> getCharToArr(String type) {
        char[] chars = type.toCharArray();
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(chars[i]);
            if (i % 2 == 0) {
                stringBuilder.append(chars[i]);
                strings.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
        return strings;
    }

    /*
     *温度处理
     * */
    public static String getTemp(String s) {
        long tmpNum = Long.parseLong(s, 16);
        String temperatureNow = new String();
        if (tmpNum > 32768) {
//    负值
            tmpNum = tmpNum - 65535;
            float getfloat = getfloat(tmpNum);
            temperatureNow = "-" + getfloat;
        } else {
            float getfloat = getfloat(tmpNum);
            temperatureNow = "" + getfloat;
        }
        return temperatureNow;
    }

    /*
     * 湿度处理
     * */
    public static String getHum(String s) {
        Long aLong = Long.getLong(s, 16);
        float getfloat = getfloat(aLong);
        return "" + getfloat;
    }

    /*
     * 光照处理
     * */
    public static String getLight(String s) {
        Long aLong = Long.getLong(s, 16);
        return aLong.toString();
    }


    public static float getfloat(long sor) {

        int i = Integer.parseInt(String.valueOf(sor));
        DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
        float v = (float) i / 100;
        System.out.println(v);
        return v;
    }


    private RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);

    @Value("${person.redis-key}")
    private String record;

    /*
     * 操作响应
     * */
    public void stateRespond(ChannelHandlerContext ctx, String string) {
        String getname = getname(ctx);
//        从redis中拿到指定的数据
        DbTcpOrder dbTcpClient = redisUtils.get(getname, DbTcpOrder.class);
        dbTcpClient.setResults(1);
        Long i = new Date().getTime() - dbTcpClient.getCreateTime().getTime();
        dbTcpClient.setWhenTime(i);
//       改变状态存储进去
        redisUtils.set(getname,JSONArray.toJSONString(dbTcpClient));

    }

    /*
     * 通过通过找到心跳名称
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
