package com.ruoyi.fangyuanapi.conf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.ruoyi.common.json.JSONUtils;
import com.ruoyi.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class QiniuUtils {

    @Autowired
    private JSONUtils jsonUtils;

    /**
     * 图片审核接口
     * @param imageUrl 图片url http
     * @return null：无违规 notNull：图片存在违规
     */
    public static String checkImage(String imageUrl){
        String url = "http://ai.qiniuapi.com/v3/image/censor";
        String host = "ai.qiniuapi.com";
        String body = "{ \"data\": { \"uri\": \""+videoUrL+"\" }, \"params\": { \"scenes\": [ \"pulp\", \"terror\", \"politician\" ] } }";
        String contentType = "application/json";
        String method = "POST";

        Auth auth = Auth.create("LCPV8dFa01TX35O8AgOvK1nZKepkTDGsSCtoJOCf", "k6RXwRuTPaRZp1Fj_nS4FPTMit39kvtp2K2WYNH0");
        String qiniuToken = "Qiniu " + auth.signQiniuAuthorization(url, method, body.getBytes(), contentType);
        System.out.println("token=== " +qiniuToken);
        log.info("url={},body={},qiniuToken={}",url,body,qiniuToken);
        //头部部分
        StringMap header = new StringMap();
        header.put("Host",host);
        header.put("Authorization",qiniuToken);
        header.put("Content-Type", contentType);
        Configuration c = new Configuration(Region.huadong());
        Client client = new Client(c);
        try {
            Response response = client.post(url, body.getBytes(), header, contentType);
            log.info("response result={}",response.bodyString());
            JSONObject checkResult = JSON.parseObject(response.bodyString());
            String result = checkResult.getString("result");
            JSONUtils<String, String> utils = new JSONUtils<>();
            Map<String, String> map = utils.stringToMap(result);
            String s = map.get("suggestion");
            log.info("返回结果： "+result);
            if (!"pass".equals(s)){
                log.warn(imageUrl+": 该图片存在违规行为！");
                return s;
            }
        } catch (QiniuException e) {
            log.error("请求出错！");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 视频审核
     * @param videoUrl 视频地址
     * @return jod_id 回调单号
     */
    public static String videoCheck(String videoUrl){
        String method = "POST";
        String host = "ai.qiniuapi.com";
        String contentType = "application/json";
        String body = "{ \"data\": { \"uri\": \""+videoUrl+"\" }, \"params\": { \"scenes\": [ \"pulp\", \"terror\", \"politician\" ] } }";
        String url = " http://ai.qiniuapi.com/v3/video/censor";
        Auth auth = Auth.create("LCPV8dFa01TX35O8AgOvK1nZKepkTDGsSCtoJOCf", "k6RXwRuTPaRZp1Fj_nS4FPTMit39kvtp2K2WYNH0");
        //身份识别

        String qiniuToken = "Qiniu " + auth.signQiniuAuthorization(url,method,body.getBytes(),contentType);
        //String qiniuToken = "Qiniu LCPV8dFa01TX35O8AgOvK1nZKepkTDGsSCtoJOCf:g4VlrsBC6w_y4PbCoOpI3m1CEmc=";
        log.info("url={},body={},qiniuToken={}",url,body,qiniuToken);
        //头部部分
        StringMap header = new StringMap();
        header.put("Host",host);
        header.put("Authorization",qiniuToken);
        header.put("Content-Type", contentType);
        Configuration configuration = new Configuration(Region.region0());
        Client client = new Client();
        try {
            Response response = client.post(url, body.getBytes(), header, contentType);
            log.info("response result={}",response.bodyString());
            JSONObject checkResult = JSON.parseObject(response.bodyString());
            String jobId = checkResult.getString("job");
            log.info("返回结果： "+jobId);
            return jobId;
        } catch (QiniuException e) {
            log.error("请求出错！");
            e.printStackTrace();
        }
        return null;
    }

    private static String imaegsUrl = "http://cdn.peasetech.cn/fangyuan/20200915/0dafb09d12ad4805a959ae71b895bd8e.jpg";
    private static String videoUrL = "http://cdn.peasetech.cn/fangyuan4d23b6ae70c7f3d9b6af1996b7add292.mp4";
    public static void main(String[] args){
       // checkImage(imaegsUrl);
        videoCheck(videoUrL);
    }
}
