package com.ruoyi.system.feign;

/*
 * tcp服务接口 指令处理 温度查询
 * */

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbOperationVo;
import com.ruoyi.system.domain.DbStateRecords;
import com.ruoyi.system.domain.DbTcpType;
import com.ruoyi.system.feign.factory.RemoteTcpFallbackFactory;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANTCP, fallbackFactory = RemoteTcpFallbackFactory.class)
public interface RemoteTcpService {

    /*
     * 指令发送  单个
     * */
    @PostMapping(value = "client/operation")
    public R operation(@RequestBody DbOperationVo dbOperationVo);
    /*
     * 指令发送  多个operationList
     * */
    @PostMapping(value = "client/operationList")
    public R operationList(@RequestBody List<DbOperationVo> dbOperationVo) ;

    /*
    *状态查询
    * */
    @GetMapping(value="type/listonly")
    public List<DbTcpType> list(@RequestBody DbTcpType dbTcpType);

    @GetMapping(value="client/sinceOrHand")
    R sinceOrHand();


    @GetMapping("order/curing")
    void  curingTiming();


    @GetMapping(value="type/timingType")
    R strtTiming();

    @GetMapping(value="type/curingType")
    R startSaveTiming();


    @RequestMapping(method = RequestMethod.GET,value="type/operateTongFengType/{heartbeatText}/{equipmentNo}/{i}/{temp}")
    R operateTongFengType(  @PathVariable("heartbeatText")String heartbeatText,@PathVariable("equipmentNo")String equipmentNo, @PathVariable("i")Integer i,@PathVariable("temp")String temp);

    @RequestMapping( method = RequestMethod.GET,value="type/operateTongFengHand/{heartbeatText}/{equipmentNo}/{i}")
    R operateTongFengHand(  @PathVariable("heartbeatText")String heartbeatText,@PathVariable("equipmentNo")String equipmentNo, @PathVariable(name = "i") Integer i);

    @GetMapping("intervalState/{startTime}/{endTime}/{interval}")
    List<DbStateRecords> intervalState(@PathVariable("startTime")String s, @PathVariable("endTime")String s1, @PathVariable("interval")String intervalTime);
}
