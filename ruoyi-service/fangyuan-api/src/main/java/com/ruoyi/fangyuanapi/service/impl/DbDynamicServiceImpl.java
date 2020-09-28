/*
package com.ruoyi.fangyuanapi.service.impl;

import cn.hutool.db.Db;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.qiniu.http.Client;
import com.qiniu.util.Auth;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtil;
import com.ruoyi.fangyuanapi.conf.QiniuUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.fangyuanapi.mapper.DbDynamicAndEntryMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserAndDynamicMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserDynamicMapper;
import com.ruoyi.fangyuanapi.mapper.DbUserMapper;
import com.ruoyi.fangyuanapi.service.DbDynamicService;
import com.ruoyi.system.oss.CloudStorageService;
import com.ruoyi.system.oss.OSSFactory;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Slf4j
public class DbDynamicServiceImpl implements DbDynamicService {

    private String image = ".jpg.png.jpeg.bmp.webp.tif.gif";

    private String video = ".mp4.flv.mov.avi.wmv.ts.mpg";

    @Autowired
    private DbUserDynamicMapper dbUserDynamicMapper;

    @Autowired
    private DbDynamicAndEntryMapper dbDynamicAndEntryMapper;

    @Autowired
    private DbUserAndDynamicMapper  dbUserAndDynamicMapper;

    @Autowired
    private DbUserMapper dbUserMapper;

    private CloudStorageService build = OSSFactory.build();

    */
/**

     *//*

    @Override
    public String checkAndUploadFile(List<MultipartFile> file) {
        String url =null;
        List <String> res =  new ArrayList<>();
        try {
            for (MultipartFile multipartFile : file) {
                byte[] bytes = multipartFile.getBytes();
                String name = multipartFile.getOriginalFilename();//获取文件名
                String s = name.substring(name.lastIndexOf("."));//文件后缀名字
                String s1 = UUID.randomUUID().toString().replaceAll("-", "");
                url = build.upload(bytes, "fangyuan/" + s1 + s);//返回图片地址
                if (StringUtils.isEmpty(url)){
                    return null;
                }
                if (image.contains(s)){//调用图片审核
                    res.add(url);
                    String result = QiniuUtils.checkImage(url);
                    if (StringUtils.isNotEmpty(result)){
                        return result;
                    }
                }
                if (video.contains(s)){//调用视频审核
                    res.add(url);
                    String result = QiniuUtils.videoCheck(url);//返回任务id
                    if (StringUtils.isNotEmpty(result)){
                        //立即返回结果 开启线程去查询任务结果

                        return result;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传出错了");
        }
        JSON json = JSONUtil.parse(res);//json数组
        return json.toString();
    }

    @Transactional
    @Override
    public DbUserDynamic insterDynamic(String userId, String text, String url,Integer authority, List<Long> entryIds, String site) {
        DbUserDynamic dynamic = new DbUserDynamic();
        dynamic.setContent(text);
        dynamic.setResource(url);
        dynamic.setPermission(authority);
        dynamic.setOrientation(site);
        dynamic.setCreatedTime(new Date());
        int i = dbUserDynamicMapper.insertDbUserDynamic(dynamic);
        if (entryIds.size() >0){
            for (Long entryId : entryIds) {
                DbDynamicAndEntry dynamicAndEntry = new DbDynamicAndEntry();
                dynamicAndEntry.setEntryId(entryId);
                dynamicAndEntry.setDynamicId(dynamic.getId());
                dbDynamicAndEntryMapper.insertDbDynamicAndEntry(dynamicAndEntry);
            }
        }
        //todo
        //插入用户和动态和中间表，修改用户动态数量
        DbUserAndDynamic dbUserAndDynamic = new DbUserAndDynamic();
        dbUserAndDynamic.setUserId(Long.valueOf(userId));
        dbUserAndDynamic.setDynamicId(dynamic.getId());
        dbUserAndDynamicMapper.insertDbUserAndDynamic(dbUserAndDynamic);
        DbUser user = dbUserMapper.selectDbUserById(Long.valueOf(userId));
        user.setDynamicNum(user.getDynamicNum()+1);
        dbUserMapper.updateDbUser(user);
        return dynamic;
    }

    */
/**
     * 上传文件
     * @param multipartFile
     * @return url地址
     *//*

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String name = StringUtils.getUUIDFileName(multipartFile.getOriginalFilename());
        String upload = null;
        try {
            upload = build.upload(multipartFile.getBytes(), name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return upload;
    }

    */
/**
     * 检测图片是否合法
     * @param url
     * @return
     *//*

    @Override
    public Integer checkImagesLegal(String url) {
        String s = QiniuUtils.checkImage(url);
        switch (s){
            case "block" :
                log.warn(url+": 该内容存在违规建议删除！");
                return 1;
            case "review" :
                log.warn(url+": 该内容无法确认审核内容是否违规，建议您进行人工复核！");
                return 0;
            case "pass" :
                log.info(url+": 该内容系统确认审核内容正常，建议您忽略该文件。");
                return 200;
        }
        return null;
    }


    public static void main(String[] args){
//        String str = ".jpg.png.jpeg";
//        System.out.println(str.contains(".jpg"));
//        System.out.println("-------------------------------------------------");
//        String s = HttpUtil.sendGet("http://192.168.3.3:8001/sms/sendSms", "/15135006102/1/1");
//        System.out.println("结果"+s);

        DbComment comment = new DbComment();
        comment.setId(100l);
        System.out.println(comment.getId());
        Timer timer = new Timer();
        Integer integer = 0;
        TimerTask task = new TimerTask() {
            Integer integer = 0;
            @Override
            public void run() {
                int anInt = 0;
                anInt++;
                if (integer<5 ){
                    integer++;
                }
                System.out.println(anInt);
            }
        };
        timer.schedule(task,5000l,30000l);

    }
}
*/
