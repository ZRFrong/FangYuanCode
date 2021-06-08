package com.ruoyi.fangyuanapi.utils;

import com.ruoyi.common.json.JSONObject;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuanapi.conf.MonitorCloudApiConf;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 宇视云请求工具类
 * @Author zheng
 * @Date 2021/5/26 10:39
 * @Version 1.0
 */
@Log4j2
public class MonitorCloudRequestUtils {

    public final static MonitorCloudApiConf monitorCloudApiConf = SpringUtils.getBean(MonitorCloudApiConf.class);
    private static RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);
    private static RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
    private static final String MONITOR_API_TOKEN_PREFIX = "MONITOR_API_TOKEN:";

    /**
     * 获取令牌Token
     * @return Token字符
     */
    @SneakyThrows
    public static String getToken() {
        String token = redisUtils.get(MONITOR_API_TOKEN_PREFIX);
        if(StringUtils.isNotEmpty(token)){
            return token;
        }
        String url = monitorCloudApiConf.tokenUrl;
        Map<String,Object> map = new HashMap<>(2);
        map.put("appId",monitorCloudApiConf.appId);
        map.put("secretKey",monitorCloudApiConf.secretKey);
        JSONObject body = postForEntity(url, map);
        try{
            String accessToken = body.getObj("data").getStr("accessToken");
            Long expireTime = body.getObj("data").getLong("expireTime");
            expireTime = expireTime - (System.currentTimeMillis()/1000);
            redisUtils.set(MONITOR_API_TOKEN_PREFIX,accessToken,expireTime);
            return accessToken;
        }catch (Exception e){
            throw new Exception("获取Token异常：",e);
        }
    }

    /**
     * 添加设备
     * @param registerCode 这边设备注册号
     * @param deviceName 设备名称
     * @return 设备序列号
     */
    @SneakyThrows
    public static String addDevice(String registerCode,String deviceName) {
        String url = monitorCloudApiConf.addDevice;
        Map<String,Object> map = new HashMap<>(2);
        map.put("registerCode",registerCode);
        map.put("deviceName",deviceName);
        JSONObject body = postForEntity(url, map);
        try{
            return body.getObj("data").getStr("deviceSerial");
        }catch (Exception e){
            throw new Exception("添加设备异常：",e);
        }
    }

    /**
     * 删除设备
     * @param deviceSerial 序列号
     */
    @SneakyThrows
    public static void deleteDevice(String deviceSerial) {
        String url = monitorCloudApiConf.deleteDevice;
        Map<String,Object> map = new HashMap<>(1);
        map.put("deviceSerial",deviceSerial);
        postForEntity(url, map);
    }

    /**
     * 修改设备
     * @param deviceSerial 序列号
     * @param deviceName 名称
     */
    @SneakyThrows
    public static void updateDevice(String deviceSerial,String deviceName) {
        String url = monitorCloudApiConf.updateDevice;
        Map<String,Object> map = new HashMap<>(1);
        map.put("deviceSerial",deviceSerial);
        map.put("deviceName",deviceName);
        postForEntity(url, map);
    }

    @SneakyThrows
    public static JSONObject listDevice(int pageNo,int pageSize) {
        String url = monitorCloudApiConf.listDevice;
        Map<String,Object> map = new HashMap<>(2);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        JSONObject body = postForEntity(url, map);
        try{
            return body.getObj("data");
        }catch (Exception e){
            throw new Exception("获取设备列表异常：",e);
        }
    }


    @SneakyThrows
    public static JSONObject getDevice(String deviceSerial) {
        String url = monitorCloudApiConf.getDevice;
        Map<String,Object> map = new HashMap<>(2);
        map.put("deviceSerial",deviceSerial);
        JSONObject body = postForEntity(url, map);
        try{
            return body.getObj("data");
        }catch (Exception e){
            throw new Exception("获取设备信息异常：",e);
        }
    }

    /**
     * 获取设备录像时间段
     * @param deviceSerial 设备序列号
     * @param channelNo 通道号，从1开始
     * @param startTime 开始时间，UTC时间
     * @param endTime 结束时间，UTC时间，查询时间跨度不能超过一天
     * @param streamIndex 流索引
     * @param recordType 录像类型（0-全部录像，1-普通录像 2-事件录像），默认为全部录像（暂不支持）
     * @param srcType 回放源（0-系统自动选择，1-云存储，2-本地录像），默认为系统自动选择（暂不支持）
     * @return 时间段集合
     */
    @SneakyThrows
    public static JSONObject.JSONArray queryRecordTime(String deviceSerial,int channelNo,long startTime,
        long endTime,int streamIndex,int recordType,int srcType) {
        String url = monitorCloudApiConf.queryRecordTime;
        Map<String,Object> map = new HashMap<>(7);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("streamIndex",streamIndex);
        map.put("recordType",recordType);
        map.put("srcType",srcType);
        JSONObject body = postForEntity(url, map);
        try{
            return body.getObj("data").getArr("recordList");
        }catch (Exception e){
            throw new Exception("获取设备录像时间段异常：",e);
        }
    }

    /**
     * 获取设备通道
     * @param deviceSerial 序列号
     * @param pageNo 页数
     * @param pageSize 页大小
     * @return 通道集合
     */
    @SneakyThrows
    public static JSONObject listChannel(String deviceSerial,int pageNo,int pageSize) {
        String url = monitorCloudApiConf.listChannel;
        Map<String,Object> map = new HashMap<>(2);
        map.put("deviceSerial",deviceSerial);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        JSONObject body = postForEntity(url, map);
        try{
            return body.getObj("data");
        }catch (Exception e){
            throw new Exception("获取设备通道列表异常：",e);
        }
    }

    @SneakyThrows
    public static String  getCapture(String deviceSerial,int channelNo) {
        String url = monitorCloudApiConf.getCapture;
        Map<String,Object> map = new HashMap<>(2);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        JSONObject body = postForEntity(url, map);
        try{
            return body.getObj("data").getStr("url");
        }catch (Exception e){
            throw new Exception("获取设备抓图异常：",e);
        }
    }

    /**
     * 开始云台控制
     * @param deviceSerial
     * @param channelNo
     * @param command 操作命令，0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距
     * @param speed 云台转速，1-9，9最快
     */
    @SneakyThrows
    public static void startPtz(String deviceSerial,int channelNo,int command,int speed) {
        String url = monitorCloudApiConf.startPtz;
        Map<String,Object> map = new HashMap<>(4);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("command",command);
        map.put("speed",speed);
        postForEntity(url, map);
    }

    /**
     * 停止云台控制
     * @param command 操作命令，0-上，1-下，2-左，3-右，4-左上，5-左下，6-右上，7-右下，8-放大，9-缩小，10-近焦距，11-远焦距
     */
    @SneakyThrows
    public static void stopPtz(String deviceSerial,int channelNo,int command) {
        String url = monitorCloudApiConf.stopPtz;
        Map<String,Object> map = new HashMap<>(3);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("command",command);
        postForEntity(url, map);
    }

    /**
     * 获取预置位列
     */
    @SneakyThrows
    public static JSONObject listPreset(String deviceSerial,int channelNo,int pageNo,int pageSize) {
        String url = monitorCloudApiConf.listPreset;
        Map<String,Object> map = new HashMap<>(3);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        try{
            JSONObject body = postForEntity(url, map);
            return body.getObj("data");
        }catch (Exception e){
            throw new Exception("获取预置位列异常：",e);
        }
    }

    /**
     * 添加预置位
     * @return 预置位图像
     */
    @SneakyThrows
    public static String addPreset(String deviceSerial,int channelNo,int presetIndex,String name) {
        String url = monitorCloudApiConf.addPreset;
        Map<String,Object> map = new HashMap<>(4);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("presetIndex",presetIndex);
        map.put("name",name);
        try{
            JSONObject body = postForEntity(url, map);
            return body.getObj("data").getStr("image");
        }catch (Exception e){
            throw new Exception("添加预置位异常：",e);
        }
    }

    /**
     * 修改预置位
     */
    @SneakyThrows
    public static void updatePreset(String deviceSerial,int channelNo,int presetIndex,String name) {
        String url = monitorCloudApiConf.updatePreset;
        Map<String,Object> map = new HashMap<>(4);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("presetIndex",presetIndex);
        map.put("name",name);
        try{
            postForEntity(url, map);
        }catch (Exception e){
            throw new Exception("修改预置位异常：",e);
        }
    }

    /**
     * 删除预置位
     */
    @SneakyThrows
    public static void deletePreset(String deviceSerial,int channelNo,int presetIndex) {
        String url = monitorCloudApiConf.deletePreset;
        Map<String,Object> map = new HashMap<>(4);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("presetIndex",presetIndex);
        postForEntity(url, map);
    }

    /**
     * 调用预置位
     */
    @SneakyThrows
    public static void invokePreset(String deviceSerial,int channelNo,int presetIndex) {
        String url = monitorCloudApiConf.invokePreset;
        Map<String,Object> map = new HashMap<>(4);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("presetIndex",presetIndex);
        try{
            postForEntity(url, map);
        }catch (Exception e){
            throw new Exception("调用预置位异常：",e);
        }
    }


    /**
     * 停止直播推流
     * @param liveId 视频流ID
     */
    @SneakyThrows
    public static void stopVideo(String liveId) {
        String url = monitorCloudApiConf.stopVideo;
        Map<String,Object> map = new HashMap<>(1);
        map.put("liveId",liveId);
        try{
            postForEntity(url, map);
        }catch (Exception e){
            throw new Exception("停止直播推流异常：",e);
        }
    }

    /**
     * 开启直播/回放
     * @param videoUrl 视频播放地址
     */
    @SneakyThrows
    public static String startVideo(String videoUrl) {
        String url = monitorCloudApiConf.startVideo;
        Map<String,Object> map = new HashMap<>(1);
        map.put("videoUrl",videoUrl);
        try{
           return postForEntity(url, map).getObj("data").getStr("liveId");
        }catch (Exception e){
            throw new Exception("开启直播/回放异常：",e);
        }
    }


    /**
     * 获取指定设备通道的直播/回放视频地址
     * @param deviceSerial 设备序列号
     * @param channelNo 通道号
     * @param streamIndex 媒体流索引
     * @param streamType 媒体流类型，默认为live,取值live为直播，record为回放
     * @param startTime 开始时间，UTC时间，streamType为record时必选
     * @param endTime 结束时间，UTC时间，查询时间跨度不能超过一天，streamType为record时必选
     * @param recordTypes 录像类型（0-全部录像，1-普通录像 2-事件录像），默认为全部录像，streamType为record时必选
     * @return 视屏地址 {rtmpUrl-RTMP直播地址, hlsUrl-HLS直播地址, flvUrl-FLV直播地址, status-直播状态（0-推流中 1-停止 2-错误）}
     */
    @SneakyThrows
    public static JSONObject getVideo(String deviceSerial,int channelNo,int streamIndex,String streamType
        ,Long startTime,Long endTime,Integer recordTypes) {
        String url = monitorCloudApiConf.getVideo;
        Map<String,Object> map = new HashMap<>(4);
        map.put("deviceSerial",deviceSerial);
        map.put("channelNo",channelNo);
        map.put("streamIndex",streamIndex);
        map.put("streamType",streamType);
        if("record".equals(streamType)){
            map.put("startTime",startTime);
            map.put("endTime",endTime);
            map.put("recordTypes",recordTypes);
        }
        try{
            return postForEntity(url, map).getObj("data");
        }catch (Exception e){
            throw new Exception("获取指定设备通道的直播/回放视频地址异常：",e);
        }
    }


    /**
     * 执行调用
     * @param url 请求地址
     * @param map 参数
     * @return 响应体
     * @throws Exception
     */
    private static JSONObject postForEntity(String url,Map<String,Object> map) throws Exception {
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(url, map, JSONObject.class);
        JSONObject body = responseEntity.getBody();
        resolveRes(body,url);
        return body;
    }

    /**
     * 解析请求响应
     * @param body 响应体
     * @throws Exception 解析异常
     */
    private static void resolveRes(JSONObject body,String requestUrl) throws Exception {
        log.info("MonitorCloudRequestUtils.resolveRes url:[{}], responseBody:[{}]",requestUrl,body);
        if(!Objects.isNull(body)){
            if(HttpStatus.OK.value() == body.getInt("code") ){
                return;
            }
            throw new Exception("请求三方接口返回状态异常");
        }
        throw new Exception("请求三方接口异常");
    }
}
