package com.ruoyi.common.utils.aes;
import com.alibaba.fastjson.JSON;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

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
            if (l < Long.valueOf(time)){
                return map;
            }else {
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
            aes.init(128,new SecureRandom(key.getBytes()));
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
        }
        return s;
    }

    /**
     * AES解密
     * @param token 加密之后生成的加密串
     * @param key 生成秘钥的随机串 加密和解密的必须是同一个
     * @return
     */
    public static String decrypt(String token,String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator aes = null;
        String s =null;
            aes = KeyGenerator.getInstance("AES");
            aes.init(128,new SecureRandom(key.getBytes()));
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
    public static void main(String[] args){
        System.out.println(encrypt("54254", "4545215431321543213").length());
    }
}
