package com.ruoyi.fangyuanapi.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.config.RedisKeyConf;
import com.ruoyi.common.redis.util.RedisUtils;
import com.ruoyi.common.utils.HttpUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.fangyuanapi.conf.AlmanacConf;
import com.ruoyi.fangyuanapi.conf.WeatherConf;
import com.ruoyi.fangyuanapi.dto.AlmanacDto;
import com.ruoyi.fangyuanapi.dto.WeatherDto;
import com.ruoyi.system.domain.WeatherVo;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 *天气调用服务
 * */
@RestController
@Api("weather")
@RequestMapping("weather")
@Log4j2
public class WeatherController {

    @Autowired
    private WeatherConf weatherConf;

    private HttpUtil httpUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AlmanacConf almanacConf;

    /**
     *
     * @since: 2.0.0
     * @param area
     * @param areaCode
     * @param date
     * @return: com.ruoyi.common.core.domain.R
     * @author: ZHAOXIAOSI
     * @date: 2021/4/23 14:36
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    @GetMapping("getWeatherByAreaCode/{area}/{areaCode}/{date}/{purpose}")
    @ApiOperation(value = "天气查询接口",notes = "会返回当天黄历信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "area",value = "地区， 要和AreaCode对应 例： 太原",required = true),
            @ApiImplicitParam(name = "areaCode",value = "地区编码 例： 140100",required = true),
            @ApiImplicitParam(name = "date",value = "时间 格式例：20210423",required = true),
            @ApiImplicitParam(name = "purpose",value = "根据传入得值返回信息 0：天气和黄历信息都会返回 1：只返回黄历信息",required = true)
    })
    public R getWeatherByAreaCode(@PathVariable(name = "area") String area,@PathVariable(name = "areaCode") String areaCode,@PathVariable(name = "date") String date,
                                  @PathVariable(name = "purpose") Integer purpose){
        Map<String,String> hl = (Map<String, String>) JSON.parse(redisUtils.get(RedisKeyConf.ALMANAC_ + date));
        if (CollectionUtils.isEmpty(hl)){
            hl = getLaoHuangLi(date);
        }
        AlmanacDto almanacDto = AlmanacDto.builder()
                .tapuList(hl.get("ji"))
                .towardlyList(hl.get("yi"))
                .build();
        if (purpose == 1){
            return R.data(almanacDto);
        }
        String str = getWeather(area, areaCode, date);
        if (str == null){
            return R.error("获取天气失败，请检查地区编码是否存在！");
        }
        JSONObject showapiResBody = JSON.parseObject(str);
        Map<String,Object> f1 = null;
        for (int i = 1; i < 8; i++) {
            if (!CollectionUtils.isEmpty(f1)){
                break;
            }
            f1 = (Map<String, Object>) showapiResBody.get("f"+i);
        }
        List<Map<String,String>> hourForcast = (List<Map<String, String>>) f1.get("3hourForcast");
        int i = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        i = i > 8 ? (i-8) /3 +1 : (i+24) /3 + 1;
        WeatherDto weatherDto = WeatherDto.builder()
                .temperature(hourForcast.get(i).get("temperature"))
                .temperatureMax(hourForcast.get(i).get("temperature_max"))
                .temperatureMin(hourForcast.get(i).get("temperature_min"))
                .weatherPic(hourForcast.get(i).get("weather_pic"))
                .weather(hourForcast.get(i).get("weather"))
                .almanacDto(almanacDto)
                .build();
        return R.data(weatherDto);
    }

    @GetMapping("getAlmanac/{date}")
    public R getAlmanac(@PathVariable String date){

        return null;
    }

    /**
     * 获取聚合数据的老黄历
     * @since: 2.0.0
     * @param date
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @author: ZHAOXIAOSI
     * @date: 2021/4/24 11:17
     * @sign: 他日若遂凌云志,敢笑黄巢不丈夫。
     */
    private Map<String,String> getLaoHuangLi(String date){
        HashMap<String, String> query = new HashMap<>();
        query.put("date",date);
        query.put("key",almanacConf.getKey());
        try {
            HttpResponse get = HttpUtil.doGet(almanacConf.getHost(), almanacConf.getUrl(), "GET", null, query);
            JSONObject object = JSON.parseObject(EntityUtils.toString(get.getEntity()));
            Map<String,String> o = (Map<String, String>) object.get("result");
            //持久缓存
            if (!CollectionUtils.isEmpty(o)){
                redisUtils.set(RedisKeyConf.ALMANAC_+date,o);
            }else {
                log.error("调用聚合数据错误，错误信息是："+object);
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @GetMapping("getWeatherWeChat")
    @ApiOperation(value = "根据经纬度查询天气情况", notes = "根据经纬度查询天气情况")
    public AjaxResult getWeatherWeChat(@ApiParam(name = "lat", value = "经度", required = true) String lat, @ApiParam(name = "lng", value = "纬度", required = true) String lng, @ApiParam(name = "type", value = "\t输入的坐标类型： 1：GPS设备获取的角度坐标; 2：GPS获取的米制坐标、sogou地图所用坐标; 3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标 4：3中列表地图坐标对应的米制坐标 5：百度地图采用的经纬度坐标 6：百度地图采用的米制坐标 7：mapbar地图坐标; 8：51地图坐标", required = true) String type) {

        WeatherVo toget = toget(lng, lat, type,2);
        if (toget==null){
            return AjaxResult.error("坐标转换失败");
        }
        return AjaxResult.success(toget);
    }


    private WeatherVo toget(String lng, String lat, String type,int code) {
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + weatherConf.getAppCode());
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("from", type);
        querys.put("lat", lat);
        querys.put("lng", lng);
        querys.put("need3HourForcast", "0");
        querys.put("needAlarm", "1");
        querys.put("needHourData", "0");
        querys.put("needIndex", "0");
        querys.put("needMoreDay", "0");
        try {
            HttpResponse httpResponse = HttpUtil.doGet(weatherConf.getHost(), weatherConf.getWeatherApiGpsUrl(), method, headers, querys);
            //获取response的body
            String s = EntityUtils.toString(httpResponse.getEntity());
            Map<String, Object> parse = (Map<String, Object>) JSONObject.parse(s);
            Map<String, Object> showapi_res_body = (Map<String, Object>) parse.get("showapi_res_body");

            Object now = showapi_res_body.get("now");
            String s1 = com.alibaba.fastjson.JSON.toJSONString(now);
            WeatherVo parse1 = com.alibaba.fastjson.JSON.parseObject(s1, WeatherVo.class);
            /*
             * 替换指定图片
             * */
            if (code==1){

            WeatherVo parse2 = replacePic(parse1);

                return parse2;
            }else {
                WeatherVo parse2 = replacePic2(parse1);

                return parse2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getWeather(String area,String areaCode,String date){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + weatherConf.getAppCode());
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("area", area);
        querys.put("areaCode", areaCode);
        querys.put("date", date);
        //开启区间为三个小时得温度查询 最小为3 其次为6
        querys.put("need3HourForcast", "1");
        try {
            HttpResponse response = HttpUtil.doGet(weatherConf.getHost(), weatherConf.getWeatherApiAreaUrl(), "get", headers, querys);
            String s = EntityUtils.toString(response.getEntity());
            Map<String,Object> o = (Map<String,Object>) JSON.parse(s);
            String resError = o.get("showapi_res_error")+"";
            if (StringUtils.isEmpty(resError)){
                //正常
                return o.get("showapi_res_body")+"";
            }
        } catch (Exception e) {
            log.error("调用天气接口失败：" );
            e.printStackTrace();
        }
        return null;
    }

    private WeatherVo replacePic(WeatherVo parse1) {
        String weather = parse1.getWeather();
        if (weather.contains("雨")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/%E9%9B%A8.png");
        } else if (weather.contains("冰雹")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/%E5%86%B0%E9%9B%B9.png");
        } else if (weather.contains("晴")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/%E6%99%B4%E5%A4%A9.png");
        } else if (weather.contains("多云")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/%E5%A4%9A%E4%BA%91.png?e=1601279187&token=-rrLxBpbfyQjssy5kU0GGZdYUFLR7p_T225rvz5I:XyurSeO6QuWT5IbwZYTwqqg-Peo=");
        } else if (weather.contains("雪")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/%E9%9B%AA.png");
        } else if (weather.contains("雷")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/%E9%9B%B7%E9%98%B5%E9%9B%A8.png");
        } else if (weather.contains("风")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/%E9%A3%8E.png");
        }
        return parse1;
    }

    private WeatherVo replacePic2(WeatherVo parse1) {
        String weather = parse1.getWeather();
        if (weather.contains("雨")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/fangyuan/20201116/98b238bad46b4dd1801299d708cd7729.png");
        } else if (weather.contains("冰雹")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/fangyuan/20201116/035f9fc4670b44b992e7e172504df53f.png");
        } else if (weather.contains("晴")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/fangyuan/20201116/f5f2d96440bb4e1ea00fcaa057ecd9d0.png");
        } else if (weather.contains("多云")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/fangyuan/20201116/6c01e705a9804e84b91ba4440d18bb0e.png");
        } else if (weather.contains("雪")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/fangyuan/20201116/d859091bb942481d887330cdc5f8db58.png");
        } else if (weather.contains("雷")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/fangyuan/20201116/8ba7aa45e1444485ab993d4554f3de04.png");
        } else if (weather.contains("风")) {
            parse1.setWeather_pic("http://cdn.fangyuancun.cn/fangyuan/20201116/d1cdd56d6d15452d9785fe0ec77d8a71.png");
        }
        return parse1;
    }

    public static void main(String[] args){
        System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        System.out.println((25 - 8) / 3 +1);
    }

}
