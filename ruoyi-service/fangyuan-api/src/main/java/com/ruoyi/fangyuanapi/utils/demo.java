package com.ruoyi.fangyuanapi.utils;

import com.alibaba.fastjson.JSON;
import com.ruoyi.system.domain.OperatePojo;

import java.util.List;

public class demo {

    public static void main(String[] args) {
        String a="[{\"checkCode\":\"1\",\"checkName\":\"卷帘1\",\"spList\":[{\"handleCode\":\"15,160,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,160,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,161,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,161,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"1\",\"checkName\":\"卷帘2\",\"spList\":[{\"handleCode\":\"15,162,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,162,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,163,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,163,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"2\",\"checkName\":\"通风1\",\"spList\":[{\"handleCode\":\"15,164,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,164,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,165,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,165,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"2\",\"checkName\":\"通风2\",\"spList\":[{\"handleCode\":\"15,166,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,166,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,167,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,167,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"3\",\"checkName\":\"补光\",\"spList\":[{\"handleCode\":\"15,168,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,168,00,00\",\"handleName\":\"start_stop\"}]},{\"checkCode\":\"4\",\"checkName\":\"浇水\",\"spList\":[{\"handleCode\":\"15,169,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,169,00,00\",\"handleName\":\"start_stop\"}]}]";
        List<OperatePojo> pojos = JSON.parseArray(a, OperatePojo.class);
        System.out.println(pojos);

    }
}
