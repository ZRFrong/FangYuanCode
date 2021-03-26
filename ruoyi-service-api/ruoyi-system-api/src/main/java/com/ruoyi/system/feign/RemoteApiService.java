package com.ruoyi.system.feign;

/*
 * tcp服务接口 指令处理 温度查询
 * */

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.factory.RemoteApiFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANAPI, fallbackFactory = RemoteApiFallbackFactory.class)
public interface RemoteApiService {



    @GetMapping(value="weather/curingType")
    R startSaveTiming();

    @PostMapping(value="abnormalInfo/saveEquiment")
    R abnormalInfoSave(@RequestBody DbAbnormalInfo dbAbnormalInfo);


    @PostMapping(value="abnormalInfo/saveEquimentOperation")
    R saveEquimentOperation(@RequestBody DbAbnormalInfo dbAbnormalInfo);

    @GetMapping(value="land/deviceBelongs/{equipmentId}")
    public R deviceBelongs(@PathVariable("equipmentId") Long equipmentId);
}
