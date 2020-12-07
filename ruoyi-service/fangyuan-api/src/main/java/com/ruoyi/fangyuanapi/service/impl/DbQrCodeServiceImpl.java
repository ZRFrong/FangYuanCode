package com.ruoyi.fangyuanapi.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.aes.TokenUtils;
import com.ruoyi.fangyuanapi.conf.TokenConf;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentMapper;
import com.ruoyi.fangyuanapi.utils.QrCodeUtils;
import com.ruoyi.system.domain.DbQrCode;
import com.ruoyi.system.domain.DbQrCodeVo;
import com.ruoyi.system.domain.OperatePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class DbQrCodeServiceImpl implements IDbQrCodeService {
    @Autowired
    private DbQrCodeMapper dbQrCodeMapper;

    @Autowired
    private DbEquipmentMapper dbEquipmentMapper;


    @Autowired
    private TokenConf tokenConf;

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
            dbQrCode.setAdminUserId(dbQrCode.getAdminUserId() == null ? 0 : dbQrCode.getAdminUserId());
            dbQrCodeVo.setFirstBind( dbQrCode.getAdminUserId().equals(id) ? true : false);
            String text = dbEquipmentMapper.selectDbEquipmentById(dbQrCode.getEquipmentId()).getHandlerText();
            dbQrCodeVo.setOperatePojo( JSON.parseArray(text, OperatePojo.class));
            return dbQrCodeVo;
        } else {
            return null;
        }
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

    public static void main(String [] args){
        String s = "[{\"checkCode\":\"1\",\"checkName\":\"卷帘1\",\"spList\":[{\"handleCode\":\"15,160,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,160,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,161,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,161,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"1\",\"checkName\":\"卷帘2\",\"spList\":[{\"handleCode\":\"15,162,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,162,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,163,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,163,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"2\",\"checkName\":\"通风1\",\"spList\":[{\"handleCode\":\"15,164,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,164,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,165,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,165,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"2\",\"checkName\":\"通风2\",\"spList\":[{\"handleCode\":\"15,166,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,166,00,00\",\"handleName\":\"start_stop\"},{\"handleCode\":\"15,167,255,00\",\"handleName\":\"down\"},{\"handleCode\":\"15,167,00,00\",\"handleName\":\"down_stop\"}]},{\"checkCode\":\"3\",\"checkName\":\"补光\",\"spList\":[{\"handleCode\":\"15,168,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,168,00,00\",\"handleName\":\"start_stop\"}]},{\"checkCode\":\"4\",\"checkName\":\"浇水\",\"spList\":[{\"handleCode\":\"15,169,255,00\",\"handleName\":\"start\"},{\"handleCode\":\"15,169,00,00\",\"handleName\":\"start_stop\"}]}]";
        Object parse = JSON.parse(s);
        System.out.println(parse);
        List<OperatePojo> list = JSON.parseArray(s, OperatePojo.class);
        System.out.println(list);
        OperatePojo operatePojo = JSONUtil.toBean(parse+"", OperatePojo.class);
        System.out.println(operatePojo);

    }
}
