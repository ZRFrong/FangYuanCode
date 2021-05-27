package com.ruoyi.fangyuanapi.conf;

import com.ruoyi.fangyuanapi.interceptor.RestRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @Description: 宇视云视屏调用api配置
 * @Author zheng
 * @Date 2021/5/26 10:06
 * @Version 1.0
 */
@Component
@Configuration
public class MonitorCloudApiConf {
    @Value("${monitor-cloud.appId}")
    public String appId;
    @Value("${monitor-cloud.secretKey}")
    public String secretKey;
    // device
    @Value("${monitor-cloud.api.host}")
    public String host;
    @Value("${monitor-cloud.api.interface-url.device.getToken}")
    public String tokenUrl;
    @Value("${monitor-cloud.api.interface-url.device.addDevice}")
    public String addDevice;
    @Value("${monitor-cloud.api.interface-url.device.deleteDevice}")
    public String deleteDevice;
    @Value("${monitor-cloud.api.interface-url.device.updateDevice}")
    public String updateDevice;
    @Value("${monitor-cloud.api.interface-url.device.listDevice}")
    public String listDevice;
    @Value("${monitor-cloud.api.interface-url.device.getDevice}")
    public String getDevice;
    @Value("${monitor-cloud.api.interface-url.device.queryRecordTime}")
    public String queryRecordTime;
    @Value("${monitor-cloud.api.interface-url.device.listChannel}")
    public String listChannel;
    @Value("${monitor-cloud.api.interface-url.device.getCapture}")
    public String getCapture;
    // ptz
    @Value("${monitor-cloud.api.interface-url.ptz.startPtz}")
    public String startPtz;
    @Value("${monitor-cloud.api.interface-url.ptz.stopPtz}")
    public String stopPtz;
    @Value("${monitor-cloud.api.interface-url.ptz.listPreset}")
    public String listPreset;
    @Value("${monitor-cloud.api.interface-url.ptz.addPreset}")
    public String addPreset;
    @Value("${monitor-cloud.api.interface-url.ptz.updatePreset}")
    public String updatePreset;
    @Value("${monitor-cloud.api.interface-url.ptz.deletePreset}")
    public String deletePreset;
    @Value("${monitor-cloud.api.interface-url.ptz.invokePreset}")
    public String invokePreset;
    // cdn
    @Value("${monitor-cloud.api.interface-url.cdn.stopVideo}")
    public String stopVideo;
    @Value("${monitor-cloud.api.interface-url.cdn.startVideo}")
    public String startVideo;
    @Value("${monitor-cloud.api.interface-url.cdn.getVideo}")
    public String getVideo;


    /**
     * 设置请求rest
     * @return 获取模板
     */
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        // 设置拦截器
        ArrayList<ClientHttpRequestInterceptor> interceptor = new ArrayList<>();
        interceptor.add(new RestRequestInterceptor());
        restTemplate.setInterceptors(interceptor);
        return restTemplate;
    }


    /**
     * 创建http连接池工厂
     * @return 返回连接池工厂
     */
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    /**
     * http链接池
     * 服务器返回数据(response)的时间，超过该时间抛出read timeout
     * @return http客户端
     */
    @Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        //设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(10);
        //路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(100);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000) //服务器返回数据(response)的时间，超过该时间抛出read timeout
                .setConnectTimeout(5000)//连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                .setConnectionRequestTimeout(1000)//从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}
