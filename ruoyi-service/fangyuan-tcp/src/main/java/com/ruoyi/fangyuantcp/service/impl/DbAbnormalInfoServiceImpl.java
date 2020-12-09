package com.ruoyi.fangyuantcp.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.fangyuantcp.mapper.DbAbnormalInfoMapper1;
import com.ruoyi.fangyuantcp.mapper.DbEquipmentMapper1;
import com.ruoyi.fangyuantcp.service.IDbAbnormalInfoService1;
import com.ruoyi.system.domain.DbAbnormalInfo;
import com.ruoyi.system.domain.DbEquipment;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 报警信息Service业务层处理
 * 
 * @author zheng
 * @date 2020-12-02
 */
@Service
public class DbAbnormalInfoServiceImpl implements IDbAbnormalInfoService1
{
    @Autowired
    private DbAbnormalInfoMapper1 dbAbnormalInfoMapper;


    @Autowired
    private DbEquipmentMapper1 equipmentMapper1;

    /**
     * 查询报警信息
     * 
     * @param id 报警信息ID
     * @return 报警信息
     */
    @Override
    public DbAbnormalInfo selectDbAbnormalInfoById(Long id)
    {
        return dbAbnormalInfoMapper.selectDbAbnormalInfoById(id);
    }

    /**
     * 查询报警信息列表
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 报警信息
     */
    @Override
    public List<DbAbnormalInfo> selectDbAbnormalInfoList(DbAbnormalInfo dbAbnormalInfo)
    {
        return dbAbnormalInfoMapper.selectDbAbnormalInfoList(dbAbnormalInfo);
    }

    @Override
    public R saveEquiment( DbAbnormalInfo dbAbnormalInfo) {
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHandlerText(dbAbnormalInfo.getObjectType());
        Long equipmentId = equipmentMapper1.selectDbEquipmentList(dbEquipment).get(0).getEquipmentId();
        dbAbnormalInfo.setDbEquipmentId(equipmentId);
        int i = dbAbnormalInfoMapper.insertDbAbnormalInfo(dbAbnormalInfo);
        if (i>0){

        return R.ok();
        }
        return R.error();
    }

    /**
     * 新增报警信息
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 结果
     */
    @Override
    public int insertDbAbnormalInfo(DbAbnormalInfo dbAbnormalInfo)
    {
        return dbAbnormalInfoMapper.insertDbAbnormalInfo(dbAbnormalInfo);
    }

    /**
     * 修改报警信息
     * 
     * @param dbAbnormalInfo 报警信息
     * @return 结果
     */
    @Override
    public int updateDbAbnormalInfo(DbAbnormalInfo dbAbnormalInfo)
    {
        return dbAbnormalInfoMapper.updateDbAbnormalInfo(dbAbnormalInfo);
    }

    /**
     * 删除报警信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbAbnormalInfoByIds(String ids)
    {
        return dbAbnormalInfoMapper.deleteDbAbnormalInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除报警信息信息
     * 
     * @param id 报警信息ID
     * @return 结果
     */
    public int deleteDbAbnormalInfoById(Long id)
    {
        return dbAbnormalInfoMapper.deleteDbAbnormalInfoById(id);
    }

    /*
    *
    * */
    @Override
    public void saveEquimentOperation(DbAbnormalInfo dbAbnormalInfo) {
        DbEquipment dbEquipment = new DbEquipment();
        dbEquipment.setHeartbeatText(dbAbnormalInfo.getObjectType());
        dbEquipment.setEquipmentNo(Integer.parseInt(dbAbnormalInfo.getText()));

        DbEquipment dbEquipment1 = equipmentMapper1.selectDbEquipmentList(dbEquipment).get(0);
        dbAbnormalInfo.setDbEquipmentId(dbEquipment1.getEquipmentId());
        dbAbnormalInfo.setObjectType(dbEquipment1.getEquipmentName());
        int i = dbAbnormalInfoMapper.insertDbAbnormalInfo(dbAbnormalInfo);


    }


}
