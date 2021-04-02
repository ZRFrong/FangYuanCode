package com.ruoyi.fangyuanapi.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RefreshScope
@Data
public class ControlSystemConf {
    @Value("#{${com.fangyuan.control.system.map}}")
    private Map<Integer,String> map;

    @Value("#{${com.fangyuan.control.system.voideUrl}}")
    private Map<String,String> voideUrl;
}
