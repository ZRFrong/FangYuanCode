package com.ruoyi.fangyuanapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.DbQrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbQrCodeMapper;
import com.ruoyi.fangyuanapi.service.IDbQrCodeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 二维码Service业务层处理
 * 
 * @author zheng
 * @date 2020-09-30
 */
@Service
public class DbQrCodeServiceImpl implements IDbQrCodeService 
{
    @Autowired
    private DbQrCodeMapper dbQrCodeMapper;

    /**
     * 查询二维码
     * 
     * @param qrCodeId 二维码ID
     * @return 二维码
     */
    @Override
    public DbQrCode selectDbQrCodeById(Long qrCodeId)
    {
        return dbQrCodeMapper.selectDbQrCodeById(qrCodeId);
    }

    /**
     * 查询二维码列表
     * 
     * @param dbQrCode 二维码
     * @return 二维码
     */
    @Override
    public List<DbQrCode> selectDbQrCodeList(DbQrCode dbQrCode)
    {
        return dbQrCodeMapper.selectDbQrCodeList(dbQrCode);
    }

    /**
     * 新增二维码
     * 
     * @param dbQrCode 二维码
     * @return 结果
     */
    @Override
    public int insertDbQrCode(DbQrCode dbQrCode)
    {
        dbQrCode.setCreateTime(DateUtils.getNowDate());
        return dbQrCodeMapper.insertDbQrCode(dbQrCode);
    }

    /**
     * 修改二维码
     * 
     * @param dbQrCode 二维码
     * @return 结果
     */
    @Override
    public int updateDbQrCode(DbQrCode dbQrCode)
    {
        dbQrCode.setUpdateTime(DateUtils.getNowDate());
        return dbQrCodeMapper.updateDbQrCode(dbQrCode);
    }

    /**
     * 删除二维码对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbQrCodeByIds(String ids)
    {
        return dbQrCodeMapper.deleteDbQrCodeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除二维码信息
     * 
     * @param qrCodeId 二维码ID
     * @return 结果
     */
    public int deleteDbQrCodeById(Long qrCodeId)
    {
        return dbQrCodeMapper.deleteDbQrCodeById(qrCodeId);
    }
}
