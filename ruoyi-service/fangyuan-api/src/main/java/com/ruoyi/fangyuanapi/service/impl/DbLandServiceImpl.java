package com.ruoyi.fangyuanapi.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.mapper.*;
import com.ruoyi.fangyuanapi.utils.DbLandUtils;
import com.ruoyi.system.domain.*;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 土地Service业务层处理
 *
 * @author zheng
 * @date 2020-09-24
 */
@Service
public class DbLandServiceImpl implements IDbLandService
{
    @Autowired
    private DbLandMapper dbLandMapper;

    @Autowired
    private DbEquipmentMapper dbEquipmentMapper;

    @Autowired
    private DbEquipmentAdminMapper dbEquipmentAdminMapper;

    @Autowired
    private DbEquipmentComponentMapper dbEquipmentComponentMapper;

    @Autowired
    private DbUserMapper dbUserMapper;

    @Autowired
    private DbQrCodeMapper dbQrCodeMapper;

    /**
     * 查询土地
     *
     * @param landId 土地ID
     * @return 土地
     */
    @Override
    public DbLand selectDbLandById(Long landId)
    {
        return dbLandMapper.selectDbLandById(landId);
    }

    /**
     * 查询土地列表
     *
     * @param dbLand 土地
     * @return 土地
     */
    @Override
    public List<DbLand> selectDbLandList(DbLand dbLand)
    {
        return dbLandMapper.selectDbLandList(dbLand);
    }

    /**
     * 新增土地
     *
     * @param dbLand 土地
     * @return 结果
     */
    @Override
    public int insertDbLand(DbLand dbLand)
    {
        dbLand.setCreateTime(DateUtils.getNowDate());
        return dbLandMapper.insertDbLand(dbLand);
    }

    /**
     * 修改土地
     *
     * @param dbLand 土地
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDbLand(DbLand dbLand)
    {
        dbLand.setUpdateTime(DateUtils.getNowDate());
        return dbLandMapper.updateDbLand(dbLand);
    }

    /**
     * 删除土地对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbLandByIds(String ids)
    {

        return dbLandMapper.deleteDbLandByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除土地信息
     *
     * @param landId 土地ID
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteDbLandById(Long landId)
    {
        Integer i = dbLandMapper.selectDbLandBySiteId(landId);
        if (i >0){
            return 0;
        }
        return dbLandMapper.deleteDbLandById(landId);
    }

    /**
     * 查询土地树列表
     *
     * @return 所有土地信息
     */
    @Override
    public List<Ztree> selectDbLandTree()
    {
        List<DbLand> dbLandList = dbLandMapper.selectDbLandList(new DbLand());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (DbLand dbLand : dbLandList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(dbLand.getLandId());
            ztree.setpId(dbLand.getSiteId());
            ztree.setName(dbLand.getNickName());
            ztree.setTitle(dbLand.getNickName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     * 小程序新增土地
     * @param dbLand
     * @return
     */
    @Override
    @Transactional
    public R weChatAddSave(DbLand dbLand) {
        List<DbLand> dbLands = dbLandMapper.selectDbLandByUserId(dbLand.getDbUserId(),0L);
        if (dbLands == null || dbLands.size() == 0 ){
            dbLands.add(checkLand(dbLand,1));
        }
        Integer count = dbLandMapper.selectDbLandCountByUserId(dbLand.getDbUserId());
        if (dbLands.size() == 9 && count == 54){
            return R.error("土地容量已经达到上限！");
        }
        Integer flag =  dbLands.size() == 0 ? 1 :dbLands.size();
        if (count / flag == 6){
            dbLands.add(checkLand(dbLand,dbLands.size()+1));
        }
        for (DbLand d : dbLands) {
            List<DbLand> landList = dbLandMapper.selectDbLandByUserId(d.getDbUserId(), d.getLandId());
            if (landList == null || landList.size()<6){
                dbLand.setSiteId(d.getLandId());
                int j = dbLandMapper.insertDbLand(dbLand);
                return j>0?R.data(dbLand.getLandId()): R.error();
            }
        }
        return null;
    }

    @Override
    public List<DbLand> selectDbLandListByUserId(Long userId) {
        return dbLandMapper.selectDbLandByUserId(userId,null);
    }

    @Override
    public List<Long> groupByUserId() {
        return dbLandMapper.groupByUserId();
    }

    @Override
    public List<DbLand> selectDbLandNoSiteList(DbLand dbLand) {

        return  dbLandMapper.selectDbLandNoSiteList(dbLand);
    }


    @Override
    public List<DbLand> selectDbLandWeChatList(DbLand dbLand) {
        return dbLandMapper.selectDbLandWeChatList(dbLand);
    }

    @Override
    public List<Map<String, Object>> selectLandOperationByLandId(Long landId) {
        DbLand dbLand = dbLandMapper.selectDbLandById(landId);
        String ids = dbLand.getEquipmentIds();
        if (StringUtils.isEmpty(ids)){
            return null;
        }
        String[] split = ids.split(",");
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (String s : split) {
            DbEquipment equipment = dbEquipmentMapper.selectDbEquipmentById(Long.valueOf(s));
            HashMap<String, Object> map = new HashMap<>();
            map.put("equipmentId",equipment.getEquipmentId());
            map.put("heartbeatText",equipment.getHeartbeatText());
            map.put("handlerText",JSON.parse(equipment.getHandlerText()));
            map.put("equipmentName",equipment.getEquipmentName());
            map.put("equipmentNo",equipment.getEquipmentNo());
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String,Object>> selectDbLandByUserIdAndSideId(Long userId) {
        List<Map<String,Object>> list = dbLandMapper.selectDbLandByUserIdAndSideId(userId,0L);
        if (list == null || list.size() <= 0){
            return null;
        }
        for (Map<String, Object> map : list) {
            List<Map<String, Object>> lands = dbLandMapper.selectDbLandByUserIdAndSideId(userId, Long.valueOf(map.get("land_id").toString()));
            map.put("lands",lands);
        }
        return list;
    }

    @Override
    public List<DbLand> selectDbLandsByUserId(Long userId) {
        return dbLandMapper.selectDbLandsByUserId(userId);
    }

    private DbLand checkLand(DbLand land,Integer num){
        DbLand dbLand = new DbLand();
        dbLand.setNickName(DbLandUtils.getLnadName(num));
        dbLand.setSiteId(0L);
        dbLand.setDbUserId(land.getDbUserId());
        int i = dbLandMapper.insertDbLand(dbLand);
        return dbLand;
    }

    /**
     * 二次追加
     * @since: 2.0.0
     * @return:
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 14:01
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateEquipmentToLand(Long landId, Long equipmentId, String userId) {
        DbEquipmentAdmin admin = dbEquipmentAdminMapper.selectDbEquipmentAdminByUserIdAndLandId(landId, Long.valueOf(userId), null);
        DbLand land = dbLandMapper.selectDbLandById(landId);
        if (land == null){
            throw new NullPointerException();
        }
        String[] split = land.getEquipmentIds().split(",");
        for (String s : split) {
            if (s.equals(equipmentId+"")){
                return true;
            }
        }
        land.setEquipmentIds(StringUtils.isNotEmpty(land.getEquipmentIds())?equipmentId+"":land.getEquipmentIds()+","+equipmentId);
        if (!land.getEquipmentIds().contains(equipmentId+"")){
            return true;
        }
        //要求后台人员必须在出厂前填好指令码
        admin.setFunctionIds(admin.getFunctionIds()+","+getDbEquipmentComponent(equipmentId));
        int i = dbEquipmentAdminMapper.updateDbEquipmentAdmin(admin);
        land.setEquipmentIds(StringUtils.isEmpty(land.getEquipmentIds())?equipmentId+"": land.getEquipmentIds()+","+equipmentId);
        dbLandMapper.updateDbLand(land);
        return i>0 ? true : false;
    }


    @Override
    public List<Map<String, String>> selectDbLandsByUserIdAndSiteId(Long userId) {
        return dbLandMapper.selectDbLandsByUserIdAndSiteId(userId);
    }

    @Override
    public Map<String, Object> getLandAndOperateInfo(Long equipmentId, Long userId,Integer type) {
        HashMap<String, Object> map = new HashMap<>(2);
        if (type == 0){
            map.put("lands",dbLandMapper.selectDbLandNameByUserId(userId,null));
        }
        map.put("lands",dbLandMapper.selectDbLandNameByUserId(userId,type));
        map.put("operate",dbEquipmentComponentMapper.selectDbEquipmentComponentNameByEquipmentId(equipmentId));
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertEquipmentToLand(DbLand dbLand, String userId, Long equipmentId,Long qrCodeId) {
        dbLand.setDbUserId(Long.valueOf(userId));
        //设备id让扫码页填写
        dbLand.setEquipmentIds(equipmentId+"");
        weChatAddSave(dbLand);
        DbUser user = dbUserMapper.selectDbUserById(Long.valueOf(userId));
        dbQrCodeMapper.updateDbQrCodeUserIdById(Long.valueOf(userId),qrCodeId);
        int i = dbEquipmentAdminMapper.insertDbEquipmentAdmin(DbEquipmentAdmin.builder()
                .avatar(user.getAvatar())
                .isSuperAdmin(0L)
                .functionIds(getDbEquipmentComponent(equipmentId))
                .landId(dbLand.getLandId())
                .landName(dbLand.getNickName())
                .userId(Long.valueOf(userId))
                .createTime(new Date())
                .isDel(0)
                .build());
        return i>0 ? true : false;
    }

    @Override
    public List<DbLand> selectDbLandListByUserIdAndSideId(Long userId) {
        return dbLandMapper.selectDbLandListByUserIdAndSideId(userId);
    }

    @Override
    public List<Long> getLandIdByHeartName(String heartName,Long userId) {
        List<Long> landIds = dbLandMapper.getLandIdByHeartName(heartName,userId);
        return landIds;
    }

    @Override
    public List<Long> getLandIdsByHeartName(String heartName) {
        return dbLandMapper.getLandIdsByHeartName(heartName);
    }

    /***
     * 通过设备id 获取到对应的功能id
     * @since: 2.0.0
     * @param equipmentId
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/4/19 14:04
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    //DEL
    private String getDbEquipmentComponent(Long equipmentId){
        List<DbEquipmentComponent> dbEquipmentComponentList = dbEquipmentComponentMapper.selectDbEquipmentComponentByEquipmentId(equipmentId);
        List<Long> ids = dbEquipmentComponentList.stream().map(DbEquipmentComponent::getId).collect(Collectors.toList());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i == 0){
                builder.append(ids.get(i));
            }else {
                builder.append(",").append(ids.get(i));
            }
        }
        return builder.toString();
    }
}
