package com.ruoyi.system.feign;


import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteOssFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/*
* 文件上传服务
* */
@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteOssFallbackFactory.class)
public interface RemoteOssService {

    @PostMapping("oss/upload")
    public R editSave(@RequestParam("file") MultipartFile file);



}
