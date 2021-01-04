/*
package com.ruoyi.system.controller;

import com.ruoyi.common.core.domain.R;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("apk")
public class APKUploadController {

    @PostMapping("apkUpload")
    public R apkUpload(MultipartFile file,String path){
        String fileName = file.getOriginalFilename().substring(file.getOriginalFilename().length() - 4, file.getOriginalFilename().length());
        String apkVersion = "";
        try {
            file.transferTo(new File(path,fileName));
//            ApkFile apkFile = new ApkFile(path+fileName);
//            ApkMeta apkMeta = apkFile.getApkMeta();
//            apkVersion = apkMeta.getVersionCode()+"";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.data(apkVersion);
    }

    @RequestMapping("upload")
    public R upload(MultipartFile file){
        String orgFileName = file.getOriginalFilename();
        String[] split = orgFileName.split(".");
        String s = orgFileName.substring(orgFileName.length() - 3, +orgFileName.length());
        String fileName = System.currentTimeMillis() + "." +s;
        String path ="/opt/apkupload";
        try {
            file.transferTo(new File(path, fileName));
            ApkFile apkFile = new ApkFile(path+fileName);
            ApkMeta apkMeta = apkFile.getApkMeta();

            String label = apkMeta.getLabel();
            String version = apkMeta.getMinSdkVersion();
            System.out.println("版本号     :"+version);
            System.out.println("版本号     :"+apkMeta.getMaxSdkVersion());
            System.out.println("版本号     :"+apkMeta.getVersionCode());
            System.out.println("应用名称   :" + apkMeta.getLabel());
            System.out.println("包名       :" + apkMeta.getPackageName());
            System.out.println("版本号     :" + apkMeta.getVersionName());
            System.out.println("图标       :" + apkMeta.getIcon());

            File file1 = new File(path + fileName);
            boolean b = file1.delete();
            boolean b1 = FileUtils.deleteQuietly(file1);
            System.out.println("删除结果"+b +" ----"+b1);
            return R.data(info);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
*/
