package com.ruoyi.fangyuanapi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.aes.TokenUtils;
import com.ruoyi.fangyuanapi.conf.TokenConf;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentMapper;
import com.ruoyi.fangyuanapi.mapper.DbLandMapper;
import com.ruoyi.fangyuanapi.utils.QrCodeUtils;
import com.ruoyi.system.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbQrCodeMapper;
import com.ruoyi.fangyuanapi.service.IDbQrCodeService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 二维码Service业务层处理
 *
 * @author zheng
 * @date 2020-09-30
 */
@Service
public class DbQrCodeServiceImpl implements IDbQrCodeService {
    @Autowired
    private DbQrCodeMapper dbQrCodeMapper;

    @Autowired
    private DbEquipmentMapper dbEquipmentMapper;


    @Autowired
    private TokenConf tokenConf;

    @Autowired
    private DbLandMapper dbLandMapper;

    /*
     * 二维码地址
     * */
    @Value("${com.fangyuan.qrcode.url}")
    private String url;


    /**
     * 查询二维码
     *
     * @param qrCodeId 二维码ID
     * @return 二维码
     */
    @Override
    public DbQrCode selectDbQrCodeById(Long qrCodeId) {
        return dbQrCodeMapper.selectDbQrCodeById(qrCodeId);
    }

    /**
     * 查询二维码列表
     *
     * @param dbQrCode 二维码
     * @return 二维码
     */
    @Override
    public List<DbQrCode> selectDbQrCodeList(DbQrCode dbQrCode) {
        return dbQrCodeMapper.selectDbQrCodeList(dbQrCode);
    }

    /**
     * 新增二维码
     *
     * @param dbQrCode 二维码
     * @return 结果
     */
    @Override
    public int insertDbQrCode(DbQrCode dbQrCode) {
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
    public int updateDbQrCode(DbQrCode dbQrCode) {
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
    public int deleteDbQrCodeByIds(String ids) {
        return dbQrCodeMapper.deleteDbQrCodeByIds(Convert.toStrArray(ids));
    }

    /*
     *
     * 生成二维码   创建二维码表
     * */
    @Override
    public String qrCodeGenerate(DbQrCode dbQrCode) throws Exception {

        dbQrCode.setCreateTime(new Date());
        String argument = "?qrCodeId=" + dbQrCode.getQrCodeId();
        String text = url + argument;
        String encode = QrCodeUtils.encode(text, "http://cdn.fangyuancun.cn/logo9.png", true);
        dbQrCode.setQrCodePic(encode);
        int i = dbQrCodeMapper.updateDbQrCode(dbQrCode);
        if (i > 0) {
            return encode;
        } else {
            return null;
        }
    }

    /*
     * 扫码获取信息
     * */
    @Override
    public DbQrCodeVo qrCodeInfo(String token, String qrCodeId) {
        Map<String, Object> map = TokenUtils.verifyToken(token, tokenConf.getAccessTokenKey());
        if (map != null) {
            /* id == null token被篡改 解密失败 */
            String id = map.get("id") + "";
            DbQrCodeVo dbQrCodeVo = new DbQrCodeVo();
            DbQrCode dbQrCode = dbQrCodeMapper.selectDbQrCodeById(Long.valueOf(qrCodeId));


            dbQrCodeVo.setDbQrCode(dbQrCode);
            if (dbQrCode.getAdminUserId()!=null){
            dbQrCodeVo.setFirstBind(dbQrCode.getAdminUserId().equals(id) ? true : false);
            }else {
            dbQrCodeVo.setFirstBind(true);
            }
            List<OperatePojo> lists = JSON.parseArray(dbEquipmentMapper.selectDbEquipmentById(dbQrCode.getEquipmentId()).getHandlerText(), OperatePojo.class);

            /*
            * 添加选中的操作集
            * */


            dbQrCodeVo.setOperatePojo(lists);
            return dbQrCodeVo;
        } else {
            return null;
        }
    }

    /**
     *
     * @param dbLandId 土地id
     * @param dbEquipmentId 设备id
     * @param userId 用户id
     * @param handleText 操作集
     */
    @Override
    @Transactional
    public boolean banDingEquipment(Long dbLandId, Long dbEquipmentId, Long userId, String handleText) {

        DbLand dbLand = dbLandMapper.selectDbLandById(dbLandId);
        String ids = dbLand.getEquipmentIds();
        DbEquipment equipment = new DbEquipment();
        if (StringUtils.isEmpty(ids)){
            dbLand.setEquipmentIds(dbEquipmentId+"");

        }else {
            dbLand.setEquipmentIds(ids+","+dbEquipmentId);
        }
        dbLandMapper.updateDbLand(dbLand);
        equipment.setEquipmentId(dbEquipmentId);
        equipment.setUpdateTime(new Date());
        equipment.setHandlerText(handleText);
        equipment.setEquipmentNo(1);
        int i = dbEquipmentMapper.updateDbEquipment(equipment);
        return i>0 ? true : false;
    }

    /**
     * 删除二维码信息
     *
     * @param qrCodeId 二维码ID
     * @return 结果
     */
    public int deleteDbQrCodeById(Long qrCodeId) {
        return dbQrCodeMapper.deleteDbQrCodeById(qrCodeId);
    }
}
