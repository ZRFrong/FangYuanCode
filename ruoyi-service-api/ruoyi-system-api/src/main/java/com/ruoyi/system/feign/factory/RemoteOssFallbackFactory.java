package com.ruoyi.system.feign.factory;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.RemoteOssService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class RemoteOssFallbackFactory  implements FallbackFactory<RemoteOssService> {


    @Override
    public RemoteOssService create(Throwable throwable) {
        return new RemoteOssService()
        {


            @Override
            public R editSave(MultipartFile file) {
                return R.error();
            }
        };

    }
}
