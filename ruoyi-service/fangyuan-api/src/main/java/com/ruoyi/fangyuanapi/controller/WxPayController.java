package com.ruoyi.fangyuanapi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.IpUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.fangyuanapi.service.IDbOrderService;
import com.ruoyi.fangyuanapi.wechat.WeChatPay;
import com.ruoyi.system.domain.DbOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("wx/v3")
public class WxPayController extends BaseController {

    @Autowired
    private WeChatPay weChatPay;

    @Autowired
    private IDbOrderService orderService;

    /**
     * 微信h5下单接口
     * @param request
     * @return
     */
    @PostMapping("wxpay")
    public R  wxPay(HttpServletRequest request){
        String addr = IpUtils.getIpAddr(request);
        String s = null;
        try {
            s = weChatPay.send(addr, 5451354L, 123456L);
            if (StringUtils.isEmpty(s)){
                return R.error(ResultEnum.WE_CHAT_PAY_ERROR.getCode(),ResultEnum.WE_CHAT_PAY_ERROR.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.data(s);
    }

    /**
     * 微信支付回调接口
     * @return
     */
    @RequestMapping("notify")
    public String notifyUrl() {
        HttpServletRequest request = getRequest();
        String Wechatpay_Timestamp = request.getHeader("Wechatpay-Timestamp");
        //时间戳
        String Wechatpay_Nonce = request.getHeader("Wechatpay-Nonce");
        //随机串
        String Wechatpay_Signature = request.getHeader("Wechatpay-Signature");
        //签名
        String Wechatpay_Serial = request.getHeader("Wechatpay-Serial");
        //证书序列号
        String str = null;
        //body
        HashMap<String, String> map = new HashMap<>();
        String code = "ERROR_NAME";
        String message = null;
        BufferedReader breader = null;
        try { breader = new BufferedReader(new InputStreamReader(request.getInputStream(),Charset.forName("UTF-8")));
        StringBuffer strb = new StringBuffer();
        while(null != (str = breader.readLine())) {
            strb.append(str);
        }
        str = strb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean b = weChatPay.validateNotify(Wechatpay_Timestamp, Wechatpay_Nonce, str, Wechatpay_Signature, Wechatpay_Serial);
        if (!b){
            message = "验签失败！";
            return JSON.toJSONString(map);
        }
        String data = weChatPay.AesData(str);
        JSONObject resource = JSON.parseObject(data);
        String orderId = resource.getString("out_trade_no");
        String tradeState = resource.getString("trade_state");
        if ("SUCCESS".equals(tradeState)){
            /* 支付成功 ； 先查询数据库订单状态  */
            code = "SUCCESS";
            DbOrder order = orderService.selectDbOrderById(Long.valueOf(orderId));
            if (order.getIsSusses() == 0){
                order.setIsSusses(1);
                orderService.updateDbOrder(order);
            }
            return JSON.toJSONString(map);
        }
        return JSON.toJSONString(map);
    }

}
