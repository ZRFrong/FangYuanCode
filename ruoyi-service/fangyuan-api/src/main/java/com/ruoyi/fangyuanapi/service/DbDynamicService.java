package com.ruoyi.fangyuanapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DbDynamicService {
    String checkAndUploadFile(List<MultipartFile> file);
}
