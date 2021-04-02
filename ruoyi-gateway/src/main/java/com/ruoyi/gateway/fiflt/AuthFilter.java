package com.ruoyi.gateway.fiflt;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.utils.aes.TokenUtils;
import com.ruoyi.gateway.config.TokenConf;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * 网关鉴权
 */
@Slf4j
@Component

public class AuthFilter implements GlobalFilter, Ordered {
    // 排除过滤的 uri 地址
    // swagger排除自行添加
    private static final String[] whiteList = {"/auth/login", "/user/register", "/system/v2/api-docs", "/fangyuanapi/v2/api-docs", "/fangyuantcp/v2/api-docs",
            "/auth/captcha/check", "/auth/captcha/get", "/act/v2/api-docs", "/auth/login/slide", "/system/appVersion/checkUpdate", "/system/appVersion/downapp",
            "/fangyuanapi/qrcode/qrCodeGenerate", "/system/appVersion/checkUpdate"};

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> ops;


    /**
     * 方便测试，
     */
    private static final List<String> zhao = Arrays.asList("/system/sms/", "fangyuanapi/wxUser/appLogin", "fangyuanapi/wxUser/appRegister", "fangyuanapi/dynamic1", "fangyuanapi/category", "fangyuanapi/wx/v3",
            "/fangyuanapi/order/insertOrder", "fangyuanapi/giveLike", "fangyuanapi/wxUser/getOpenId", "fangyuanapi/wxUser/smallRegister",
            "/fangyuanapi/banner/getBannerList", "fangyuanapi/wxUser/appUpdatePassword", "/fangyuanapi/qrcode/qrCodeGenerate",
            "qrCodeGenerate", "weather", "system/apk/upload", "/fangyuanapi/wxUser/filesUpload", "problem", "questions", "type", "/control/getControlSystem/","/control/sendInstruct"
    );

    @Autowired
    private TokenConf tokenConf;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String url = exchange.getRequest().getURI().getPath();
        String token = exchange.getRequest().getHeaders().getFirst(Constants.TOKEN);
        log.info("url:{}", url);
        /**
         * 方便测试，
         */
        for (String s : zhao) {
            if (url.contains(s)) {
                return chain.filter(exchange);
            }
        }
        // 跳过不需要验证的路径
        if (Arrays.asList(whiteList).contains(url)) {
            return chain.filter(exchange);
        }

        // token为空
        if (StringUtils.isBlank(token)) {
            if (!url.contains("websocket")) {

                return setUnauthorizedResponse(exchange, "token can't null or empty string", "401");
            }
        }
//        token解析
        Map<String, Object> map2 = TokenUtils.verifyToken(token, tokenConf.getAccessTokenKey());
        String id = "";
        JSONObject jo = null;
        if (map2 != null) {
            id = map2.get("id") + "";
            if ("1".equals(map2.get("type") + "")) {
                String redisToken = ops.get(RedisKeyConf.APP_ACCESS_TOKEN_.name() + id);
                if (!token.equals(redisToken)) {
                    return setUnauthorizedResponse(exchange, "token verify error", "403");
                }
            }
        } else {
            String userStr = ops.get(Constants.ACCESS_TOKEN + token);
            if (StringUtils.isBlank(userStr)) {
                if (!url.contains("websocket")) {
                    return setUnauthorizedResponse(exchange, "token verify error", "401");
                }
            }else {
                jo = JSONObject.parseObject(userStr);
                id = jo.getString("userId");
            }

        }

        //        请求来自客户端api 转化token为userHomeId
        if (url.contains("fangyuanapi") || url.contains("websocket")) {
            /* id == null token被篡改 解密失败 */

            //此处对ws开头的url就进行重写 将userid加到后面
            if (url.contains("websocket")) {
                addOriginalRequestUrl(exchange, exchange.getRequest().getURI());
                String newPath = "";
                if (id == null||id.equals("")) {
                    List<String> collect = Arrays.stream(url.split("/").clone()).skip(3).collect(Collectors.toList());
                    String s = collect.get(0);
                    Map<String, Object> map3 = TokenUtils.verifyToken(s, tokenConf.getAccessTokenKey());
                    if (map3 != null) {
                        url.replace(s, map3.get("id") + "");

                    } else {
                        String userStr = ops.get(Constants.ACCESS_TOKEN + s);
                        if (StringUtils.isBlank(userStr)) {
                            return setUnauthorizedResponse(exchange, "token verify error", "401");
                        }
                        jo = JSONObject.parseObject(userStr);
                        String userId = url.replace(s, jo.getString("userId"));
                    newPath = userId;
                    }
                } else {
                    newPath = url + "/" + id;
                }
                ServerHttpRequest newRequest = exchange.getRequest().mutate()
                        .path(newPath)
                        .build();
                exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());
                return chain.filter(exchange.mutate()
                        .request(newRequest).build());
            }

            ServerHttpRequest mutableReq = exchange.getRequest().mutate().header(Constants.CURRENT_ID, id).build();
            ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
            return chain.filter(mutableExchange);
        }

        // 查询token信息
        if (StringUtils.isBlank(id)) {
            return setUnauthorizedResponse(exchange, "token verify error", "401");
        }

        // 设置userId到request里，后续根据userId，获取用户信息
        ServerHttpRequest mutableReq = exchange.getRequest().mutate().header(Constants.CURRENT_ID, id)
                .header(Constants.CURRENT_USERNAME, jo.getString("loginName")).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();

        // ip id time
        return chain.filter(mutableExchange);
    }


    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg, String code) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = null;
        try {
            response = JSON.toJSONString(R.error(401, msg)).getBytes(Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }
    public static void addOriginalRequestUrl(ServerWebExchange exchange, URI url) {
        exchange.getAttributes().computeIfAbsent(GATEWAY_ORIGINAL_REQUEST_URL_ATTR,
                s -> new LinkedHashSet<>());
        LinkedHashSet<URI> uris = exchange
                .getRequiredAttribute(GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        uris.add(url);
    }
    @Override
    public int getOrder() {
        return -200;
    }
}