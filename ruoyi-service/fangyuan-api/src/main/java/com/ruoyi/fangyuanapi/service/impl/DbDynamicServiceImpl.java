package com.ruoyi.fangyuanapi.service.impl;

import com.ruoyi.fangyuanapi.service.DbDynamicService;
import com.ruoyi.system.oss.CloudStorageService;
import com.ruoyi.system.oss.OSSFactory;
import org.apache.http.client.HttpClient;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class DbDynamicServiceImpl implements DbDynamicService {

    private String image = ".jpg.png.jpeg";

    private String video = ".mp4";

    @Override
    public String checkAndUploadFile(MultipartFile file) {
        CloudStorageService build = OSSFactory.build();
        String url =null;
        try {
            byte[] bytes = file.getBytes();
            String name = file.getName();
            String[] split = name.split(".");
            String s = split[split.length - 1];
            if (image.contains(s)||video.contains(s)){
                String s1 = UUID.randomUUID().toString().replaceAll("-", "");
                url = build.upload(bytes, "fangyuan/"+s1+s);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static void main(String[] args){
        String str = ".jpg.png.jpeg";
        System.out.println(str.contains(".jpg"));
    }
}
