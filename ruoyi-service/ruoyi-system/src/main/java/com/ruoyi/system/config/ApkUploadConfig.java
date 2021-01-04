package com.ruoyi.system.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApkUploadConfig {
    @Value("${com.upload.path}")
    private String path;
}
