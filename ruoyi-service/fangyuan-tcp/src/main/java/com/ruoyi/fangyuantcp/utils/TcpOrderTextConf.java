package com.ruoyi.fangyuantcp.utils;


/*
* 一些固定指令的枚举
* */
public class   TcpOrderTextConf {

    public   static final String SinceOrhand="00,00,00,01";

    /*
    * 查询自动通风是否开启
    * */
    public   static final String SinceOrhandTongFeng="01,244,00,01";


    /*
    * 查询当前自动通风开始和关闭的温度
    * */
    public   static final String SinceOrhandTongFengType="01,244,00,01";


    /*
     * 更改自动手动通风操作 开
     * */
    public   static final String operateTongFeng="01,244,255,00";


    /*关*/
    public static String operateTongFengOver="01,244,00,00";

    /*
     * 更改自动手动通风温度操作 开
     * */
    public   static final String operateTongFengType="00,76";


    /*关*/
    public static String operateTongFengOverType="00,77";
}
