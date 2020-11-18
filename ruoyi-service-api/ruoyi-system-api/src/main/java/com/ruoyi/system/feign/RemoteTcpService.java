package com.ruoyi.system.feign;

/*
 * tcp服务接口 指令处理 温度查询
 * */

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.factory.RemoteTcpFallbackFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANTCP, fallbackFactory = RemoteTcpFallbackFactory.class)
public interface RemoteTcpService {

    /*
     * 指令发送  单个
     * */
    @GetMapping(value = "client/operation", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R operation( DbOperationVo dbOperationVo);
    /*
     * 指令发送  多个
     * */
    @GetMapping(value = "client/operationList", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R operationList(@RequestBody List<DbOperationVo> dbOperationVo) ;

    /*
    *状态查询
    * */
    @GetMapping(value="type/listonly", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<DbTcpType> list(@RequestBody DbTcpType dbTcpType);


    @GetMapping(value="type/timingType", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R strtTiming();

    @GetMapping(value="type/curingType", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R startSaveTiming();


    @GetMapping(value="type/operateTongFengType", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R operateTongFengType(@RequestBody DbEquipment dbEquipment, @PathVariable("i")int i,@PathVariable("temp")String temp);

    @GetMapping(value="type/operateTongFengHand", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R
    operateTongFengHand(@RequestBody @RequestParam("equipment") DbEquipment equipment,@RequestParam(name = "i") int i);
}
