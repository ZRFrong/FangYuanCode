package com.ruoyi.fangyuanapi.controller;

import com.github.wxpay.sdk.WXPay;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.conf.MyConfig;
import com.ruoyi.fangyuanapi.wechat.WeChatPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("wx/v3")
public class WxPayController {

    @Autowired
    private WeChatPay weChatPay;

    @PostMapping("wxpay")
    public R  wxPay(HttpServletRequest request){
        String addr = IpUtils.getIpAddr(request);
        String s = null;
        try {
            s = weChatPay.send(addr, 5451354L, 123456L);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok(s);
    }


    @RequestMapping("notify")
    public R notifyUrl(){

        return R.ok();
    }

    public static void main(String[] args) throws Exception {

//        MyConfig myConfig = new MyConfig();
//        WXPay pay = new WXPay(myConfig);
//        SortedMap<String,String> data = new TreeMap<String,String>();
//        data.put("nonce_str",StringUtils.getUUID());
//        data.put("sign",ASCIISortUtil.createSign("utf-8",data));//签名
//        data.put("body", "腾讯充值中心-QQ会员充值");
//        data.put("out_trade_no", "2016090910595900000012");
//        data.put("device_info", "");
//        data.put("fee_type", "CNY");
//        data.put("total_fee", "0.01");
//        data.put("spbill_create_ip", "123.12.12.123");//终端ip
//        data.put("notify_url", "http://www.example.com/wxpay/notify");//通知地址
//        data.put("trade_type", "MWEB");  // 此处指定为扫码支付
//        data.put("product_id", "12");
//        data.put("scene_info","{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"https://pay.qq.com\",\"wap_name\": \"腾讯充值\"}}");
//        Map<String, String> map = pay.unifiedOrder(data);
//        System.out.println(map);
//        System.out.println(StringUtils.getUUID());

    }

}
