package com.ruoyi.fangyuanapi.service.impl;

import com.qiniu.http.Client;
import com.qiniu.util.Auth;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.fangyuanapi.service.DbDynamicService;
import com.ruoyi.system.oss.CloudStorageService;
import com.ruoyi.system.oss.OSSFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class DbDynamicServiceImpl implements DbDynamicService {

    private String image = ".jpg.png.jpeg.bmp.webp.tif.gif";

    private String video = ".mp4.flv.mov.avi.wmv.ts.mpg";

    private String iamgeUrl = "http://ai.qiniuapi.com/v3/image/censor";

    private String videoUrl = "http://ai.qiniuapi.com/v3/video/censor";

    @Override
    public String checkAndUploadFile(List<MultipartFile> file) {
        CloudStorageService build = OSSFactory.build();
        String url =null;
        try {
            for (MultipartFile multipartFile : file) {
                byte[] bytes = multipartFile.getBytes();
                String name = multipartFile.getOriginalFilename();//获取文件名
                String s = name.substring(name.lastIndexOf("."));//文件后缀名字
                String s1 = UUID.randomUUID().toString().replaceAll("-", "");
                String upload = build.upload(bytes, "fangyuan/" + s1 + s);//返回图片地址
                if (image.contains(s)){//调用图片审核
                    String body = "{ \"data\": { \"uri\": \""+upload+"\" }, \"params\": { \"scenes\": [ \"pulp\", \"terror\", \"politician\" ] } }";
                    HttpUtils.sendPost(iamgeUrl,null);
                    Auth auth = Auth.create("", "");
                    String token = auth.signQiniuAuthorization(image, "post", body.getBytes(), "application/json");
                    new Client();
                }

                if (video.contains(s)){//调用视频审核

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传出错了");
        }
        return url;
    }
    public static void main(String[] args){
//        String str = ".jpg.png.jpeg";
//        System.out.println(str.contains(".jpg"));
        System.out.println("-------------------------------------------------");
        String s = HttpUtils.sendGet("http://192.168.3.3:8001/sms/sendSms", "/15135006102/1/1");
        System.out.println("结果"+s);
    }
}
