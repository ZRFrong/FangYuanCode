package com.ruoyi.fangyuanapi.conf;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MultipartFileConf {

    @Bean
    public MultipartConfigElement configElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //最大上传限制 15m
        factory.setMaxFileSize(DataSize.ofMegabytes(15));
        //factory.setMaxRequestSize(DataSize.ofGigabytes(10L));
        //总上传大小限制
        factory.setMaxRequestSize(DataSize.ofMegabytes(15));

        return factory.createMultipartConfig();
    }

}
