package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbQrCode;

import java.util.List;

/**
 * 二维码Mapper接口
 * 
 * @author zheng
 * @date 2020-09-30
 */
public interface DbQrCodeMapper 
{
    /**
     * 查询二维码
     * 
     * @param qrCodeId 二维码ID
     * @return 二维码
     */
    public DbQrCode selectDbQrCodeById(Long qrCodeId);

    /**
     * 查询二维码列表
     * 
     * @param dbQrCode 二维码
     * @return 二维码集合
     */
    public List<DbQrCode> selectDbQrCodeList(DbQrCode dbQrCode);

    /**
     * 新增二维码
     * 
     * @param dbQrCode 二维码
     * @return 结果
     */
    public int insertDbQrCode(DbQrCode dbQrCode);

    /**
     * 修改二维码
     * 
     * @param dbQrCode 二维码
     * @return 结果
     */
    public int updateDbQrCode(DbQrCode dbQrCode);

    /**
     * 删除二维码
     * 
     * @param qrCodeId 二维码ID
     * @return 结果
     */
    public int deleteDbQrCodeById(Long qrCodeId);

    /**
     * 批量删除二维码
     * 
     * @param qrCodeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbQrCodeByIds(String[] qrCodeIds);
}
