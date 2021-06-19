package com.ruoyi.fangyuanapi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @NAME: UserLogDto
 * @USER: chensy
 * @DATE: 2021/6/19
 * @PROJECT_NAME: ruoyi-cloud
 * @Description 日志记录类
 **/
@Data
@Accessors(chain = true)
public class UserLogDto {
    private Long id;
    private Long userId;
    private String nickName;
    private String active;
    private String operateTime;


}
