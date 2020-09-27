package com.ruoyi.system.feign.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.feign.RemoteTcpService;
import feign.hystrix.FallbackFactory;

import java.util.List;

public class RemoteTcpFallbackFactory implements FallbackFactory<RemoteTcpService> {

    @Override
    public RemoteTcpService create(Throwable cause) {

        return new RemoteTcpService()
        {

            @Override
            public R operation(DbOperationVo dbOperationVo) {
                return null;
            }

            @Override
            public R operationList(List<DbOperationVo> dbOperationVo) {
                return null;
            }
        };

    }
}
