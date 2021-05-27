package com.ruoyi.system.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DbAppVersionMapper;
import com.ruoyi.system.domain.DbAppVersion;
import com.ruoyi.system.service.IDbAppVersionService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * app版本更新Service业务层处理
 * 
 * @author zheng
 * @date 2020-10-28
 */
@Service
public class DbAppVersionServiceImpl implements IDbAppVersionService 
{
    @Autowired
    private DbAppVersionMapper dbAppVersionMapper;

    /**
     * 查询app版本更新
     * 
     * @param id app版本更新ID
     * @return app版本更新
     */
    @Override
    public DbAppVersion selectDbAppVersionById(Long id)
    {
        return dbAppVersionMapper.selectDbAppVersionById(id);
    }

    /**
     * 查询app版本更新列表
     * 
     * @param dbAppVersion app版本更新
     * @return app版本更新
     */
    @Override
    public List<DbAppVersion> selectDbAppVersionList(DbAppVersion dbAppVersion)
    {
        return dbAppVersionMapper.selectDbAppVersionList(dbAppVersion);
    }

    /**
     * 新增app版本更新
     * 
     * @param dbAppVersion app版本更新
     * @return 结果
     */
    @Override
    @Transactional
    public int insertDbAppVersion(DbAppVersion dbAppVersion)
    {
        return dbAppVersionMapper.insertDbAppVersion(dbAppVersion);
    }

    /**
     * 修改app版本更新
     * 
     * @param dbAppVersion app版本更新
     * @return 结果
     */
    @Override
    public int updateDbAppVersion(DbAppVersion dbAppVersion)
    {
        return dbAppVersionMapper.updateDbAppVersion(dbAppVersion);
    }

    /**
     * 删除app版本更新对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbAppVersionByIds(String ids)
    {
        return dbAppVersionMapper.deleteDbAppVersionByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除app版本更新信息
     * 
     * @param id app版本更新ID
     * @return 结果
     */
    @Override
    public int deleteDbAppVersionById(Long id)
    {
        return dbAppVersionMapper.deleteDbAppVersionById(id);
    }

    @Override
    public DbAppVersion selectDbAppVersionByAppVersion(String apkVersion) {
        return dbAppVersionMapper.selectDbAppVersionByAppVersion(apkVersion);
    }

    @Override
    public Map<String, Object> appCheckUpdate(String version) {
        List<Map<String, Object>> dbAppVersionsByVersions = dbAppVersionMapper.selectDbAppVersionsByVersion(version);
        Map<String, Object> hashMap = null;
        if (CollectionUtils.isEmpty(dbAppVersionsByVersions)){
            hashMap = new HashMap<>();
            hashMap.put("is_constraint", "0");
            return hashMap;
        }
        hashMap = dbAppVersionsByVersions.get(dbAppVersionsByVersions.size()-1);
        for (Map<String, Object> map : dbAppVersionsByVersions) {
            if("1".equals(map.get("is_constraint")+"")){
                hashMap.put("is_constraint", "1");
                return hashMap;
            }
        }
        hashMap.put("is_constraint", "2");
        return hashMap;
    }
}
