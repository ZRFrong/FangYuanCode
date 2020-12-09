package com.ruoyi.system.feign.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.feign.RemoteApiService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteApiFallbackFactory implements FallbackFactory<RemoteApiService> {


    @Override
    public RemoteApiService create(Throwable throwable)
    {
        log.error(throwable.getMessage());
        return new RemoteApiService()
        {

            @Override
            public R startSaveTiming() {
                return null;
            }

            @Override
            public R abnormalInfoSave(DbAbnormalInfo dbAbnormalInfo) {
                return null;
            }

            @Override
            public R saveEquimentOperation(DbAbnormalInfo dbAbnormalInfo) {
                return null;
            }


        };
    }
}
