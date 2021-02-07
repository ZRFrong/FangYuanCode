package com.ruoyi.fangyuanapi.conf;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ControlSystemConf {
    @Value("#{${com.fangyuan.control.system.map}}")
    private Map<Integer,String> map;

    public ControlSystemConf() {
    }

    @Override
    public String toString() {
        return "ControlSystemConf{" +
                "map=" + map +
                '}';
    }

    public ControlSystemConf(Map<Integer, String> map) {

        this.map = map;
    }

    public Map<Integer, String> getMap() {

        return map;
    }

    public void setMap(Map<Integer, String> map) {
        this.map = map;
    }
}
