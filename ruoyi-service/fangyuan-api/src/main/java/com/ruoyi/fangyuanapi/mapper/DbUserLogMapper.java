package com.ruoyi.fangyuanapi.mapper;

import com.ruoyi.fangyuanapi.dto.UserLogDto;

import java.util.List;

public interface DbUserLogMapper {

    public List<UserLogDto> selectDbUserLogList();

    public int insertDbUserLog(UserLogDto userLogDto);
}
