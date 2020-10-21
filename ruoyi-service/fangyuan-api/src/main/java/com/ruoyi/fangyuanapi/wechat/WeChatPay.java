package com.ruoyi.fangyuanapi.wechat;

import com.alibaba.fastjson.JSON;
import feign.Body;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;

/**
 * @author just myself
 * @create 2020-10-06-23:03
 */
@Slf4j
@Component
public class WeChatPay {


    @Autowired
    private WeChatArgs weChatArgs;



    public  String send(String ip, long price, long orderId) throws Exception {
        HashMap<String, Object> body = new HashMap<>();
        body.put("appid",weChatArgs.getAppid());
        body.put("mchid",weChatArgs.getMchid());
        //body.put("serial_no",weChatArgs.getSerial_no());
        body.put("notify_url",weChatArgs.getNotify_url());
        body.put("out_trade_no",orderId+"");
        body.put("description","方圆村智慧农业产品！");
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",price+"");
        map.put("currency","CNY");
        body.put("amount",map);
        HashMap<String, Object> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map1.put("payer_client_ip",ip);
        map2.put("type","Wap");
        map1.put("h5_info",map2);
        body.put("scene_info",map1);
        System.out.println(weChatArgs);
        String data = JSON.toJSONString(body);
        System.out.println(data);
        String Authorization = "WECHATPAY2-SHA256-RSA2048 " + getToken("POST", "https://api.mch.weixin.qq.com/v3/pay/transactions/h5", data);
        String str = "";
        try {
            str = Request.Post("https://api.mch.weixin.qq.com/v3/pay/transactions/h5")
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", Authorization)
                    .bodyString(data, ContentType.APPLICATION_JSON)
                    .execute()
                    .returnContent()
                    .asString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(str);
        Map<String,String> o = (Map<String,String>) JSON.parse(str);
        return o.get("h5_url");
    }
    public  String nonceStr = System.currentTimeMillis()+"";
    public  long timestamp = System.currentTimeMillis() / 1000;
    public  String getToken(String method, String url, String body) throws Exception {
        String message = WeChatPay.buildMessage(method, url, timestamp, nonceStr, body);
        String signature = sign(message.getBytes("utf-8"));
        return "mchid=\"" + weChatArgs.getMchid() + "\"," + "nonce_str=\"" + nonceStr + "\"," + "timestamp=\"" + timestamp + "\"," + "serial_no=\"" + weChatArgs.getSerial_no() + "\"," + "signature=\"" + signature + "\"";
    }

    public static String buildMessage(String method, String url, long timestamp, String nonceStr, String body) {
        String str = method + "\n" + url + "\n" + timestamp + "\n" + nonceStr + "\n" + body + "\n";
        return str;
    }

    public static String sign(byte[] message) throws Exception {
        String path = WeChatPay.class.getClassLoader().getResource("apiclient_key.pem").getPath();//不能有中文路径
        String certPath = "D:\\FANGYUAN\\FANGYUAN\\FangYuanCode\\ruoyi-service\\fangyuan-api\\src\\main\\resources\\apiclient_key.pem";
        PrivateKey yourPrivateKey = WeChatPay.getPrivateKey(certPath);
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(yourPrivateKey);
        sign.update(message);
        return Base64.getEncoder().encodeToString(sign.sign());
    }

    public static PrivateKey getPrivateKey(String filename) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(filename)), "UTF-8");
        try {
            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");

            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(
                        new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("当前Java环境不支持RSA", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("无效的密钥格式");
        }
    }

//    public static PrivateKey getPrivateKey(String filename) throws Exception {
//        String content = new String(Files.readAllBytes(Paths.get(filename)), "utf-8");
//        try {
//            String privateKey = content.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//            return kf.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey)));
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("当前Java环境不支持RSA", e);
//        } catch (InvalidKeySpecException e) {
//            throw new RuntimeException("无效的密钥格式");
//        }
//    }

    public static void main(String[] args) throws Exception {
//        String str ="{\t\n" +
//                "\"h5_url\": \"https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id=wx2016121516420242444321ca0631331346&package=1405458241\"\n" +
//                "}";
//        Map<String,String> o = (Map<String,String>) JSON.parse(str);
//        System.out.println(o.get("h5_url"));

//        String s = WeChatPay.class.getClassLoader().getResource("apiclient_key.pem").getPath();//不能有中文路径
//        URL url = WeChatPay.class.getClassLoader().getResource("apiclient_key.pem");
//        String path1 = url.getPath();
//        System.out.println(s);
//        String certPath = "D:\\FANGYUAN\\FANGYUAN\\FangYuanCode\\ruoyi-service\\fangyuan-api\\src\\main\\resources\\apiclient_cert.pem";
//        Request get = Request.Get("https://is0zt7ts.xiaomy.net/fangyuanapi/category/getCategory");
//        String s = get.execute().returnContent().asString();
//        System.out.println(s);
        String str ="L5wOLPeAMnmvusJ45BE30y/YCtFBWAvyMgyBalWEYvSK2o3c6KcrGmhjSDD0yz9ttttlV22s3BVn52w7i+t16768L5FO2RcTYLfGj4rpOxll8C3StA7fgQFfsoQtBg/kCY8eErjhYfxwXJp8Jab3WOWog0kyuvj1ZkRwjAc4lMe1NDa11IdaSn9kqg8PuGnFpHbrmDN8ZnmZKEeiulo4nkv7nvIu/iLvNerxOnHkBb6zvB+EOINzWcsVBVRIkYCgzvWUlOzPhmCAJ+UxjqsvQtp2yxC96jQacwzNS8tBAnIOJbq4XSdva7ZHI30ulxB+MSpdcV0fsd/ub/mz5KX+vQ==";
        String s = "TDV3T0xQZUFNbm12dXNKNDVCRTMweS9ZQ3RGQldBdnlNZ3lCYWxXRVl2U0sybzNjNktjckdtaGpTREQweXo5dHR0dGxWMjJzM0JWbjUydzdpK3QxNjc2OEw1Rk8yUmNUWUxmR2o0cnBPeGxsOEMzU3RBN2ZnUUZmc29RdEJnL2tDWThlRXJqaFlmeHdYSnA4SmFiM1dPV29nMGt5dXZqMVprUndqQWM0bE1lMU5EYTExSWRhU245a3FnOFB1R25GcEhicm1ETjhabm1aS0VlaXVsbzRua3Y3bnZJdS9pTHZOZXJ4T25Ia0JiNnp2QitFT0lOeldjc1ZCVlJJa1lDZ3p2V1VsT3pQaG1DQUorVXhqcXN2UXRwMnl4Qzk2alFhY3d6TlM4dEJBbklPSmJxNFhTZHZhN1pISTMwdWx4QitNU3BkY1YwZnNkL3ViL216NUtYK3ZRPT0=";
        String encode = Base64Util.encode(str);
        System.out.println(encode);
    }
}
