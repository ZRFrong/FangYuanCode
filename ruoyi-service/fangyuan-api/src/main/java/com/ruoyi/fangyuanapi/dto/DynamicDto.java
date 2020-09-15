package com.ruoyi.fangyuanapi.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 动态接口传输对象
 */
@Data
public class DynamicDto {

    /**
     * 动态用户的头像路径
     */
    private String avatar;
    /**
     * 动态用户昵称
     */
    private String nickname;
    /**
     * 动态的发布时间
     */
    private Date createdTime;
    /**
     * 动态资源 图片OR视频
     */
    private String resource;
    /**
     * 动态内容 比如：朋友圈的文字
     */
    private String content;
    /**
     * 动态的词条集合
     */
    private List<String> relSet;
     /**
     * 转发数量
     */
    private Integer forwardSum;
    /**
     * 评论数量
     */
    private Integer commentSum;
    /**
     * 点赞数量
     */
    private Integer liveGiveSum;

}
