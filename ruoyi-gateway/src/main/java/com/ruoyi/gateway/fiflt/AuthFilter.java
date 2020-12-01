package com.ruoyi.gateway.fiflt;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

/**
 * 网关鉴权
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    // 排除过滤的 uri 地址
    // swagger排除自行添加
    private static final String[] whiteList = {"/auth/login", "/user/register", "/system/v2/api-docs", "/fangyuanapi/v2/api-docs", "/fangyuantcp/v2/api-docs",
            "/auth/captcha/check", "/auth/captcha/get", "/act/v2/api-docs", "/auth/login/slide", "/system/appVersion/downapp","/fangyuanapi/operateApp/oprateLand"};

    @Resource(name = "stringRedisTemplate")
    private ValueOperations<String, String> ops;


    /**
     * 方便测试，
     */
    private static final List<String> zhao = Arrays.asList("/system/sms/","fangyuanapi/wxUser/appLogin","fangyuanapi/wxUser/appRegister","fangyuanapi/dynamic1","fangyuanapi/category","fangyuanapi/wx/v3",
            "/fangyuanapi/order/insertOrder","fangyuanapi/giveLike","fangyuanapi/wxUser/getOpenId","fangyuanapi/wxUser/smallRegister","/fangyuanapi/banner/getBannerList","fangyuanapi/wxUser/appUpdatePassword");

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
            if (url.contains(s)){
                return chain.filter(exchange);
            }
        }
        // 跳过不需要验证的路径
        if (Arrays.asList(whiteList).contains(url)) {
            return chain.filter(exchange);
        }

        // token为空
        if (StringUtils.isBlank(token)) {
            return setUnauthorizedResponse(exchange, "token can't null or empty string","401");
        }
        //        请求来自客户端api 转化token为userHomeId
        if (url.contains("fangyuanapi")) {
            Map<String, Object> map = TokenUtils.verifyToken(token, tokenConf.getAccessTokenKey());
            if (map != null){
                /* id == null token被篡改 解密失败 */
                String id = map.get("id")+"";
                String redisToken = ops.get(RedisKeyConf.ACCESS_TOKEN_.name() + id);
                if (!token.equals(redisToken)){
                    return setUnauthorizedResponse(exchange, "token verify error","403");
                }
                ServerHttpRequest mutableReq = exchange.getRequest().mutate().header(Constants.CURRENT_ID, id).build();
                ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();
                return chain.filter(mutableExchange);
            }
        }

        String userStr = ops.get(Constants.ACCESS_TOKEN + token);
        if (StringUtils.isBlank(userStr)) {
            return setUnauthorizedResponse(exchange, "token verify error","401");
        }
        JSONObject jo = JSONObject.parseObject(userStr);
        String userId = jo.getString("userId");
        // 查询token信息
        if (StringUtils.isBlank(userId)) {
            return setUnauthorizedResponse(exchange, "token verify error","401");
        }

        // 设置userId到request里，后续根据userId，获取用户信息
        ServerHttpRequest mutableReq = exchange.getRequest().mutate().header(Constants.CURRENT_ID, userId)
                .header(Constants.CURRENT_USERNAME, jo.getString("loginName")).build();
        ServerWebExchange mutableExchange = exchange.mutate().request(mutableReq).build();

        // ip id time
        return chain.filter(mutableExchange);
    }


    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg,String code) {
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

    @Override
    public int getOrder() {
        return -200;
    }
}