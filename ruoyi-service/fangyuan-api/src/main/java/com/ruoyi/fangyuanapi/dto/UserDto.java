package com.ruoyi.fangyuanapi.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private Integer isAttention ;
    private String nickname;
    private String avatar;
}
