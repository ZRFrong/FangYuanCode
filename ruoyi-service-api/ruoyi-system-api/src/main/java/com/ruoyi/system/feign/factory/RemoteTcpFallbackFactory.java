package com.ruoyi.system.feign.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.RemoteTcpService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RemoteTcpFallbackFactory implements FallbackFactory<RemoteTcpService> {

    @Override
    public RemoteTcpService create(Throwable cause) {

        return new RemoteTcpService() {

            @Override
            public R operation(DbOperationVo dbOperationVo) {
                return null;
            }

            @Override
            public List<DbTcpType> list(DbTcpType dbTcpType) {
                return null;
            }

            @Override
            public R strtTiming() {
                return null;
            }

            @Override
            public R startSaveTiming() {
                return null;
            }

            @Override
            public R operateTongFengType(DbEquipment dbEquipment, int i, String temp) {
                return null;
            }

            @Override
            public R operateTongFengHand(DbEquipment dbEquipment, int i) {
                return null;
            }

            @Override
            public R operationList(List<DbOperationVo> dbOperationVo) {
                return null;
            }
        };

    }
}
