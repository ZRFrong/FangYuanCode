package com.ruoyi.fangyuanapi.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.controller.OperateControllerAppNew;
import com.ruoyi.fangyuanapi.dto.LandDto;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentAdminMapper;
import com.ruoyi.fangyuanapi.mapper.DbLandMapper;
import com.ruoyi.fangyuanapi.service.DaPengService;
import com.ruoyi.system.domain.DbEquipmentAdmin;
import com.ruoyi.system.domain.DbLand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author zheng
 * @Date 2021/6/25 16:26
 * @Version 1.0
 */
@Service
public class DaPengServiceImpl implements DaPengService {

    @Autowired
    private DbLandMapper dbLandMapper;
    @Autowired
    private DbEquipmentAdminMapper dbEquipmentAdminMapper;
    @Autowired
    private OperateControllerAppNew operateControllerAppNew;

    /**
     * 获取所有大棚详细信息
     * @return 大棚列表
     */
    @Override
    public R listData() {
        List<DbLand> dbLands = dbLandMapper.selectDbLandList(new DbLand());
        List<LandDto> result = new ArrayList<>();
        for (DbLand dbLand : dbLands) {
            DbEquipmentAdmin dbEquipmentAdmin = dbEquipmentAdminMapper.selectDbEquipmentForAdminByLandId(dbLand.getLandId());
            if (dbEquipmentAdmin == null || StringUtils.isEmpty(dbEquipmentAdmin.getId() + "")) {
                continue;
            }
            LandDto landDto = operateControllerAppNew.getLandDto(Arrays.asList(dbEquipmentAdmin)).get(0);
            result.add(landDto);
        }
        return R.data(result);
    }
}
