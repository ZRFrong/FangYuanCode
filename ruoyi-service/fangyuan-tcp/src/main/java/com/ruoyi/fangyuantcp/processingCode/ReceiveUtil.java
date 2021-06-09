package com.ruoyi.fangyuantcp.processingCode;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.qiniu.util.Json;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisLockUtil;
import com.ruoyi.common.redis.util.RedisMqUtils;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.redis.wsmsg.MsgType;
import com.ruoyi.common.redis.wsmsg.SocketMsg;
import com.ruoyi.common.redis.wsmsg.WSTypeEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.abnormal.DropsExceptions;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.fangyuantcp.utils.LogOrderUtil;
import com.ruoyi.system.domain.*;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.tcp.NettyServer;
import com.ruoyi.system.feign.DbEquipmentComponentClient;
import com.ruoyi.system.feign.RemoteDeptService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/*
 *接收信息处理
 * */


@Slf4j
public class ReceiveUtil {

    private IDbTcpClientService tcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    private IDbEquipmentService iDbEquipmentService = SpringUtils.getBean(IDbEquipmentService.class);
    private IDbTcpTypeService tcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);
    private LogOrderUtil logOrderUtil = SpringUtils.getBean(LogOrderUtil.class);
    private RedisMqUtils redisMqUtils = SpringUtils.getBean(RedisMqUtils.class);
    private DbEquipmentComponentClient dbEquipmentComponentClient = SpringUtils.getBean(DbEquipmentComponentClient.class);
    /*
     * 心跳添加或者更改状态处理
     * */
    public void heartbeatChoose(DbTcpClient dbTcpClient, ChannelHandlerContext ctx) {
        // 记录指令返回日志
        logOrderUtil.recordFollowBack(dbTcpClient.getHeartName(), dbTcpClient.getHeartName(),"ReceiveUtil.heartbeatChoose");
        int i = tcpClientService.heartbeatChoose(dbTcpClient);
//            不存在 新建，添加map管理
        NettyServer.map.put(dbTcpClient.getHeartName(), ctx);
        if (i == 1) {
//            发送心跳查询指令
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String text = "01" + "," + "03," + TcpOrderTextConf.stateSave;
                    SendBasisUtils.operateCodeCtx(ctx, text);
                    try {
                        Thread.sleep(500);
                        //            发送心跳查询指令
                        String text3 = "01" + "," + "01," + TcpOrderTextConf.SinceOrhandTongFeng;
                        SendBasisUtils.operateCodeCtx(ctx, text3);
                        Thread.sleep(500);
                        String text2 = "01" + "," + "03," + TcpOrderTextConf.SinceOrhandTongFengType;
                        SendBasisUtils.operateCodeCtx(ctx, text2);
                        Thread.sleep(500);
                        String text4 = "01" + "," + "03," + TcpOrderTextConf.TaskOnline;
                        SendBasisUtils.operateCodeCtx(ctx, text4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();


        }


    }

    /*
     *传感器状态接收处理
     * */
    public void stateRead(String type, ChannelHandlerContext ctx) {
        logOrderUtil.recordFollowBack(getname(ctx), type,"ReceiveUtil.stateRead");
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
        List<DbTcpType> dbTcpTypes = tcpTypeService.selectDbTcpTypeList(dbTcpType);
        dbTcpType = dbTcpTypes.get(0);

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
            }

//            目前5个
        }

        dbTcpType.setIsShow(0);
        dbTcpType.setUpdateTime(new Date());
        DbTcpType dbTcpType1 = new DbTcpType();
        dbTcpType1.setHeartName(dbTcpType.getHeartName());
        List<DbTcpType> list = tcpTypeService.selectDbTcpTypeList(dbTcpType1);
        if (null != list && list.size() != 0) {
            DbTcpType dbTcpType2 = list.get(0);
            dbTcpType.setTcpTypeId(dbTcpType2.getTcpTypeId());

            tcpTypeService.updateDbTcpTypeFeedback(dbTcpType);
        } else {
            int i = tcpTypeService.insertDbTcpType(dbTcpType);

        }

    }

    /*
     * 手自动状态返回处理
     * */
    public void sinceOrHandRead(String type, ChannelHandlerContext ctx) {
        logOrderUtil.recordFollowBack(getname(ctx), type,"ReceiveUtil.sinceOrHandRead");
//        设备号
        String substring = type.substring(0, 2);
//        心跳
        String getname = getname(ctx);
//        01 03 02 00 F9 78 06
//        01 03 02 03 E8 B8 FA
//      手自判断
        List<String> arr = getCharToArr(type);
        int i = Integer.parseInt(arr.get(3) + arr.get(4), 16);
        DbEquipment dbEquipment1 = new DbEquipment();
        dbEquipment1.setHeartbeatText(getname);
        dbEquipment1.setEquipmentNo(Integer.parseInt(substring));
        List<DbEquipment> dbEquipments = iDbEquipmentService.selectDbEquipmentList(dbEquipment1);

        DbEquipment dbEquipment = dbEquipments.get(0);


        if (i < 450) {
//            手动
            dbEquipment.setIsOnline(1);
//            提醒
//                throw new DropsExceptions(dbEquipment.getEquipmentName(), "已经切换手动状态", dbEquipment.getEquipmentId().toString());
            log.info(dbEquipment.getEquipmentName() + "已经切换手动状态" + dbEquipment.getEquipmentId().toString());

        } else {
//            自动
            dbEquipment.setIsOnline(0);
        }
        iDbEquipmentService.updateDbEquipmentFeedback(dbEquipment);
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
        //C8 10 00 01 00 14 28
        // 14 5E
        // 04 B0
        // 0005
        // 01 33
        // 01 5A
        // 00 00
        // 00 00
        // 1842
        // 00E8
        // 001E000A01F401F401F401F401F401F40040000000001DA7
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
        // 记录指令返回日志
        logOrderUtil.recordFollowBack(getname(ctx), string,"ReceiveUtil.returnHand");
        DbTcpType dbTcpType = new DbTcpType();
        List<String> arr = getCharToArr(string);

        String getname = getname(ctx);
        dbTcpType.setHeartName(getname + "_" + arr.get(0));
        List<DbTcpType> list = tcpTypeService.selectDbTcpTypeList(dbTcpType);
        if (list != null && list.size() > 0) {
            DbTcpType dbTcpType1 = list.get(0);
//        01 01 01 00 51 88
            dbTcpType1.setIdAuto(arr.get(3).equals("00") ? 1 : 0);
            tcpTypeService.updateDbTcpTypeFeedback(dbTcpType1);
        } else {

//        01 01 01 00 51 88
            dbTcpType.setIdAuto(arr.get(3).equals("00") ? 1 : 0);
            int i = tcpTypeService.insertDbTcpType(dbTcpType);
        }


    }


    public void returnautocontrolType(ChannelHandlerContext ctx, String string) {
        logOrderUtil.recordFollowBack(getname(ctx), string,"ReceiveUtil.returnautocontrolType");
        DbTcpType dbTcpType = new DbTcpType();
        List<String> arr = getCharToArr(string);
        String getname = getname(ctx);
        dbTcpType.setHeartName(getname + "_" + arr.get(0));

        List<DbTcpType> list = tcpTypeService.selectDbTcpTypeList(dbTcpType);
        if (list != null && list.size() > 0) {
            DbTcpType dbTcpType1 = list.get(0);
            dbTcpType1.setAutocontrolType(getTemp(arr.get(3) + arr.get(4)));
            dbTcpType1.setAutocontrolTypeEnd(getTemp(arr.get(5) + arr.get(6)));
            //dbTcpType.setTcpTypeId(dbTcpType1.getTcpTypeId());
            tcpTypeService.updateDbTcpTypeFeedback(dbTcpType1);
        } else {

            int i = tcpTypeService.insertDbTcpType(dbTcpType);
        }

    }


    public void heartbeatUpdate(DbTcpClient dbTcpClient) {
        // 记录指令返回日志
        logOrderUtil.recordFollowBack(dbTcpClient.getHeartName(), dbTcpClient.getHeartName(),"ReceiveUtil.heartbeatUpdate");
        int i = tcpClientService.heartbeatUpdate(dbTcpClient);
    }


    /*
     * 自动上发返回
     * */
    /*
     * 主动通讯返回
     * */
    public void messageActive(ChannelHandlerContext ctx, String s) {
        String getname = getname(ctx);
        if (StringUtils.isEmpty(getname)){
            return;
        }
        /* C8 10 00 00 00 14 28                  头信息 包含设备号
           2B C1                                  程序版本号
           00 3C                                  发送时间间隔
           00 01                                  远程本地加风口自动手动
           00 D4 00 CA 00 00 00 00 00 22 02 BB    传感器数据
           00 1E  01 F4                           开关风口温度
           00 32 13 88 01 F4 01 F4 01 F4 01 F4   两卷帘4卷膜百分比显示    卷帘1 卷帘2 卷膜1 卷膜2 卷膜3 卷膜4
           00 E0   二进制    两卷帘四卷膜开关装态  （补光，浇水，配药，打药只能在远程的时候获取到）0关1开
           00 03 00 00
           60 D2*/

        /*
        *  C8 10 00 01 00 14 28  14
        *  10 73    4
        *  04 B0    4
        *  00 05
        *  00 00 00 00 00 00 00 00 00 00 00 00
        *  02 A6 01 59
        *  01 F4 01 F4 01 F4 01 F4 01 F4 01 F4
        *  00 00
        *  00 00 00 00
        *  82D3
        * */
        List<String> charToArr = getCharToArr(s);
//        传感状态记录+
        DbTcpType dbTcpType = new DbTcpType();
//        网关状态

//        功能项具体状态以及进度

//        设备号
        int i = Integer.parseInt(charToArr.stream().skip(2).collect(Collectors.toList()).subList(0, 2).get(1));
//      版本号
        StringBuilder stringBuilder = new StringBuilder();
        charToArr.stream().skip(7).collect(Collectors.toList()).subList(0, 2).forEach(item -> stringBuilder.append(item));
        stringBuilder.toString();
//        清空
        stringBuilder.setLength(0);
//        时间间隔   判断时候有设备在运行   3C  3分钟   单位0.1秒
        charToArr.stream().skip(9).collect(Collectors.toList()).subList(0, 2).forEach(item -> stringBuilder.append(item));
        stringBuilder.setLength(0);
//      本地，远程/风口自动,手动   2进制
        List<String> strings1 = charToArr.stream().skip(11).collect(Collectors.toList()).subList(0, 2);
//        int i1 = Integer.parseInt(strings1.get(0), 2);
//        int i2 = Integer.parseInt(strings1.get(1), 2);
        stringBuilder.setLength(0);
//    传感状态
        List<String> strings = charToArr.stream().skip(11).collect(Collectors.toList()).subList(0, 12);
        SensingTypeUtils.storageMessage(strings, 12, dbTcpType);

//    通风开关风口温度
        charToArr.stream().skip(23).collect(Collectors.toList()).subList(0, 4);
//    两卷帘4卷膜百分比显示
        charToArr.stream().skip(29).collect(Collectors.toList()).subList(0, 12).forEach(item -> stringBuilder.append(item));
        List<String> charToArr1 = getCharToArr(stringBuilder.toString());
        stringBuilder.setLength(0);
        ArrayList<Object> objects = new ArrayList<>();
        for (int i3 = 0; i3 < charToArr1.size(); i3++) {
            if ((i3 + 1) % 2 == 0) {
                stringBuilder.append(charToArr1.get(i3));
                int i4 = Integer.parseInt(stringBuilder.toString(), 16);
                objects.add(i4);
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append(charToArr1.get(i3));
            }
        }
//调试
//        if (code.equals(getname)) {
//            /*
//             *  读取推送至页面
//             * */
//            SocketMsg socketMsg = new SocketMsg();
//            socketMsg.setMsg(JSON.toJSONString(objects));
//            socketMsg.setUserId("1");
//            socketMsg.setMsgType(MsgType.INFO.name());
//            redisMqUtils.publishTopic(socketMsg, WSTypeEnum.SYSTEM);
//        }



//


    }

    public static String code = "";

    public static void binaryToDecimal(int n) {
        int t = 0;  //用来记录位数
        int bin = 0; //用来记录最后的二进制数
        int r = 0;  //用来存储余数
        while (n != 0) {
            r = n % 2;
            n = n / 2;
            bin += r *Math.pow(10, t);
            t++;
        }
        System.out.println(bin);
    }

    public static String hexToBinString(String hex){
        String bin=Integer.toBinaryString(Integer.parseInt(hex,16));
        int num = 16 - bin.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            builder.append("0");
        }
        return builder.append(bin).toString();
    }

    public static void main(String[] args) {
        int i = Integer.parseInt("00");
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.parseInt(1+"", 2));
        //字符串形式的:16进制!
        String s="0A00";
        //字符串形式十进制--作为桥梁!
        int sint=Integer.valueOf(s, 16);
        //十进制在转换成二进制的字符串形式输出!
        String bin=Integer.toBinaryString(Integer.parseInt(s,16));
        //输出!
        System.out.println(bin);
        String s1 = ReceiveUtil.hexToBinString(s);
        System.out.println(s1);
        System.out.println(s1.substring(s1.length() - 13, s1.length() - 12));

    }
}


