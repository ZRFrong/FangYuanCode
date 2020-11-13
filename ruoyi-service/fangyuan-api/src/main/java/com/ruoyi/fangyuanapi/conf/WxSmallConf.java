package com.ruoyi.fangyuanapi.conf;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class WxSmallConf {
    @Value("${com.fangyuan.wechat.appid}")
    private String appid;
    @Value("${com.fangyuan.wechat.secret}")
    private String secret;
    @Value("${com.fangyuan.wechat.host}")
    private String host;
    @Value("${com.fangyuan.wechat.path}")
    private String path;
    @Value("${com.fangyuan.wechat.grant_type}")
    private String grant_type;
}
