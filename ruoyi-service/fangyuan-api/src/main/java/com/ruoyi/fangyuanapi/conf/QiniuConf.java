package com.ruoyi.fangyuanapi.conf;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class QiniuConf {

    @Value("${com.fangyuan.qiniu.image.url}")
    private String imageUrl;

    @Value("${com.fangyuan.qiniu.image.filter}")
    private String imageFilter;

    @Value("${com.fangyuan.qiniu.video.url}")
    private String videoUrl;

    @Value("${com.fangyuan.qiniu.video.filter}")
    private String videoFilter;

    @Value("${com.fangyuan.qiniu.video.checkSelect}")
    private String checkSelect;

    @Value("${com.fangyuan.qiniu.host}")
    private String host;

    @Value("${com.fangyuan.qiniu.accessKey}")
    private String accessKey;

    @Value("${com.fangyuan.qiniu.secretKey}")
    private String secretKey;

    @Value("${com.fangyuan.qiniu.contentType}")
    private String contentType;


}
