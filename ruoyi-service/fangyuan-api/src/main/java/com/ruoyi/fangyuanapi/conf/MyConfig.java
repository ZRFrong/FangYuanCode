package com.ruoyi.fangyuanapi.conf;

import com.ruoyi.common.utils.StringUtils;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import org.apache.commons.collections4.Put;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MyConfig  {
    private byte[] certData;

//    public MyConfig() throws Exception {
//        String path = MyConfig.class.getClassLoader().getResource("apiclient_cert.p12").getPath();//发布时用这个
//        String certPath = "D:\\FANGYUAN\\FANGYUAN\\FangYuanCode\\ruoyi-service\\fangyuan-api\\src\\main\\resources\\apiclient_cert.p12";
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
//    }
//
//    public String getAppID() {
//        return "wxd678efh567hg6787";
//    }
//
//    public String getMchID() {
//        return "1586675541";
//    }
//
//    public String getKey() {
//        return "e0d46bcdb94046adaa6e9b42beb86770";
//    }
//
//    public InputStream getCertStream() {
//        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
//        return certBis;
//    }
//
//    public int getHttpConnectTimeoutMs() {
//        return 8000;
//    }
//
//    public int getHttpReadTimeoutMs() {
//        return 10000;
//    }
//    public IWXPayDomain getWXPayDomain() {
//        // 这个方法需要这样实现, 否则无法正常初始化WXPay
//        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
//
//            public void report(String domain, long elapsedTimeMillis, Exception ex) {
//
//            }
//
//            public DomainInfo getDomain(WXPayConfig config) {
//                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
//            }
//        };
//        return iwxPayDomain;
//
//    }

}
