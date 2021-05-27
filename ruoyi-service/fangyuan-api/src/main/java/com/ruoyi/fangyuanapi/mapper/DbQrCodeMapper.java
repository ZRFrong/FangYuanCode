package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.system.domain.DbQrCode;
import org.apache.ibatis.annotations.Param;

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


    void updateDbQrCodeById(@Param("qrCodeId") Long qrCodeId,@Param("heartbeatText") String heartbeatText);

    DbQrCode selectDbQrCodeByEquipmentId(Long equipmentId);

    /**
     * 修改二维码管理员id
     * @since: 2.0.0
     * @param userId
     * @param qrCodeId
     * @return: int
     * @author: ZHAOXIAOSI
     * @date: 2021/5/9 22:47
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    int updateDbQrCodeUserIdById(@Param("userId") Long userId,@Param("qrCodeId") Long qrCodeId);
}
