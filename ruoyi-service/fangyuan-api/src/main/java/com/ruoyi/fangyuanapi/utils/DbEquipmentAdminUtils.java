package com.ruoyi.fangyuanapi.utils;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.dto.LandAdminDto;
import com.ruoyi.fangyuanapi.service.IDbEquipmentAdminService;
import com.ruoyi.system.domain.DbEquipmentAdmin;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZHAO
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.fangyuanapi.utils.DbEquipmentAdminUtils.java
 * @Description TODO
 * @createTime 2021年05月03日 00:09:00
 */
public class DbEquipmentAdminUtils {
    public static ArrayList<LandAdminDto> test(List<DbEquipmentAdmin> list, String userId){
        IDbEquipmentAdminService bean = SpringUtils.getBean(IDbEquipmentAdminService.class);
        Set<Long> set = list.stream().map(DbEquipmentAdmin::getLandId).collect(Collectors.toSet());
        ArrayList<LandAdminDto> landAdmins = new ArrayList<>();
        for (Long landId : set) {
            LandAdminDto build = LandAdminDto.builder().build();
            List<DbEquipmentAdmin> dbEquipmentAdmins = bean.selectDbEquipmentAdminsByLandId(landId);
            ArrayList<Map<String, String>> maps = new ArrayList<>();
            for (DbEquipmentAdmin admin : dbEquipmentAdmins) {
                HashMap<String, String> map = new HashMap<>();
                map.put("userId",admin.getUserId()+"");
                map.put("avatar",admin.getAvatar());
                map.put("isSuperAdmin",admin.getIsSuperAdmin()+"");
                if (admin.getIsSuperAdmin() == 0){
                    build.setLandName(admin.getLandName());
                    build.setLandId(admin.getLandId());
                }
                if (admin.getUserId().equals(Long.valueOf(userId))){
                    build.setIsSuperAdmin(admin.getIsSuperAdmin());
                }
                maps.add(map);
            }
            build.setAdmins(maps);
            landAdmins.add(build);
        }
        return landAdmins;
    }
}
