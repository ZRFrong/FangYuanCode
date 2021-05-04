package com.ruoyi.fangyuanapi.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.fangyuanapi.dto.EquipmentAdminDto;
import com.ruoyi.fangyuanapi.mapper.DbLandMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserMapper;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.system.domain.DbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentAdminMapper;
import com.ruoyi.system.domain.DbEquipmentAdmin;
import com.ruoyi.fangyuanapi.service.IDbEquipmentAdminService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description:    java类作用描述
* @Author:          ZhaoHuiSheng
* @CreateDate:    2021/4/6 16:01
* @Version:         1.0
* @Sign:            天下风云出我辈，一入代码岁月催。
*/
@Service
public class DbEquipmentAdminServiceImpl implements IDbEquipmentAdminService 
{

    @Autowired
    private DbEquipmentAdminMapper dbEquipmentAdminMapper;

    @Autowired
    private DbUserMapper dbUserMapper;


    @Autowired
    private DbLandMapper dbLandMapper;


    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public DbEquipmentAdmin selectDbEquipmentAdminById(Long id)
    {
        return dbEquipmentAdminMapper.selectDbEquipmentAdminById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param dbEquipmentAdmin 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<DbEquipmentAdmin> selectDbEquipmentAdminList(DbEquipmentAdmin dbEquipmentAdmin)
    {
        return dbEquipmentAdminMapper.selectDbEquipmentAdminList(dbEquipmentAdmin);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param dbEquipmentAdmin 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertDbEquipmentAdmin(DbEquipmentAdmin dbEquipmentAdmin)
    {
        dbEquipmentAdmin.setCreateTime(DateUtils.getNowDate());
        return dbEquipmentAdminMapper.insertDbEquipmentAdmin(dbEquipmentAdmin);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param dbEquipmentAdmin 【请填写功能名称】
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDbEquipmentAdmin(DbEquipmentAdmin dbEquipmentAdmin)
    {
        return dbEquipmentAdminMapper.updateDbEquipmentAdmin(dbEquipmentAdmin);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentAdminByIds(String ids)
    {
        return dbEquipmentAdminMapper.deleteDbEquipmentAdminByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentAdminById(Long id)
    {
        return dbEquipmentAdminMapper.deleteDbEquipmentAdminById(id);
    }

    /***
     * 方法描述
     * @since: 1.0.0
     * @param userId
     * @return: java.util.List<java.lang.Long>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/6 11:31
     */
    @Override
    public List<Long> selectEquipmentIdByUserId(Long userId) {
        return dbEquipmentAdminMapper.selectEquipmentIdByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> selectDbEquipmentAdminByLandId(List<Long> landIds) {
        List<DbEquipmentAdmin> dbEquipmentAdmins  = dbEquipmentAdminMapper.selectDbEquipmentAdminByLandId(landIds);
        Map<Long,List<DbEquipmentAdmin>> map = dbEquipmentAdmins.stream().collect(Collectors.groupingBy(DbEquipmentAdmin :: getLandId));
        ArrayList<Map<String, Object>> result = new ArrayList<>();
        map.forEach((x,y) -> {
            int i = 0;
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("landId", y.get(i).getLandId());
            hashMap.put("landName",y.get(i).getLandName());
            hashMap.put("admins","");
            List<EquipmentAdminDto> admins = new ArrayList<>();
            for (DbEquipmentAdmin dbEquipmentAdmin : y) {
                admins.add(EquipmentAdminDto.builder()
                            .avatar(dbEquipmentAdmin.getAvatar())
                            .isSuperAdmin(dbEquipmentAdmin.getIsSuperAdmin())
                            .userId(dbEquipmentAdmin.getUserId())
                            .build());
            }
            hashMap.put("admins",admins);
            result.add(hashMap);
            i++;
        });
        return result;
    }


    @Override
    public DbEquipmentAdmin selectDbEquipmentAdminByUserIdAndLandId(Long landId, Long userId,String equipmentId) {
        return dbEquipmentAdminMapper.selectDbEquipmentAdminByUserIdAndLandId(landId,userId,equipmentId);
    }

    @Override
    public DbEquipmentAdmin insertEquipmentAdmin(Long landId, String userId,String equipmentId) {
        DbUser user = dbUserMapper.selectDbUserById(Long.valueOf(userId));
        if (user == null || user.getId() == null){
            return null;
        }
        DbLand land = dbLandMapper.selectDbLandById(landId);
        if (land == null){
            return null;
        }
        DbEquipmentAdmin equipmentAdmin = DbEquipmentAdmin.builder()
                .landId(landId)
                .landName(land.getNickName())
                .avatar(user.getAvatar())
                .userId(user.getId())
                .isSuperAdmin(1L)
                .createTime(new Date())
                .equipmentId(equipmentId)
                .build();
        int i = dbEquipmentAdminMapper.insertDbEquipmentAdmin(equipmentAdmin);
        return i>0? equipmentAdmin : null;
    }


    @Override
    public List<DbEquipmentAdmin> selectDbEquipmentAdminListByUserId(Long userId) {
        return dbEquipmentAdminMapper.selectDbEquipmentAdminListByUserId(userId);
    }

    @Override
    public DbEquipmentAdmin selectIsSuperAdmin(Long landId) {
        return dbEquipmentAdminMapper.selectIsSuperAdmin(landId);
    }

    @Override
    public List<DbEquipmentAdmin> selectDbEquipmentAdminsByLandId(Long landId) {
        return dbEquipmentAdminMapper.selectDbEquipmentAdminsByLandId(landId);
    }
}
