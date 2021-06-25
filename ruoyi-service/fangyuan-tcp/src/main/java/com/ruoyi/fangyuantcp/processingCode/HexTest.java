package com.ruoyi.fangyuantcp.processingCode;

import com.ruoyi.common.constant.FunctionStateConstant;
import com.ruoyi.common.constant.MessageReturnTypeConstant;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbEquipmentService;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.utils.LogOrderUtil;
import com.ruoyi.fangyuantcp.utils.SendSocketMsgUtils;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.DbEquipmentComponentClient;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Mr.Zhao
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuantcp.processingCode.HexTest.java
 * @Description
 * @createTime 2021年05月20日 15:16:00
 * @sign 编程法则第一条，永远不要大而全
 */
@Slf4j
public class HexTest {

    private IDbTcpTypeService dbTcpTypeService = SpringUtils.getBean(IDbTcpTypeService.class);

    private DbEquipmentComponentClient dbEquipmentComponentClient = SpringUtils.getBean(DbEquipmentComponentClient.class);

    private LogOrderUtil logOrderUtil = SpringUtils.getBean(LogOrderUtil.class);

    private SendSocketMsgUtils socketMsgUtils = SpringUtils.getBean(SendSocketMsgUtils.class);

    private IDbEquipmentService dbEquipmentService = SpringUtils.getBean(IDbEquipmentService.class);

    private RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);

    /**
     * 字符串按指定间隔分割
     *
     * @param str
     * @param regex
     * @since: 2.0.0
     * @return: java.util.List<java.lang.String>
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:10
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    public static List<String> strSplit(String str, Integer regex) {
        if (str.length() % regex > 0) {
            log.error("接收的字符串不满足格式！");
            return null;
        }
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < str.length() / regex + 1; i++) {
            list.add(str.substring(i == 1 ? 0 : regex * (i - 1), regex * i));
        }
        return list;
    }

    /**
     * 16进制字符串转二进制字符串 二进制字符串不足16位补0
     *
     * @param hex
     * @since: 2.0.0
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:11
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    private static String hexToBinString(String hex) {
        String bin = Integer.toBinaryString(Integer.parseInt(hex, 16));
        int num = 16 - bin.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++) {
            builder.append("0");
        }
        return builder.append(bin).toString();
    }


    /**
     * 主动上发消息解析
     *
     * @param ctx
     * @param s
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:05
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    public void messageActive(ChannelHandlerContext ctx, String s) {
        logOrderUtil.recordFollowBack(ReceiveUtils.getname(ctx), s,"HexTest.messageActive");
        String heartbeatText = ReceiveUtils.getname(ctx);
        if (StringUtils.isEmpty(heartbeatText)) {
            return;
        }
        /** C8 10 00 00 00 14 28   14               头信息 包含设备号 没处理
         2B C1                   4               程序版本号  没处理
         00 3C                   4              发送时间间隔 没处理
         00 01                   4               远程本地加风口自动手动  没处理
         00 D4 00 CA 00 00 00 00 00 22 02 BB    传感器数据
         00 D4 00 CA 00 00 00 00 00 22 02 BB
         00 1E  01 F4                           开关风口温度
         00 32 13 88 01 F4 01 F4 01 F4 01 F4   两卷帘4卷膜百分比显示    卷帘1 卷帘2 卷膜1 卷膜2 卷膜3 卷膜4
         00 E0   二进制    两卷帘四卷膜开关装态  （补光，浇水，配药，打药只能在远程的时候获取到）0关1开
         00 03 00 00 补光定时
         60 D2*/
        /**
         * C8100001001428 145B 04B0 0005 011B 013F 0000 0000 0008 01C3 012C 0064 0064 01F4 004B 005A 01F4 01F4 0000 0000 0000 9197
         * C8100001001428145D04B00005010C0131000000000020025B02580064006400640064006401F401F4000000000000 1AB1
         * */

        /**
         * 自动手动状态
         * */

        automaticManualState(s.substring(22,26),heartbeatText);

        /**
         * 传感器数据 及自动通风温度状态修改
         * */
        String sensorData = s.substring(26, 58);
        if (s.length() > 98) {
            sensorData = sensorData + s.substring(102, 122);
        }
        sensor(sensorData, heartbeatText);

        /**
         * 进度条解析
         * */
        progressAnalysis(s.substring(58, 82), heartbeatText);
        /**
         * 各个功能状态 这里只对补光做了处理
         * */
        lightStatus(s.substring(82, 94), heartbeatText);

        daYaoStatus(s.substring(82,94),heartbeatText);

        /**
         *  补光定时是否开启，或者补光定时是否存在h
         * */
        //fillLightTimingStatus(s.substring(82,86),heartbeatText);
        /**
         * 电导率 PH  氮 磷 钾
         * */

    }

    /**
     * 关于补光开关状态及补光定时解析
     *
     * @param hexStr
     * @param heartbeatText
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:06
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    private void lightStatus(String hexStr, String heartbeatText) {
        if (socketMsgUtils.hexStrVarietyCheck(RedisKeyConf.LIGHT_HEX_+heartbeatText,hexStr)){
            return;
        }
        List<String> list = strSplit(hexStr, 4);
        String string = HexTest.hexToBinString(list.get(0));
        log.info("lightStatus.string============================={}", string);
        Integer fillLightTimingStatus = null;
        if (Integer.parseInt(list.get(1), 16) != 0 || Integer.parseInt(list.get(2), 16) != 0) {
            fillLightTimingStatus = 0;
        }
        String[] split = string.split("");
        if (socketMsgUtils.switchCheck(heartbeatText, FunctionStateConstant.CHECK_CODE_3+"", Integer.parseInt(split[3]))){
            dbEquipmentComponentClient.modifyLightStatus(heartbeatText, split[3], fillLightTimingStatus);
            socketMsgUtils.switchMsgSend(heartbeatText,split[3],FunctionStateConstant.CHECK_CODE_3);
        }
    }


    /**
     * 关于打药开关状态解析
     * @since: 2.0.0
     * @param hexStr
     * @param heartbeatText
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:06
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private void daYaoStatus(String hexStr,String heartbeatText){
        if (socketMsgUtils.hexStrVarietyCheck(RedisKeyConf.SWITCH_HEX_+heartbeatText,hexStr)){
            return;
        }
        List<String> list = strSplit(hexStr,4);
        String string = HexTest.hexToBinString(list.get(0));
        log.info("daYaoStatus.string============================={}",string);
        String[] split = string.split("");
        if (socketMsgUtils.switchCheck(heartbeatText, FunctionStateConstant.CHECK_CODE_5+"", Integer.parseInt(split[0]))){
            dbEquipmentComponentClient.modifyFunctionLogoStatus(heartbeatText, FunctionStateConstant.CHECK_CODE_5+"", split[0], null);
            socketMsgUtils.switchMsgSend(heartbeatText,split[0],FunctionStateConstant.CHECK_CODE_5);
        }
        if (socketMsgUtils.switchCheck(heartbeatText, FunctionStateConstant.CHECK_CODE_4+"", Integer.parseInt(split[2]))){
            dbEquipmentComponentClient.modifyFunctionLogoStatus(heartbeatText, FunctionStateConstant.CHECK_CODE_4+"", split[2], null);
            socketMsgUtils.switchMsgSend(heartbeatText,split[2],FunctionStateConstant.CHECK_CODE_4);
        }
        if (socketMsgUtils.switchCheck(heartbeatText, FunctionStateConstant.CHECK_CODE_9+"", Integer.parseInt(split[1]))){
            dbEquipmentComponentClient.modifyFunctionLogoStatus(heartbeatText, FunctionStateConstant.CHECK_CODE_9+"", split[1], null);
            socketMsgUtils.switchMsgSend(heartbeatText,split[1],FunctionStateConstant.CHECK_CODE_9);
        }

    }


    /**
     * 两卷帘四卷膜进度百分比解析
     *
     * @param data
     * @param heartbeatText
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:07
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    private void progressAnalysis(String data, String heartbeatText) {
        if (socketMsgUtils.hexStrVarietyCheck(RedisKeyConf.PROGRESS_HEX_+heartbeatText,data)){
            return;
        }
        List<String> list = HexTest.strSplit(data, 4);
        dbEquipmentComponentClient.progressAnalysis(list, heartbeatText);
        socketMsgUtils.progressState(heartbeatText);
    }

    /**
     * 自动手动状态
     * @since: 2.0.0
     * @param data
     * @param heartbeatText
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/23 13:50
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private void automaticManualState(String data, String heartbeatText){
        if (socketMsgUtils.hexStrVarietyCheck(RedisKeyConf.SWITCH_HEX_+heartbeatText,data)){
            return;
        }
        String binStr = HexTest.hexToBinString(data);
        int isOnline = Integer.parseInt(binStr.split("")[binStr.length() - 1]);
        DbEquipment dbEquipment = dbEquipmentService.selectDbEquipmentByHeartName(heartbeatText);
        if (dbEquipment == null){
            return;
        }
        dbEquipment.setIsOnline(isOnline == 0? 1 : isOnline == 1? 0 : isOnline );
        dbEquipmentService.updateDbEquipment(dbEquipment);
        socketMsgUtils.autoState(heartbeatText,dbEquipment.getIsOnline());
    }

    /**
     * 六个传感器温度解析及自动通风开关风口温度
     *
     * @param s
     * @param heartbeatText
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:07
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    public void  sensor(String s, String heartbeatText) {
        if (socketMsgUtils.hexStrVarietyCheck(RedisKeyConf.SENSOR_HEX_+heartbeatText,s)){
            return;
        }
        List<String> list = HexTest.strSplit(s, 4);
        if (list == null) {
            return;
        }
        heartbeatText = heartbeatText +"_"+ dbEquipmentComponentClient.selectByHeartbeatText(heartbeatText);
        DbTcpType build = DbTcpType.builder()
                .heartName(heartbeatText )
                .temperatureAir(ReceiveUtils.getTemp(list.get(0)))
                .humidityAir(ReceiveUtils.getHum(list.get(1)))
                .temperatureSoil(ReceiveUtils.getTemp(list.get(2)))
                .humiditySoil(ReceiveUtils.getHum(list.get(3)))
                .light(Integer.parseInt(list.get(4), 16) + "")
                .co2(Integer.parseInt(list.get(5), 16) + "")
                .updateTime(new Date())
                .build();
        if (list.size()>6 ){
                build =build.toBuilder()
                        .autocontrolType(ReceiveUtils.getTemp(list.get(6)))
                        .autocontrolTypeEnd(ReceiveUtils.getTemp(list.get(7)))
                        .build();
        }
        if (list.size() > 8) {
                build = build.toBuilder()
                    .conductivity(Integer.parseInt(list.get(8), 16) + "")
                    .ph(ReceiveUtils.getHum(list.get(9)))
                    .nitrogen(Integer.parseInt(list.get(10), 16) + "")
                    .phosphorus(Integer.parseInt(list.get(11), 16) + "")
                    .potassium(Integer.parseInt(list.get(12), 16) + "")
                    .build();
        }
        DbTcpType i = dbTcpTypeService.selectDbTcpTypeByHeartName(heartbeatText);
        if (i == null) {
            dbTcpTypeService.insertDbTcpType(build);
        }else {
            dbTcpTypeService.updateDbTcpTypeSensorData(build);
        }
        socketMsgUtils.sensorMsgSend(build,MessageReturnTypeConstant.SENSOR_STATE);
        log.warn("传感器温度采集  "+build.getHeartName()+":"+ build.toString());
    }


    public static void main(String[] args){
        String s = "C8100001001428 145D 04B0 0005 010C0131000000000020025B02580064006400640064006401F401F40000000000001AB1";
        String s1 = "000000000011001200000000028E0141002D000D000E00100013";
        System.out.println(s1.substring(6, 30));
        System.out.println(hexToBinString("0001"));
        String str  = "0000000000000001".split("")[15];
        System.out.println(str);
        System.out.println(0 == 0? 1 : 1 == 1? 0 : 1);
        System.out.println(s.substring(22, 26));

        if (!StringUtils.isEmpty(s1) && s1.equals("000000000011001200000000028E0141002D000D000E00100013")){
            System.out.println("===================");
        }
        String i = "0";
        int flag = Integer.parseInt(i) == 0? 1 :0;
        System.out.println(flag);
    }

}
