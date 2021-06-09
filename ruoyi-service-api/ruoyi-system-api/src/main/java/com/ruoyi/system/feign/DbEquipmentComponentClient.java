package com.ruoyi.system.feign;

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.feign.factory.RemoteApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Mr.ZHAO
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.feign.DbEquipmentComponentClient.java
 * @Description
 * @createTime 2021年05月18日 23:20:00
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANAPI, fallbackFactory = RemoteApiFallbackFactory.class)
public interface DbEquipmentComponentClient {

    /**
     * 修改补光开关状态
     * @since: 2.0.0
     *  @param heartbeatText 心跳
     * @param switchState 开关状态 0关 1开
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/18 23:22
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PutMapping("component/modifyLightStatus")
    R modifyLightStatus(@RequestParam("heartbeatText") String heartbeatText,@RequestParam("switchState") String switchState,@RequestParam("fillLightTimingStatus") Integer fillLightTimingStatus);


    /**
     * 修改功能的开关状态
     * @since: 2.0.0
     * @param heartbeatText
     * @param switchState
     * @param  functionLogo 功能标识
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/5/20 17:38
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PutMapping("component/modifyFunctionStatus")
    R modifyFunctionLogoStatus(@RequestParam("heartbeatText")String heartbeatText
            ,@RequestParam("functionLogo")String functionLogo,@RequestParam("switchState")String switchState,@RequestParam("fillLightTimingStatus")Integer fillLightTimingStatus);

    /**
     * 方法描述
     * @since: 2.0.0
     * @param list
     * @param heartbeatText
     * @return: com.ruoyi.common.core.domain.R
     * @author: Mr.Zhao
     * @date: 2021/5/20 17:46
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @PutMapping("component/heartbeatText")
    R progressAnalysis(@RequestParam("list") List<String> list,@RequestParam("heartbeatText")String heartbeatText);

    /**
     * 方法描述
     * @since: 2.0.0
     * @param heartbeatText
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/5/20 22:37
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。       
     */
    @GetMapping("dbEquipment/selectByHeartbeatText/{heartbeatText}")
    String selectByHeartbeatText(@PathVariable("heartbeatText") String heartbeatText);
}
