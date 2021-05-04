package com.ruoyi.fangyuantcp.processingCode;

import com.ruoyi.system.domain.DbTcpType;

import java.util.List;

/**
 * @Description: 传感数据处理
 * @Author zheng
 * @Date 2021/2/26 9:54
 * @Version 1.0
 */
public class SensingTypeUtils {


    /*
     * 温湿度等传感温度处理
     * */
    public static void storageMessage(List<String> arr, int i1, DbTcpType dbTcpType) {
        for (int i = 0; i < (i1 / 2); i++) {
            switch (i) {
                case 0:
//                      空气  温度
                    dbTcpType.setTemperatureAir(getTemp(arr.get(i * 2) + arr.get(i * 2 + 1)));
                case 1:
//                    空气     湿度
                    dbTcpType.setHumidityAir(getHum(arr.get(i * 2) + arr.get(i * 2 + 1)));

                case 2:
//                土壤   温度
                    dbTcpType.setTemperatureSoil(getHum(arr.get(i * 2) + arr.get(i * 2 + 1)));
                case 3:
                    //                土壤   湿度
                    dbTcpType.setHumiditySoil(getTemp(arr.get(i * 2) + arr.get(i * 2 + 1)));
                case 4:
                    //                光照
                    dbTcpType.setLight(getLight(arr.get(i * 2) + arr.get(i * 2 + 1)));

                case 5:
//                    二氧化碳
//                    二氧化碳
                    dbTcpType.setCo2(getLight(arr.get(i * 2) + arr.get(i * 2 + 1)));
            }

//            目前5个
        }
    }


    /*
     *本地远程处理
     * */
    public static int sinceOrHandRead(List<String> arr) {
        int i = Integer.parseInt(arr.get(3) + arr.get(4), 16);
        return i < 450 ? 1 : 0;

    }

    /*
     *自动通风处理
     * */

    /*
     * 自动通风温湿度处理
     * */
    public static void returnautocontrolType(List<String> arr,  DbTcpType dbTcpType) {
        dbTcpType.setAutocontrolType(getTemp(arr.get(0) + arr.get(1)));
        dbTcpType.setAutocontrolTypeEnd(getTemp(arr.get(2) + arr.get(3)));
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
}
