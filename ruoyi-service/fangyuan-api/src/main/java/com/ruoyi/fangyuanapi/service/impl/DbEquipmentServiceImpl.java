package com.ruoyi.fangyuanapi.service.impl;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import com.ruoyi.fangyuanapi.mapper.DbQrCodeMapper;
import com.ruoyi.fangyuanapi.service.IDbQrCodeService;
import com.ruoyi.fangyuanapi.utils.QrCodeUtils;
import com.ruoyi.system.domain.DbQrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.fangyuanapi.mapper.DbEquipmentMapper;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.fangyuanapi.service.IDbEquipmentService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;

/**
 * 设备Service业务层处理
 *
 * @author fangyuan
 * @date 2020-09-01
 */

@Service
public class DbEquipmentServiceImpl implements IDbEquipmentService {
    @Autowired
    private DbEquipmentMapper dbEquipmentMapper;

    @Autowired
    private DbQrCodeMapper dbQrCodeMapper;

    @Autowired
    private IDbQrCodeService dbQrCodeService;



    /**
     * 查询设备
     *
     * @param equipmentId 设备ID
     * @return 设备
     */
    @Override
    public DbEquipment selectDbEquipmentById(Long equipmentId) {
        return dbEquipmentMapper.selectDbEquipmentById(equipmentId);
    }

    /**
     * 查询设备列表
     *
     * @param dbEquipment 设备
     * @return 设备
     */
    @Override
    public List<DbEquipment> selectDbEquipmentList(DbEquipment dbEquipment) {
        return dbEquipmentMapper.selectDbEquipmentList(dbEquipment);
    }

    /**
     * 新增设备
     *
     * @param dbEquipment 设备
     * @return 结果
     */
    @Override
    public int insertDbEquipment(  DbEquipment dbEquipment) {
        //dbEquipment.setCreateTime(DateUtils.getNowDate());
        return dbEquipmentMapper.insertDbEquipment(dbEquipment);
    }

    /**
     * 修改设备
     *
     * @param dbEquipment 设备
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDbEquipment(DbEquipment dbEquipment) {
        //dbEquipment.setUpdateTime(DateUtils.getNowDate());

        return dbEquipmentMapper.updateDbEquipment(dbEquipment);
    }

    /**
     * 删除设备对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentByIds(String ids) {
        return dbEquipmentMapper.deleteDbEquipmentByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除设备信息
     *
     * @param equipmentId 设备ID
     * @return 结果
     */
    @Override
    public int deleteDbEquipmentById(Long equipmentId) {
        return dbEquipmentMapper.deleteDbEquipmentById(equipmentId);
    }

    @Override
    public DbEquipment selectByHeart(DbEquipment dbEquipment) {
        return dbEquipmentMapper.selectByHeart(dbEquipment.getHeartbeatText(), dbEquipment.getEquipmentNoString());
    }

    /**
     * 方法描述
     * @since: 1.0.0
     * @param prefix
     * @param suffix
     * @param openInterval
     * @param closedInterval
     * @return: java.lang.Integer
     * @author: ZHAOXIAOSI
     * @date: 2021/3/19 12:05
     */
    @Override
    @Transactional
    public Integer batchInsertEquipment(String prefix, String suffix, String openInterval, String closedInterval) throws Exception {
        List<DbEquipment> result = getResult(prefix, suffix, Integer.valueOf(openInterval), Integer.valueOf(closedInterval));
        int num = 0;
        for (DbEquipment equipment : result) {
            DbEquipment dbEquipment = dbEquipmentMapper.selectByHeartbeatText(equipment.getHeartbeatText());
            if (dbEquipment != null){
                DbQrCode dbQrCode = dbQrCodeMapper.selectDbQrCodeByEquipmentId(dbEquipment.getEquipmentId());
                if (dbQrCode != null){
                    continue;
                }else {
                    //插入二维码表 批量生成无法返回自增主键的id
                    insertDbQrCode(equipment);
                    num ++;
                }
            }
            int i = dbEquipmentMapper.insertDbEquipment(equipment);
            if (i>0){//设备表插入成功
                //插入二维码表 批量生成无法返回自增主键的id
                insertDbQrCode(equipment);
                num ++;
            }
        }
        return num;
    }

    private void  insertDbQrCode(DbEquipment equipment) throws Exception {
        DbQrCode qrCode = new DbQrCode();
        qrCode.setHeartbeatText(equipment.getHeartbeatText());
        qrCode.setEquipmentId(equipment.getEquipmentId());
        int row = dbQrCodeMapper.insertDbQrCode(qrCode);
        if (row > 0){
            //生成二维码
            String s = dbQrCodeService.qrCodeGenerate(qrCode);
            BufferedImage bufferedImage = base64ToBufferedImage(s);
            BufferedImage image = QrCodeUtils.addNote(bufferedImage, qrCode.getHeartbeatText());
            s = BufferedImageToBase64(image);
            dbQrCodeMapper.updateDbQrCodeById(qrCode.getQrCodeId(),s);
        }
    }

    /***
     * 得到指定区间规则的 设备对象
     * @since: 1.0.0
     * @param prefix
     * @param suffix
     * @param openInterval
     * @param closedInterval
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipment>
     * @author: ZHAOXIAOSI
     * @date: 2021/3/22 10:39
     */
    private List<DbEquipment> getResult(String prefix, String suffix, Integer openInterval, Integer closedInterval){
        ArrayList<DbEquipment> list = new ArrayList<>();
        String heartbeatText = "";
        while (openInterval <= closedInterval){
            DbEquipment equipment = new DbEquipment();
            heartbeatText = prefix+"-"+getNum(openInterval)+"-"+suffix;
            equipment.setHeartbeatText(heartbeatText);
            equipment.setIsFault(0);
            equipment.setCreateTime(new Date());
            equipment.setIsFault(0);
            equipment.setIsOnline(0);
            equipment.setEquipmentNo(1);
            list.add(equipment);
            System.out.println(prefix+"-"+getNum(openInterval)+"-"+suffix);
            openInterval ++;
        }
        return list;
    }

    /**
     * BufferedImage 转base 64
     * @since: 1.0.0

     * @param bufferedImage
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/3/22 10:37
     */
    private static String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return "data:image/jpg;base64," + png_base64;
    }

    /**
     * Base64 转换 BufferedImage
     * @since: 1.0.0
     * @param base64
     * @return: java.awt.image.BufferedImage
     * @author: ZHAOXIAOSI
     * @date: 2021/3/22 10:38
     */

    private  static BufferedImage base64ToBufferedImage(String base64) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] result = decoder.decode(base64.substring(22, base64.length()));
            ByteArrayInputStream bais = new ByteArrayInputStream(result);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 计算心跳中间五位前面是否补零 要补多少
     * @since: 1.0.0
     * @param openInterval
     * @return: java.lang.String
     * @author: ZHAOXIAOSI
     * @date: 2021/3/22 10:40
     */

    private static String getNum(Integer openInterval){
        String result = openInterval.toString();
        StringBuilder builder = new StringBuilder();
        int i = 5 - result.length();
        if ( i == 0 ){
            return openInterval+"";
        }
        for (int j = 0; j < i; j++) {
            builder.append("0");
        }
        return builder+result;
    }

    /***
     * 查询所有设备
     * @since: 1.0.0
     * @return: java.util.List<com.ruoyi.system.domain.DbEquipment>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/14 15:26
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */

    @Override
    public List<DbEquipment> selectAllDbEquipment() {
        return dbEquipmentMapper.selectAllDbEquipment();
    }

    public static void  main(String[] args) throws Exception {
        String s ="data:image/jpg;base64,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";
        String s1 ="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";
        String s3 = s.substring(22, s.length());
        BufferedImage image = base64ToBufferedImage(s3);
        String s2 = BufferedImageToBase64(image);
        System.out.println(image);
        System.out.println(s2);
    }

    @Override
    public String selectByHeartbeatText(String heartbeatText) {
        DbEquipment equipment = dbEquipmentMapper.selectByHeartbeatText(heartbeatText);
        return equipment.getEquipmentNoString();
    }
}
