package com.ruoyi.fangyuanapi.service;

import com.ruoyi.common.core.domain.R;

public interface DaPengService {

    /**
     * 查询设备数据
     * @param landIds 大棚ID集合
     * @return 设备集合
     */
    R listData(String landIds);

}
