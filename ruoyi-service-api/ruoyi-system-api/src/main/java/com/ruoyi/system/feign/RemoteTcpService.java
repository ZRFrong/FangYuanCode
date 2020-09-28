package com.ruoyi.system.feign;

/*
 * tcp服务接口 指令处理
 * */

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.feign.factory.RemoteTcpFallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANTCP, fallbackFactory = RemoteTcpFallbackFactory.class)
public interface RemoteTcpService {

    /*
     * 指令发送  单个
     * */
    @GetMapping(value = "client/operation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R operation(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true) DbOperationVo dbOperationVo);
    /*
     * 指令发送  多个
     * */
    @GetMapping(value = "client/operationList", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R operationList(@ApiParam(name = "DbOperationVo", value = "传入json格式", required = true) List<DbOperationVo> dbOperationVo) ;


}
