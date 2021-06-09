package com.ruoyi.fangyuantcp.processingCode;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpTypeService;
import com.ruoyi.fangyuantcp.utils.LogOrderUtil;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.DbEquipmentComponentClient;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
        logOrderUtil.recordFollowBack(ReceiveUtil.getname(ctx), s,"HexTest.messageActive");
        String heartbeatText = ReceiveUtil.getname(ctx);
        if (StringUtils.isEmpty(heartbeatText)) {
            return;
        }
        /** C8 10 00 00 00 14 28   14               头信息 包含设备号 没处理
         2B C1                   4               程序版本号  没处理
         00 3C                   4              发送时间间隔 没处理
         00 01                   4               远程本地加风口自动手动  没处理
         00 D4 00 CA 00 00 00 00 00 22 02 BB    传感器数据     没处理
         00 D4 00 CA 00 00 00 00 00 22 02 BB
         00 1E  01 F4                           开关风口温度    没处理
         00 32 13 88 01 F4 01 F4 01 F4 01 F4   两卷帘4卷膜百分比显示    卷帘1 卷帘2 卷膜1 卷膜2 卷膜3 卷膜4     没处理
         00 E0   二进制    两卷帘四卷膜开关装态  （补光，浇水，配药，打药只能在远程的时候获取到）0关1开      没处理
         00 03 00 00 补光定时
         60 D2*/
        /**
         * C8100001001428 145B 04B0 0005 011B 013F 0000 0000 0008 01C3 012C 0064 0064 01F4 004B 005A 01F4 01F4 0000 0000 0000 9197
         * C8100001001428 145D 04B0 0005 010C 0131 0000 0000 0020 025B 0258 0064 0064 0064 0064 0064 01F4 01F4 0000 0000 0000 1AB1
         * */
        /**
         * 传感器数据 及自动通风温度状态修改
         * */
        String sensorData = s.substring(26, 58);
        if (s.length() > 98) {
            sensor(sensorData + s.substring(102, 122), heartbeatText);
        }
        /**
         * 进度条解析
         * */
        progressAnalysis(s.substring(58, 82), heartbeatText);
        /**
         * 各个功能状态 这里只对补光做了处理
         * */
        lightStatus(s.substring(82, 94), heartbeatText);
        daYaoStatus(s.substring(82, 94), heartbeatText);
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
        List<String> list = strSplit(hexStr, 4);
        String string = HexTest.hexToBinString(list.get(0));
        log.info("lightStatus.string============================={}", string);
        Integer fillLightTimingStatus = null;
        if (Integer.parseInt(list.get(1), 16) != 0 || Integer.parseInt(list.get(2), 16) != 0) {
            fillLightTimingStatus = 0;
        }
        //dbEquipmentComponentClient.selectByHeartbeatText("");
        dbEquipmentComponentClient.modifyLightStatus(heartbeatText, string.substring(string.length() - 13, string.length() - 12), fillLightTimingStatus);

    }

    /**
     * 关于打药开关状态解析
     *
     * @param hexStr
     * @param heartbeatText
     * @since: 2.0.0
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/5/21 17:06
     * @sign: 他日若遂凌云志, 敢笑黄巢不丈夫。
     */
    private void daYaoStatus(String hexStr, String heartbeatText) {
        List<String> list = strSplit(hexStr, 4);
        String string = HexTest.hexToBinString(list.get(0));
        log.info("daYaoStatus.string============================={}", string);
        String states = string.substring(12, 15);
        Integer fillLightTimingStatus = null;
        if (Integer.parseInt(list.get(1), 16) != 0 || Integer.parseInt(list.get(2), 16) != 0) {
            fillLightTimingStatus = 0;
        }
        //dbEquipmentComponentClient.selectByHeartbeatText("");
        dbEquipmentComponentClient.modifyFunctionLogoStatus(heartbeatText, "打药", string.substring(string.length()-16 , string.length() - 15), fillLightTimingStatus);
        dbEquipmentComponentClient.modifyFunctionLogoStatus(heartbeatText, "浇水", string.substring(string.length() - 14, string.length() - 13), fillLightTimingStatus);
        dbEquipmentComponentClient.modifyFunctionLogoStatus(heartbeatText, "配药", string.substring(string.length() - 16, string.length() - 15), fillLightTimingStatus);
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
        List<String> list = HexTest.strSplit(data, 4);
        dbEquipmentComponentClient.progressAnalysis(list, heartbeatText);
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
    public void sensor(String s, String heartbeatText) {
        List<String> list = HexTest.strSplit(s, 4);
        if (list == null) {
            return;
        }
        DbTcpType build = DbTcpType.builder()
                .heartName(heartbeatText +"_"+ dbEquipmentComponentClient.selectByHeartbeatText(heartbeatText))
                .temperatureAir(ReceiveUtil.getTemp(list.get(0)))
                .humidityAir(ReceiveUtil.getHum(list.get(1)))
                .temperatureSoil(ReceiveUtil.getTemp(list.get(2)))
                .humiditySoil(ReceiveUtil.getHum(list.get(3)))
                .light(Integer.parseInt(list.get(4), 16) + "")
                .co2(Integer.parseInt(list.get(5), 16) + "")
                .updateTime(new Date())
                .build();
        if (list.size()>6){
                build =build.toBuilder()
                        .autocontrolType(ReceiveUtil.getTemp(list.get(6)))
                        .autocontrolTypeEnd(ReceiveUtil.getTemp(list.get(7)))
                        .build();
        }
        if (list.size() > 8) {
                build = build.toBuilder()
                    .conductivity(Integer.parseInt(list.get(8), 16) + "")
                    .ph(ReceiveUtil.getHum(list.get(9)))
                    .nitrogen(Integer.parseInt(list.get(10), 16) + "")
                    .phosphorus(Integer.parseInt(list.get(11), 16) + "")
                    .potassium(Integer.parseInt(list.get(12), 16) + "")
                    .build();
        }
        log.warn("传感器温度采集  "+heartbeatText+":"+ build.toString());
        Integer i = dbTcpTypeService.selectDbTcpTypeByHeartName(heartbeatText);
        if (i == null) {
            dbTcpTypeService.insertDbTcpType(build);
        }
        dbTcpTypeService.updateDbTcpTypeSensorData(build);
    }

    public static void main(String[] args){
        String s = "C8100001001428145D04B00005010C0131000000000020025B02580064006400640064006401F401F40000000000001AB1";
        String s1 = "01030C010B01C800000000001403386CB1";

        System.out.println(s1.substring(6, 30));

    }

}
