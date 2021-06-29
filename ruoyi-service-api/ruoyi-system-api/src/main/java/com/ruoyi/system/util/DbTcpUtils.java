package com.ruoyi.system.util;

import com.ruoyi.common.core.domain.SensorDeviceDto;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.DbTcpType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.util.DbTcpUtils.java
 * @Description
 * @createTime 2021年06月21日 10:41:00
 */
public class DbTcpUtils {
    
    public static List<SensorDeviceDto> getSensorResult(DbTcpType tcpType){
        ArrayList<SensorDeviceDto> sensorDeviceDtos = new ArrayList<>();
        if (strIsNotEmpty(tcpType.getTemperatureSoil())) {
            sensorDeviceDtos.add(getSensorDeviceDto("土壤温度",tcpType.getTemperatureSoil(),"°C","https://cdn.fangyuancun.cn/fangyuan/20210601/efa57a9e51ae4b3499a513b5fa1cef9e.png"));
        }
        if (strIsNotEmpty(tcpType.getHumiditySoil())) {
            sensorDeviceDtos.add(getSensorDeviceDto("土壤湿度",tcpType.getHumiditySoil(),"%","https://cdn.fangyuancun.cn/fangyuan/20210601/0c35dfc7b95c48898fadc12e7780c0ad.png"));
        }
        if (strIsNotEmpty(tcpType.getLight())) {
            sensorDeviceDtos.add(getSensorDeviceDto("光照",tcpType.getLight(),"Lux","https://cdn.fangyuancun.cn/fangyuan/20210531/5a67f20d23c943abb9ad79eda6f3ede9.png"));
        }
        if (strIsNotEmpty(tcpType.getCo2())) {
            sensorDeviceDtos.add(getSensorDeviceDto("二氧化碳",tcpType.getCo2()," PPM","https://cdn.fangyuancun.cn/fangyuan/20210531/b4ca796f1c37452eb836c06ce9928f4c.png"));
        }
        if (strIsNotEmpty(tcpType.getTemperatureAir())) {
            sensorDeviceDtos.add(getSensorDeviceDto("空气温度",tcpType.getTemperatureAir(),"°C","https://cdn.fangyuancun.cn/fangyuan/20210531/1344157531f14ec09f854fd5d1c66131.png"));
        }
        if (strIsNotEmpty(tcpType.getHumidityAir())) {
            sensorDeviceDtos.add(getSensorDeviceDto("空气湿度",tcpType.getHumidityAir(),"%","https://cdn.fangyuancun.cn/fangyuan/20210531/bd9c257c50eb473c98e2e833be573c84.png"));
        }
        if (strIsNotEmpty(tcpType.getConductivity())) {
            sensorDeviceDtos.add(getSensorDeviceDto("电导率",tcpType.getConductivity(),"us/cm","https://cdn.fangyuancun.cn/fangyuan/20210531/f413a1f4dfc74ee38d2d51c9d8dad0df.png"));
        }
        if (strIsNotEmpty(tcpType.getPh())) {
            sensorDeviceDtos.add(getSensorDeviceDto("PH",tcpType.getPh(),"","https://cdn.fangyuancun.cn/fangyuan/20210531/5572d3cb9b99427aa621a95c631f87f6.png"));
        }
        if (strIsNotEmpty(tcpType.getNitrogen())) {
            sensorDeviceDtos.add(getSensorDeviceDto("氮",tcpType.getNitrogen(),"mg/L","https://cdn.fangyuancun.cn/fangyuan/20210531/6cef706ab57c442db9f3af4fbbd14b36.png"));
        }
        if (strIsNotEmpty(tcpType.getPhosphorus())) {
            sensorDeviceDtos.add(getSensorDeviceDto("磷",tcpType.getPhosphorus(),"mg/L","https://cdn.fangyuancun.cn/fangyuan/20210531/0fa6b43d9afd49f485beb1c866c5543e.png"));
        }
        if (strIsNotEmpty(tcpType.getPotassium())) {
            sensorDeviceDtos.add(getSensorDeviceDto("钾",tcpType.getPotassium(),"mg/L","https://cdn.fangyuancun.cn/fangyuan/20210531/a8650b01fb014101bc8e4793070d5555.png"));
        }
        return sensorDeviceDtos;
    }

    /**
     * 大同传感器数据封装
     * @param tcpType 传感器数据
     * @return
     */
    public static List<SensorDeviceDto> getSensorResultForDaTong(DbTcpType tcpType){
        ArrayList<SensorDeviceDto> sensorDeviceDtos = new ArrayList<>();
        if (strIsNotEmpty(tcpType.getTemperatureSoil())) {
            sensorDeviceDtos.add(getSensorDeviceDto("土壤温度",tcpType.getTemperatureSoil(),"°C","https://cdn.fangyuancun.cn/fangyuan/20210628/767a771e5c99405fb249dcccb498f990.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("土壤温度","0","°C","https://cdn.fangyuancun.cn/fangyuan/20210628/767a771e5c99405fb249dcccb498f990.png"));
        }

        if (strIsNotEmpty(tcpType.getHumiditySoil())) {
            sensorDeviceDtos.add(getSensorDeviceDto("土壤湿度",tcpType.getHumiditySoil(),"%","https://cdn.fangyuancun.cn/fangyuan/20210628/277c78fd82a3496f83029d1dd9f55b30.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("土壤湿度","0","%","https://cdn.fangyuancun.cn/fangyuan/20210628/277c78fd82a3496f83029d1dd9f55b30.png"));
        }

        if (strIsNotEmpty(tcpType.getLight())) {
            sensorDeviceDtos.add(getSensorDeviceDto("光照",tcpType.getLight(),"Lux","https://cdn.fangyuancun.cn/fangyuan/20210628/4492838e5a814eb8a4414a2f3a3e8a7c.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("光照","0","Lux","https://cdn.fangyuancun.cn/fangyuan/20210628/4492838e5a814eb8a4414a2f3a3e8a7c.png"));
        }

        if (strIsNotEmpty(tcpType.getCo2())) {
            sensorDeviceDtos.add(getSensorDeviceDto("二氧化碳",tcpType.getCo2()," PPM","https://cdn.fangyuancun.cn/fangyuan/20210628/81ae10b3271f493ba69d2b2396a9ec84.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("二氧化碳","0"," PPM","https://cdn.fangyuancun.cn/fangyuan/20210628/81ae10b3271f493ba69d2b2396a9ec84.png"));
        }

        if (strIsNotEmpty(tcpType.getTemperatureAir())) {
            sensorDeviceDtos.add(getSensorDeviceDto("空气温度",tcpType.getTemperatureAir(),"°C","https://cdn.fangyuancun.cn/fangyuan/20210628/e07cf4c4e419408dbc54e7f60dd89dcb.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("空气温度","0","°C","https://cdn.fangyuancun.cn/fangyuan/20210628/e07cf4c4e419408dbc54e7f60dd89dcb.png"));
        }

        if (strIsNotEmpty(tcpType.getHumidityAir())) {
            sensorDeviceDtos.add(getSensorDeviceDto("空气湿度",tcpType.getHumidityAir(),"%","https://cdn.fangyuancun.cn/fangyuan/20210628/cb0628560a5d49adb4acc6cd9600467e.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("空气湿度","0","%","https://cdn.fangyuancun.cn/fangyuan/20210628/cb0628560a5d49adb4acc6cd9600467e.png"));
        }

        if (strIsNotEmpty(tcpType.getConductivity())) {
            sensorDeviceDtos.add(getSensorDeviceDto("电导率",tcpType.getConductivity(),"us/cm","https://cdn.fangyuancun.cn/fangyuan/20210628/d2de071cb842479694fc27b3729239ff.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("电导率","0","us/cm","https://cdn.fangyuancun.cn/fangyuan/20210628/d2de071cb842479694fc27b3729239ff.png"));
        }

        if (strIsNotEmpty(tcpType.getPh())) {
            sensorDeviceDtos.add(getSensorDeviceDto("PH",tcpType.getPh(),"","https://cdn.fangyuancun.cn/fangyuan/20210628/b57b3dcc10804d9d83b1bbdabdac50b6.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("PH","0","","https://cdn.fangyuancun.cn/fangyuan/20210628/b57b3dcc10804d9d83b1bbdabdac50b6.png"));
        }

        if (strIsNotEmpty(tcpType.getNitrogen())) {
            sensorDeviceDtos.add(getSensorDeviceDto("氮",tcpType.getNitrogen(),"mg/L","https://cdn.fangyuancun.cn/fangyuan/20210628/e133e2c951624edfb6c30046e9a7c876.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("氮","0","mg/L","https://cdn.fangyuancun.cn/fangyuan/20210628/e133e2c951624edfb6c30046e9a7c876.png"));
        }

        if (strIsNotEmpty(tcpType.getPhosphorus())) {
            sensorDeviceDtos.add(getSensorDeviceDto("磷",tcpType.getPhosphorus(),"mg/L","https://cdn.fangyuancun.cn/fangyuan/20210628/36a06360fc5f4dc1aaedff18432ffff9.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("磷","0","mg/L","https://cdn.fangyuancun.cn/fangyuan/20210628/36a06360fc5f4dc1aaedff18432ffff9.png"));
        }

        if (strIsNotEmpty(tcpType.getPotassium())) {
            sensorDeviceDtos.add(getSensorDeviceDto("钾",tcpType.getPotassium(),"mg/L","https://cdn.fangyuancun.cn/fangyuan/20210628/b615dd08871944fa974f33c7d35a510b.png"));
        }else{
            sensorDeviceDtos.add(getSensorDeviceDto("钾","0","mg/L","https://cdn.fangyuancun.cn/fangyuan/20210628/b615dd08871944fa974f33c7d35a510b.png"));
        }

        sensorDeviceDtos.add(getSensorDeviceDto("叶面湿度","0","°C","https://cdn.fangyuancun.cn/fangyuan/20210628/f307f0c54fd5485480d402b60a5a6862.png"));
        return sensorDeviceDtos;
    }



    private static boolean strIsNotEmpty(String str) {
        if (StringUtils.isNotEmpty(str) && !"0.0".equals(str) && !"0".equals(str)) {
            return true;
        }
        return false;
    }

    private static SensorDeviceDto getSensorDeviceDto(String name , String value, String unit, String icon){
        return  SensorDeviceDto.builder()
                .deviceName(name)
                .value(value)
                .unit(unit)
                .icon(icon)
                .build();
    }
    
}
