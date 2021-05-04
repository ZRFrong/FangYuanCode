package com.ruoyi.fangyuantcp.processingCode;


/*
 * 一些固定指令的枚举
 * */
public class TcpOrderTextConf {

    public static final String SinceOrhand = "00,00,00,01";

    /*
     * 查询自动通风是否开启
     * */
    public static final String SinceOrhandTongFeng = "01,244,00,01";


    /*
     * 查询当前自动通风开始和关闭的温度
     * */
    public static final String SinceOrhandTongFengType = "00,200,00,02";

    /*
     * 更改自动手动通风操作 开
     * */
    public static final String operateTongFeng = "01,244,255,00";

    public static final String TaskOnline = "00,00,00,01";


    /*更改自动手动通风操作 关*/
    public static String operateTongFengOver = "01,244,00,00";


    /*
     * 更改自动手动通风温度操作 开
     * */
    public static final String operateTongFengType = "00,200";


    /*更改自动手动通风温度操作 关*/
    public static String operateTongFengOverType = "00,201";




    /*
     * 施肥量更改 00 C8 01 2C   00,200,01，44
     * */
    public static String operateSewageFertilization = "00,200,01,44";

    /*
     * 稀释度更改 00 C8 01 2C   00,200,01，44
     * */
    public static String operateSewageDilutability = "00,201,01,44";

    /*
     *开启  水肥
     * */
    public  static  String operateSewageOn="255,00";

    /*
    * 关闭 水肥
    * */
    public  static  String operateSewageOff="255,00";

    /*
    * 装态查询
    * */
    public  static  String stateSave="00,100,00,06";

}
