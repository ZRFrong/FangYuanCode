package com.ruoyi.fangyuanapi.interceptor;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.conf.MonitorCloudApiConf;
import com.ruoyi.fangyuanapi.utils.MonitorCloudRequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;

/**
 * @author chenshiyuan
 * @description: Rest远程调用拦截器
 */
@Slf4j
public class RestRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final String CHARSET = "UTF-8";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        body = traceRequest(request, body);
        ClientHttpResponse execute = execution.execute(request, body);
        log.info("==========================request end================================================");
        return execute;
    }



    /**
     * 拦截请求内容
     * @param request 本次请求
     * @param body 请求体
     * @return 最终请求体
     * @throws IOException
     */
    private byte[] traceRequest(HttpRequest request, byte[] body) throws IOException {
        log.info("===========================request begin================================================");
        String strBody = new String(body, CHARSET);
        String uri = request.getURI().toString();
        log.info("URI         : {}", uri);
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getHeaders() );
        log.info("Request body: {}", strBody);
        // 设置请求头
        if(uri.startsWith(MonitorCloudRequestUtils.monitorCloudApiConf.host)
                && !StringUtils.equals(uri,MonitorCloudRequestUtils.monitorCloudApiConf.tokenUrl)){
            request.getHeaders().add("Authorization",MonitorCloudRequestUtils.getToken());
        }
        return body;
    }


}
