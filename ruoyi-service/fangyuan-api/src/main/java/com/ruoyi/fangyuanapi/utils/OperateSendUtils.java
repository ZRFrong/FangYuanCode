package com.ruoyi.fangyuanapi.utils;

import java.util.Date;

import com.google.common.collect.Maps;


import cn.hutool.core.thread.ThreadUtil;
import com.ruoyi.common.json.JSON;
import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.utils.HttpUtil;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.dto.TcpType;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.fangyuanapi.service.IDbEquipmentTempService;
import com.ruoyi.system.domain.*;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/*
 * 访问远程发送接口
 * */
public class OperateSendUtils {


    /*
     *
     * */
    public static String send(String address, String text) throws Exception {

        String host = "http://115.28.136.205:9009";

        String path = "/tcp/tcpOrder/query";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("address", address);
        querys.put("text", text);
        HttpResponse httpResponse = HttpUtil.doGet(host, path, method, headers, querys);
        String s = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(s);
        return s;
    }

    public static String sendStart(String address) throws Exception {

        String host = "http://115.28.136.205:9009";

        String path = "/tcp/tcpOrder/stateList";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("address", address);
        HttpResponse httpResponse = HttpUtil.doGet(host, path, method, headers, querys);
        String s = EntityUtils.toString(httpResponse.getEntity());
        return s;
    }



    public static String doget(DbOperationVo dbOperationVo) throws Exception {
        String heartName = dbOperationVo.getHeartName();
        String facility = dbOperationVo.getFacility();
        String address = heartName + "_" + tostringnum(Integer.parseInt(facility));
        String text = dbOperationVo.getOperationText();

        return send(address, text);
    }


    public static int operationList(List<DbOperationVo> dbOperationVo) {

        //       根据心跳分组
        Map<String, List<DbOperationVo>> mps = dbOperationVo.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
//         多个map依次执行（多线程）
        int query = queryIoList(mps);

        return query;
    }

    private static ExecutorService executorService = null;

    /*
     * 多线程依次执行
     * */
    public static int queryIoList(Map<String, List<DbOperationVo>> mps) {
        Set<String> strings = mps.keySet();
//    新建几条线程
        executorService = ThreadUtil.newExecutor(strings.size());
        try {
            strings.forEach(ite -> send(mps.get(ite)));
            executorService.shutdown();
            while (!executorService.isTerminated()) {
//            等待执行完成再返回
            }
            return 1;
        } catch (Exception e) {
            executorService.shutdown();
            e.printStackTrace();
            return 0;
        }
    }


    private static void send(List<DbOperationVo> dbOperationVos) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
//                    循环list
//                    int query = query(dbOperationVo);
                for (int i = 0; i < dbOperationVos.size(); i++) {
                    System.out.println(dbOperationVos.get(i) + "执行了");
                    try {
                        doget(dbOperationVos.get(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    线程礼让让其他的先执行  睡眠500毫秒
                    if (i < dbOperationVos.size()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


    }


    public static DbTcpType StateList(String address) throws Exception {
        String s = sendStart(address);
        TcpType tcpType = com.alibaba.fastjson.JSON.parseObject(s, TcpType.class);
        DbTcpType dbTcpType = new DbTcpType();
        dbTcpType.setHeartName(tcpType.getAddress());
        dbTcpType.setLight(tcpType.getLight());
        dbTcpType.setTemperatureAir(tcpType.getTemperature());
        dbTcpType.setHumidityAir(tcpType.getHumidity());

        return dbTcpType;
    }

    public static void deom() {


        String[] arr = new String[]{"卷帘1", "卷帘2", "通风1", "通风2", "补光", "浇水"};
        String[] arr2 = new String[]{"start", "start_stop", "down", "down_stop"};
        List<OperatePojo> pojos = new ArrayList<>();
        int num = 160;
        int num2 = 0;
        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                for (int j = 0; j < 2; j++) {
                    OperatePojo operatePojo1 = new OperatePojo();
                    operatePojo1.setCheckCode((i + 1) + "");
                    operatePojo1.setCheckName(arr[num2]);
                    num2++;
                    List<OperatePojo.OperateSp> list2 = new ArrayList<>();
                    for (int k = 0; k < 4; k++) {
                        String no1 = "15,";
                        String no2 = tostringnum(num);
                        String no3 = "";
                        if (k == 1 || k == 3) {
                            no3 = ",00,00";
                            num++;
                        } else {
                            no3 = ",255,00";
                        }

                        String handlecode = no1 + no2 + no3;
                        OperatePojo.OperateSp operateSp1 = new OperatePojo.OperateSp(arr2[k], handlecode);
                        list2.add(operateSp1);
                    }
                    operatePojo1.setSpList(list2);
                    pojos.add(operatePojo1);
                }
            } else {
                OperatePojo operatePojo1 = new OperatePojo();
                operatePojo1.setCheckCode((i + 1) + "");
                operatePojo1.setCheckName(arr[num2]);
                num2++;
                List<OperatePojo.OperateSp> list2 = new ArrayList<>();
                for (int k = 0; k < 2; k++) {
                    String no1 = "15,";
                    String no2 = tostringnum(num);
                    String no3 = "";
                    if (k == 1 || k == 3) {
                        no3 = ",00,00";
                        num++;
                    } else {
                        no3 = ",255,00";
                    }
                    String handlecode = no1 + no2 + no3;
                    OperatePojo.OperateSp operateSp1 = new OperatePojo.OperateSp(arr2[k], handlecode);
                    list2.add(operateSp1);
                }
                operatePojo1.setSpList(list2);
                pojos.add(operatePojo1);
            }
        }

        String s = com.alibaba.fastjson.JSON.toJSONString(pojos);

        DbEquipmentTemp dbEquipmentTemp = new DbEquipmentTemp();
        dbEquipmentTemp.setTempName("两卷膜，两卷帘，补光，浇水");
        dbEquipmentTemp.setHandleText(s);
        dbEquipmentTemp.setPlcVersion("基地址160");
        int i = SpringUtils.getBean(IDbEquipmentTempService.class).insertDbEquipmentTemp(dbEquipmentTemp);
        System.out.println(i);


    }

    private static String tostringnum(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return num + "";
        }

    }
}
