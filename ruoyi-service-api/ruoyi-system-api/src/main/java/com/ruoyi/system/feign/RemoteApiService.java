package com.ruoyi.system.feign;

/*
 * tcp服务接口 指令处理 温度查询
 * */

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.factory.RemoteApiFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANAPI, fallbackFactory = RemoteApiFallbackFactory.class)
public interface RemoteApiService {



    @GetMapping(value="weather/curingType")
    R startSaveTiming();




}
