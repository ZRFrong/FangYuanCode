package com.ruoyi.system.feign.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpClient;
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
            public R sinceOrHand() {
                return null;
            }

            @Override
            public void curingTiming() {

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
            public R operateLight(String heartbeatText, String equipmentNo, Integer flag, Long startTime, Long stopTime) {
                return null;
            }

            @Override
            public R percentageOperate(String heartbeatText, String equipmentNo, Integer percentage, String operateText) {
                return null;
            }


            @Override
            public R operateTongFengType(String heartbeatText, String equipmentNo, Integer i, String temp) {
                return null;
            }

            @Override
            public R operateTongFengHand(String heartbeatText, String equipmentNo, Integer i) {
                return null;
            }

            @Override
            public R intervalState(String s, String s1, String intervalTime, String hearName) {
                return null;
            }

            @Override
            public List<DbTcpClient> tcpClients() {
                return null;
            }

            @Override
            public R stateAllQuery(List<DbOperationVo> dbOperationVo) {
                return null;
            }

            @Override
            public R timingTongFengHand() {
                return null;
            }

            @Override
            public R saveTongFengType() {
                return null;
            }


            @Override
            public R operationList(List<DbOperationVo> dbOperationVo) {
                return null;
            }
        };

    }
}
