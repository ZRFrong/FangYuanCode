package com.ruoyi.fangyuanapi.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author just myself
 * @create 2020-10-07-21:14
 */
@Configuration
@Data
public class WeChatArgs {
    @Value("${com.fangyuan.wechat.appid}")
    private String appid ;
    @Value("${com.fangyuan.wechat.mchid}")
    private String mchid ;
    @Value("${com.fangyuan.wechat.serial_no}")
    private String serial_no;
    /**
     * 订单号
     */
    private String out_trade_no;
    /**
     * 回调地址
     */
    @Value("${com.fangyuan.wechat.notify_url}")
    private String notify_url;
    /**
     * 订单失效时间
     */
    private String time_expire;
    /**
     *单位分 订单金额
     */
    private String amount;
    /**
     * 场景信息
     */

    @JSONField(serialize=false)//不进行序列化
    private String scene_info;
    /**
     *商品描述
     */
    private String description;


    public static void main(String[] args){
       Double b = 33.3;
        Long l = b.longValue();
        System.out.println(l);
        l= l*100l;
        double v = l.doubleValue();
        System.out.println(v);
    }


}
