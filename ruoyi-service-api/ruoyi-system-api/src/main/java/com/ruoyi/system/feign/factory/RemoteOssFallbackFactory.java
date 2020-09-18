package com.ruoyi.system.feign.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.SysDept;
import com.ruoyi.system.feign.RemoteDeptService;
import com.ruoyi.system.feign.RemoteOssService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class RemoteOssFallbackFactory  implements FallbackFactory<RemoteOssService> {


    @Override
    public RemoteOssService create(Throwable throwable) {
        return new RemoteOssService()
        {


            @Override
            public R editSave(MultipartFile file) {

                return null;
            }
        };

    }
}
