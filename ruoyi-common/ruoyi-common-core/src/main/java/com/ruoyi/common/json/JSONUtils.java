package com.ruoyi.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class JSONUtils <T,V> {
    private static final ObjectMapper obj = new ObjectMapper();

    public  Map<T,V> stringToMap(String json)  {
        Map<T, V> map = null;
        try {
            map = obj.readValue(json, new TypeReference<Map<T, V>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("json转换异常");
        }
        return map;
    }
    public String mapToString(Map<T,V> data)  {
        String s = null;
        try {
            s = obj.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("json转换异常");
        }
        return s;
    }

    /**
     * list 转json数组
     * @param data
     * @return
     */
    public String listToJsonArray(List data){
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(data));

        return array.toJSONString();
    }



    public static void main(String[] args) throws JsonProcessingException {

        ArrayList<String> strings = new ArrayList<>();
        strings.add("15135006102");
        strings.add("15135002102");
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(strings));
        System.out.println(array.toJSONString());

//        JSONUtils<String, String> jsonUtils = new JSONUtils<>();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("code", "1234");
//        String s = obj.writeValueAsString(map);
//        System.out.println(s);
//        System.out.println(obj.writeValueAsString(map));
//        System.out.println(jsonUtils.mapToString(map));
//        String s ="{\"Message\":\"OK\",\"RequestId\":\"7402B09A-F12E-4655-96DF-8F053F6D5CAD\",\"BizId\":\"975423199111996661^0\",\"Code\":\"OK\"}";
//        JSONUtils<String, String> jsonUtils = new JSONUtils<>();
//        Map<String, String> map = jsonUtils.stringToMap(s);
//        System.out.println(map.get("Code"));
    }
}
