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



}
