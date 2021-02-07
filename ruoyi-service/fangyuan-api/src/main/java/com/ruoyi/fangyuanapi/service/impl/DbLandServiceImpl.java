package com.ruoyi.fangyuanapi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentMapper;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentTempMapper;
import com.ruoyi.fangyuanapi.utils.DbLandUtils;
import com.ruoyi.system.domain.DbEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbLandMapper;
import com.ruoyi.system.domain.DbLand;
import com.ruoyi.fangyuanapi.service.IDbLandService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
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

    private DbLand checkLand(DbLand land,Integer num){
        DbLand dbLand = new DbLand();
        dbLand.setNickName(DbLandUtils.getLnadName(num));
        dbLand.setSiteId(0L);
        dbLand.setDbUserId(land.getDbUserId());
        int i = dbLandMapper.insertDbLand(dbLand);
        return dbLand;
    }
    public static void main(String[] args){
        System.out.println(0/1);

    }
}
