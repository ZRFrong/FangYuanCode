package com.ruoyi.fangyuanapi.service;

import com.ruoyi.common.core.domain.R;

/**
 * @author ZHAOXIAOSI
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.service.ArCenterService.java
 * @Description
 * @createTime 2021年06月04日 16:11:00
 */
public interface ArCenterService {

    /**
     * 根据大棚id获取传感器数据以及功能项及监控
     * @since: 1.0.0
     * @param id
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/6/4 16:16
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    R getLandData(Long id);
}
