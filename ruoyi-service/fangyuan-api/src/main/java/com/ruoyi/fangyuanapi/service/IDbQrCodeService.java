package com.ruoyi.fangyuanapi.service;

import com.ruoyi.system.domain.DbQrCode;
import com.ruoyi.system.domain.DbQrCodeVo;

import java.util.List;

/**
 * 二维码Service接口
 * 
 * @author zheng
 * @date 2020-09-30
 */
public interface IDbQrCodeService 
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
     * 批量删除二维码
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbQrCodeByIds(String ids);

    /**
     * 删除二维码信息
     * 
     * @param qrCodeId 二维码ID
     * @return 结果
     */
    public int deleteDbQrCodeById(Long qrCodeId);

    String qrCodeGenerate(DbQrCode dbQrCode) throws Exception;

    DbQrCodeVo qrCodeInfo(String token, String qrCodeId);

    /**
     * 扫码绑定设备页面
     * @param dbLandId 土地id
     * @param dbEquipmentId 设备id
     * @param userId 用户id
     * @param handleText 操作集
     */
    boolean banDingEquipment(Long dbLandId, Long dbEquipmentId, Long userId, String handleText);
}
