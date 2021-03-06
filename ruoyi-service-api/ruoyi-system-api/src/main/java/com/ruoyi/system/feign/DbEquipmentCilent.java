package com.ruoyi.system.feign;

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.feign.factory.DbEquipmentCilent.java
 * @createTime 2021年03月22日 11:32:00
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANAPI, fallbackFactory = RemoteApiFallbackFactory.class)
public interface DbEquipmentCilent {

    /**
     * 批量生成设备接口  完成心跳例子： pisitai-00032-dapeng
     * @since: 2.0.0
     * @param prefix
     * @param suffix
     * @param openInterval
     * @param closedInterval
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/6/7 17:39
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PostMapping("dbEquipment/batchEquipment")
    R batchEquipment(@RequestParam("prefix") String prefix,@RequestParam("suffix") String suffix, @RequestParam("openInterval") String openInterval,@RequestParam("closedInterval") String closedInterval);

    /***
     * 根据心跳名修改在线状态
     * @since: 2.0.0
     * @param heartbeat
     * @param isOnline
     * @return: void
     * @author: ZHAOXIAOSI
     * @date: 2021/6/7 17:36
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PutMapping("dbEquipment/updateEquipmentIsOnline")
    void updateEquipmentIsOnline(@RequestParam("heartbeat")String heartbeat,@RequestParam("isOnline")Integer isOnline);
}
