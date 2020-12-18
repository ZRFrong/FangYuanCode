package com.ruoyi.fangyuantcp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.abnormal.DropsExceptions;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.system.domain.*;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpOrderService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.text.DecimalFormat;
import java.util.*;

/*
 *接收信息处理
 * */


@Slf4j
public class ReceiveUtil {
    private IDbTcpClientService tcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbEquipmentService iDbEquipmentService = SpringUtils.getBean(IDbEquipmentService.class);
    private static IDbTcpTypeService tcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);

    /*
     * 心跳添加或者更改状态处理
     * */
    public void heartbeatChoose(DbTcpClient dbTcpClient, ChannelHandlerContext ctx) {
        int i = tcpClientService.heartbeatChoose(dbTcpClient);
//            不存在 新建，添加map管理
        NettyServer.map.put(dbTcpClient.getHeartName(), ctx);
        if (i == 1) {
//            发送心跳查询指令
            SendCodeUtils.querystate03Ctx(ctx);
        }


    }

    /*
     *传感器状态接收处理
     * */
    public void stateRead(String type, ChannelHandlerContext ctx) {
        /*
            状态码 01 03   0A代码后边有10位
         *01 03 0A 00 FD 01 09 01 06 00 01 00 C1 39 6F
         * 01 03 0C 00 00 00 7C 00 00 00 00 00 87 03 2D D3 76
         * 01 03 0C 00 64 03 20 00 00 00 00 00 00 00 00 11 8F
         *5组   空气温度 湿度   土壤温度 湿度   光照  二样黄痰
         * */
        List<String> arr = getCharToArr(type);
        DbTcpType dbTcpType = new DbTcpType();
//        设备号
        String getname = getname(ctx);

//        设备号
        String s3 = intToString(Integer.parseInt(Long.toString(Long.parseLong(arr.get(0), 16))));
        dbTcpType.setHeartName(getname + "_" + s3);

//      几位返回
        int i1 = Integer.parseInt(Long.toString(Long.parseLong(arr.get(2), 16)));
        for (int i = 0; i < (i1 / 2); i++) {
            switch (i) {
                case 0:
//                      空气  温度
                    dbTcpType.setTemperatureAir(getTemp(arr.get(3) + arr.get(4)));
                case 1:
//                    空气     湿度
                    dbTcpType.setHumidityAir(getHum(arr.get(5) + arr.get(6)));

                case 2:
//                土壤   温度
                    dbTcpType.setTemperatureSoil(getHum(arr.get(7) + arr.get(8)));
                case 3:
                    //                土壤   湿度
                    dbTcpType.setHumiditySoil(getTemp(arr.get(9) + arr.get(10)));
                case 4:
                    //                光照
                    dbTcpType.setLight(getLight(arr.get(11) + arr.get(12)));

                case 5:
//                    二氧化碳
//                    二氧化碳
                    dbTcpType.setCo2(getLight(arr.get(13) + arr.get(14)));
                    ;
            }

//            目前5个
        }

        dbTcpType.setUpdateTime(new Date());
        dbTcpType.setIsShow(0);
        List<DbTcpType> list = tcpTypeService.selectDbTcpTypeList(dbTcpType);
        if (list != null && list.size() > 0) {
            int i = tcpTypeService.updateOrInstart(dbTcpType);
        } else {
            int i = tcpTypeService.insertDbTcpType(dbTcpType);
        }

    }

    /*
     * 手自动状态返回处理
     * */
    public void sinceOrHandRead(String type, ChannelHandlerContext ctx) {
//        设备号
        String substring = type.substring(0, 2);
//        心跳
        String getname = getname(ctx);
//      手自判断
        List<String> arr = getCharToArr(type);
        int i = Integer.parseInt(arr.get(3) + arr.get(4), 16);
        DbEquipment dbEquipment1 = new DbEquipment();
        dbEquipment1.setHeartbeatText(getname);
        dbEquipment1.setEquipmentNo(Integer.parseInt(substring));
        List<DbEquipment> dbEquipments = iDbEquipmentService.selectDbEquipmentList(dbEquipment1);

        DbEquipment dbEquipment = dbEquipments.get(0);


        if (i < 600) {
//            手动
            dbEquipment.setIsOnline(1);
//            提醒
            throw new DropsExceptions(dbEquipment.getEquipmentName(), "已经切换手动状态", dbEquipment.getEquipmentId().toString());
        } else {
//            自动
            dbEquipment.setIsOnline(0);
        }
        iDbEquipmentService.updateDbEquipment(dbEquipment);
//        完事  END

    }

    /*
     *通风系列返回处理
     * */

    /*
     * 设备号处理，不满10加零
     * */
    public static String intToString(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return i + "";
        }
    }


    private static List<String> getCharToArr(String type) {
        char[] chars = type.toCharArray();
        List<String> strings = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int num = 1;
        for (int i = 0; i < chars.length; i++) {

            if (num % 2 == 0) {
                stringBuilder.append(chars[i]);
                strings.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append(chars[i]);
            }
            num++;

        }
        return strings;
    }

    /*
     *温度处理
     * */
    public static String getTemp(String s) {
        int i = Integer.parseInt(s, 16);
        String temperatureNow = new String();
        if (i > 32768) {
//    负值 65486
            i = i - 65535;
            temperatureNow = "" + getfloat(i);
        } else {
            temperatureNow = "" + getfloat(i);
        }
        return temperatureNow;
    }

    /*
     * 湿度处理
     * */
    public static String getHum(String s) {

        float getfloat = getfloat(Integer.parseInt(s, 16));
        return "" + getfloat;
    }

    /*
     * 光照处理
     * */
    public static String getLight(String s) {

        return Integer.parseInt(s, 16) + "";
    }


    public static float getfloat(long sor) {

        int i = Integer.parseInt(String.valueOf(sor));
        float v = (float) i / 10;
        return v;
    }


    private RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);


    /*
     * 操作响应
     * */
    public void stateRespond(ChannelHandlerContext ctx, String string) {
        String getname = getname(ctx);
        /*
         * 处理收到信息
         * */
        String key = getRedisKey(string, getname);


//        从redis中拿到指定的数据
        DbTcpOrder dbTcpClient = redisUtils.get(key, DbTcpOrder.class);
        dbTcpClient.setResults(1);
        Long i = new Date().getTime() - dbTcpClient.getCreateTime().getTime();
        dbTcpClient.setWhenTime(i);
//       改变状态存储进去
        redisUtils.set(getname, JSONArray.toJSONString(dbTcpClient));

    }

    private String getRedisKey(String string, String getname) {
        String charStic = getname.substring(0, 2);

        return RedisKeyConf.HANDLE + ":" + getname + "_" + charStic + "_" + string;
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


    /*
     * 通风自动手动状态返回
     * */
    public void returnHand(ChannelHandlerContext ctx, String string) {
        DbTcpType dbTcpType = new DbTcpType();
        List<String> arr = getCharToArr(string);
        String getname = getname(ctx);
        dbTcpType.setHeartName(getname + "_" + arr.get(0));
        List<DbTcpType> list = tcpTypeService.selectDbTcpTypeList(dbTcpType);
        DbTcpType dbTcpType1 = list.get(0);
        dbTcpType1.setIdAuto(arr.get(3).equals("00") ? 0 : 1);
        int i = tcpTypeService.updateDbTcpType(dbTcpType1);

    }


    public static void main(String[] args) {
        AutoVo autoVo = new AutoVo();
        autoVo.setAutoDown(11.5);
        autoVo.setAutoUp(12.0);

        System.out.println(autoVo);
    }

    /*
     *传感器状态接收处理
     * */
    public static void stateRead(String type) {
        /*
            状态码 01 03   0A代码后边有10位
         *01 03 0A 00 FD 01 09 01 06 00 01 00 C1 39 6F
         *5组   空气温度 湿度   土壤温度 湿度   光照  二氧化碳
         * */
        List<String> arr = getCharToArr(type);
        DbTcpType dbTcpType = new DbTcpType();
//        设备号

//        设备号
        String s3 = intToString(Integer.parseInt(Long.toString(Long.parseLong(arr.get(0), 16))));
        dbTcpType.setHeartName("01" + "_" + s3);

//      几位返回
        int i1 = Integer.parseInt(Long.toString(Long.parseLong(arr.get(2), 16)));
        for (int i = 0; i < (i1 / 2); i++) {
            switch (i) {
                case 0:
//                      空气  温度
                    dbTcpType.setTemperatureAir(getTemp(arr.get(2 + i + 1) + arr.get(2 + i + 2)));

                case 1:
//                    空气     湿度
                    dbTcpType.setHumidityAir(getHum(arr.get(2 + i + 1 + 1) + arr.get(2 + i + 2 + 1)));

                case 2:
//                土壤   温度
                    dbTcpType.setTemperatureSoil(getHum(arr.get(2 + i + 1 + 2) + arr.get(2 + i + 2 + 2)));
                case 3:
                    //                土壤   湿度
                    dbTcpType.setHumiditySoil(getTemp(arr.get(2 + i + 1 + 3) + arr.get(2 + i + 2 + 3)));
                case 4:
                    //                光照
                    dbTcpType.setLight(getLight(arr.get(2 + i + 1 + 4) + arr.get(2 + i + 2 + 4)));
                case 5:
//                    二氧化碳
                    dbTcpType.setCo2(getLight(arr.get(2 + i + 1 + 5) + arr.get(2 + i + 2 + 5)));
                    ;
            }
//            目前5个
        }
//        存储或者添加
        List<DbTcpType> list = tcpTypeService.selectDbTcpTypeList(dbTcpType);
        if (list.size() == 0 || list == null) {

            int i = tcpTypeService.insertDbTcpType(dbTcpType);
        } else {
            int i = tcpTypeService.updateOrInstart(dbTcpType);
        }


    }
}


