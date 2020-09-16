package com.ruoyi.fangyuanapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface DbDynamicService {
    String checkAndUploadFile(MultipartFile file);
}
