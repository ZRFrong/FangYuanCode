package com.ruoyi.fangyuanapi.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZHAOXIAOSI
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.conf.Almanac.java
 * @Description
 * @createTime 2021年04月24日 11:07:00
 */
@Configuration
@Data
public class AlmanacConf {

    @Value("${com.fangyuan.almanac.host}")
    private String host;

    @Value("${com.fangyuan.almanac.url}")
    private String url;

    @Value("${com.fangyuan.almanac.key}")
    private String key;
}
