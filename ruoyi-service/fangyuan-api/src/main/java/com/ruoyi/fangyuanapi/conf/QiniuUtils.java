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
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
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
        String body = "{ \"data\": { \"uri\": \""+imageUrl+"\" }, \"params\": { \"scenes\": [ \"pulp\", \"terror\", \"politician\" , \"ads\"] } }";
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
        Configuration c = new Configuration(Region.huabei());
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
            return s;
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
        //String body = "{ \"data\": { \"uri\": \""+videoUrl+"\" }, \"params\": { \"scenes\": [ \"pulp\", \"terror\", \"politician\" ],\"cut_param\": [ \"pulp\", \"terror\", \"politician\" ]} }";
        String body = "{\"data\":{\"uri\":\""+videoUrl+"\"},\"params\":{\"scenes\":[\"pulp\",\"terror\",\"politician\"],\"cut_param\":{\"interval_msecs\":5000}}}";
        String url = "http://ai.qiniuapi.com/v3/video/censor";
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
        Configuration configuration = new Configuration(Region.huabei());
        Client client = new Client();
        try {
            Response response = client.post(url, body.getBytes(), header, contentType);
            log.info("response result={}",response.bodyString());
            JSONObject checkResult = JSON.parseObject(response.bodyString());
            System.out.println(checkResult);
            String jobId = checkResult.getString("job");
            log.info("返回结果： "+jobId);
            return jobId;
        } catch (QiniuException e) {
            log.error("请求出错！");
            e.printStackTrace();
        }
        return null;
    }

    public static String getCheckVideoResult(String jobId) throws QiniuException {
        String method ="GET ";
        String host = "ai.qiniuapi.com";
        String url = "http://ai.qiniuapi.com/v3/jobs/video/"+jobId;
        Auth auth = Auth.create("LCPV8dFa01TX35O8AgOvK1nZKepkTDGsSCtoJOCf", "k6RXwRuTPaRZp1Fj_nS4FPTMit39kvtp2K2WYNH0");
        //身份识别
        String qiniuToken = "Qiniu "+ auth.signQiniuAuthorization(url,null,null,"");
        System.out.println("token=== "+qiniuToken);
        StringMap header = new StringMap();
        header.put("Host",host);
        header.put("Authorization",qiniuToken);
        Client client = new Client();
        Response response = client.get(url, header);
        StringMap map = response.jsonToMap();
        Object o = map.get("result");
        if (o !=null){
            Map<String,Map<String,String>> m = (Map<String, Map<String, String>>) o;
            return m.get("result").get("suggestion");
        }
        return null;
    }

    private static String imaegsUrl = "http://cdn.peasetech.cn/fangyuan/20200915/0dafb09d12ad4805a959ae71b895bd8e.jpg";
    private static String videoUrL = "http://cdn.peasetech.cn/fangyuan4d23b6ae70c7f3d9b6af1996b7add292.mp4";
    public static void main(String[] args) throws IOException {
        //checkImage(imaegsUrl);
        String s = videoCheck(videoUrL);
        for (int i = 0; i < 10; i++) {

            String s1 = getCheckVideoResult(s);
            System.out.println(s1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //String d = "{\"data\":{\"uri\":\""+videoUrL+"\"},\"params\":{\"scenes\":[\"pulp\",\"terror\",\"politician\"],\"cut_param\":{\"interval_msecs\":5000}}}";



    }
}
