package com.ruoyi.fangyuanapi.service;

import com.ruoyi.fangyuanapi.domain.DbUserDynamic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface DbDynamicService {

    String checkAndUploadFile(List<MultipartFile> file);

    DbUserDynamic insterDynamic(String userId, String text, String url, Integer authority, List<Long> entryIds, String site);

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    String uploadFile(MultipartFile multipartFile);

    /**
     * 图片是否违规检测
     * @param url
     * @return
     */
    Integer checkImagesLegal(String url);
}
