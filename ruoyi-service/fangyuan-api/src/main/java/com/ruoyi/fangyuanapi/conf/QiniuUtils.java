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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class QiniuUtils {

    @Autowired
    private JSONUtils jsonUtils;

    @Autowired
    private  QiniuConf qiniuConf;

    /**
     * 图片审核接口
     * @param imageUrl 图片url http
     * @return null：无违规 notNull：图片存在违规
     */
    public  String checkImage(String imageUrl){
        String url = qiniuConf.getImageUrl();
        String host = qiniuConf.getHost();
        String body = "{ \"data\": { \"uri\": \""+imageUrl+"\" }, \"params\": { \"scenes\": [ \"pulp\", \"terror\", \"politician\" , \"ads\"] } }";
        String contentType = qiniuConf.getContentType();
        String method = "POST";
        Auth auth = Auth.create(qiniuConf.getAccessKey(), qiniuConf.getSecretKey());
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
    public String videoCheck(String videoUrl){
        String method = "POST";
        String host = qiniuConf.getHost();
        String contentType = qiniuConf.getContentType();
        //String body = "{ \"data\": { \"uri\": \""+videoUrl+"\" }, \"params\": { \"scenes\": [ \"pulp\", \"terror\", \"politician\" ],\"cut_param\": [ \"pulp\", \"terror\", \"politician\" ]} }";
        String body = "{\"data\":{\"uri\":\""+videoUrl+"\"},\"params\":{\"scenes\":[\"pulp\",\"terror\",\"politician\"],\"cut_param\":{\"interval_msecs\":5000}}}";
        String url = qiniuConf.getVideoUrl();
        Auth auth = Auth.create(qiniuConf.getAccessKey(), qiniuConf.getSecretKey());
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

    /**
     * 回调接口
     * @param jobId
     * @return
     * @throws QiniuException
     */
    public String getCheckVideoResult(String jobId) throws QiniuException {
        String method ="GET";
        String host = qiniuConf.getHost();
        String url = qiniuConf.getCheckSelect()+jobId;
        Auth auth = Auth.create(qiniuConf.getAccessKey(), qiniuConf.getSecretKey());
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

}
