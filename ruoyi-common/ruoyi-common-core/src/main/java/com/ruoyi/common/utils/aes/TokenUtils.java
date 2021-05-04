package com.ruoyi.common.utils.aes;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.java.Log;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log
public class TokenUtils {

    private Cipher cipher;

    public TokenUtils(){

    }


    /**
     * 验证token
     * @param data 加密串
     * @param key
     * @return
     */
    public static Map<String, Object> verifyToken(String data,String key){
        try {
            String s = decrypt(data, key);
            Map<String,Object> map = JSON.parseObject(s, Map.class);
            String time = map.get("expireTime").toString();
            long l = System.currentTimeMillis();
            System.out.println(time);
            if (l < Long.valueOf(time)){
                return map;
            }else {
                log.info("token验证失败: "+map.get("id"));
                return null;
            }
        }catch (Exception e){
          return null;
        }
    }

    /**
     * 生成token
     * @param id 用户id
     * @param expireTime 过期时间毫秒
     * @param publisher 发行者
     * @param topic token类别
     * @param key 初始化加密对象的随机串
     * @return
     */
    public static String getToken(Long id, Long expireTime, String publisher, String topic, String key){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("expireTime",expireTime);
        map.put("publisher",publisher);
        map.put("topic",topic);
        return encrypt(JSON.toJSONString(map),key);
    }

    /**
     * AES加密
     * @param data 数据
     * @param key 作为生成秘钥的字符串
     * @return
     */
    public static String encrypt(String data,String key){
        String s = null;
        try {
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes("UTF-8"));
            aes.init(128,random);
            SecretKey secretKey = aes.generateKey();
            byte[] keyEncoded = secretKey.getEncoded();
            /*  转换为AES秘钥 */
            SecretKeySpec spec = new SecretKeySpec(keyEncoded,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] result = cipher.doFinal(data.getBytes());
            s = ParseSystemUtil.parseByte2HexStr(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * AES解密
     * @param token 加密之后生成的加密串
     * @param key 生成秘钥的随机串 加密和解密的必须是同一个
     * @return
     */
    public static String decrypt(String token,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        KeyGenerator aes = null;
        String s =null;
            aes = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes("UTF-8"));
            aes.init(128,random);
            SecretKey secretKey = aes.generateKey();
            byte[] keyEncoded = secretKey.getEncoded();
            /*  转换为AES秘钥 */
            SecretKeySpec spec = new SecretKeySpec(keyEncoded,"AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal(ParseSystemUtil.parseHexStr2Byte(token));
            s = new String(bytes);
        return s;
    }

//    public static String test(String key) throws NoSuchAlgorithmException {
//        KeyGenerator aes = KeyGenerator.getInstance("AES");
//    }

    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
//        String data = "18D0E00C491ABD397CDC1E7E93652B7D784B4AA241CC9C510EB3AF20A9ABC7CB819D10CE3F2BB18FDE9990BB7D27BCDCFAE6450E749504690A5E34F6F68A08F051A62BA57E0D5F1357B84593FE0CCC6A";
//        String key = "196B0F14EBA66E10FBA74DBF9E99C22F";
//        String encrypt = encrypt("dadadasa", key);
//        String s = decrypt(encrypt, key);
//        System.out.println(s);
//        System.out.println(encrypt("54254", "4545215431321543213").length());
//        System.out.println(System.currentTimeMillis());
//        Map<String, Object> map = TokenUtils.verifyToken("3446DEFDFE916E14E7ACB44CC7D25E70D8F3097727EE8168846B77A512A88719E10E9184DDE0795B8219910CEF356F04E57DDC4411099D4063CF36638D0ADE31", "196B0F14EBA66E10FBA74DBF9E99C22F");
//        System.out.println(map);
//        Date date = new Date();
//        date.setTime(1000*60*60*365+System.currentTimeMillis());
//        System.out.println(DateUtils.parseDateToStr("yyyy-MM-dd kk:mm:ss", date));
//        System.out.println(new SimpleDateFormat().format(date));
//        System.out.println(System.currentTimeMillis()+(1000L*60L*60L*365L*3L));
        String token = TokenUtils.getToken((long) 1, System.currentTimeMillis(), "dhsa", "1", "4513274d");
        String s = TokenUtils.decrypt("460DD35443B5A9873AFDB5A520D7BE2BCAC002D94C8F395BBD89B116205C15CB83FE972C83B3D69E30F5CFC1837FE100", "196B0F14EBA66E10FBA74DBF9E99C22F");
        System.out.println(s);
    }
}
