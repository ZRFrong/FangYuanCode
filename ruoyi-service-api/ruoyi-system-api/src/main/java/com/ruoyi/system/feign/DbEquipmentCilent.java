package com.ruoyi.system.feign;

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.feign.factory.DbEquipmentCilent.java
 * @createTime 2021年03月22日 11:32:00
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANAPI, fallbackFactory = RemoteApiFallbackFactory.class)
public interface DbEquipmentCilent {
    @PostMapping("dbEquipment/batchEquipment")
    R batchEquipment(@RequestParam("prefix") String prefix,@RequestParam("suffix") String suffix, @RequestParam("openInterval") String openInterval,@RequestParam("closedInterval") String closedInterval);
}
