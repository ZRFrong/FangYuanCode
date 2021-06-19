package com.ruoyi.system.feign;

import com.ruoyi.common.constant.ServiceNameConstants;
import com.ruoyi.system.feign.factory.RemoteApiFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Mr.Zaho
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.feign.DbLandClient.java
 * @Description 大棚相关
 * @createTime 2021年06月18日 14:23:00
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_FANGYUANAPI, fallbackFactory = RemoteApiFallbackFactory.class)
public interface DbLandClient {

    /**
     * 根据心跳名和用户id获取大棚id
     * @since: 2.0.0
     * @param heartName
     * @param userId
     * @return: java.util.List<java.lang.Long>
     * @author: ZHAOXIAOSI
     * @date: 2021/6/18 14:26
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("land/getLandId/{heartName}/{userId}")
    List<Long> getLandIdByHeartName(@PathVariable("heartName") String heartName, @PathVariable("userId") Long userId);

    /**
     * 方法描述
     * @since: 2.0.0
     * @param heartName
     * @return: java.util.List<java.lang.Long>
     * @author: ZHAOXIAOSI
     * @date: 2021/6/18 14:41
     * @sign: 大丈夫生于天地间，岂能郁郁旧居人下。
     */
    @GetMapping("land/getLandIdsByHeartName/{heartName}")
    List<Long> getLandIdsByHeartName(@PathVariable("heartName") String heartName);

}
