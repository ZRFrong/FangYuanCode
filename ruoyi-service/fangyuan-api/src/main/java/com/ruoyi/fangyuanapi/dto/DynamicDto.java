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
     * dangqi
     */
    private Integer likeFlag;
    /**
     * 是否转发
     */
    private Integer isForward;
    /**
     * 转发的用户id
     */
    private Long forwardUserId;
    /**
     * 转发的用户昵称
     */
    private String forwardNickName;
    /**
     * 转发时的评论
     */
    private String forwardComment;
    /**
     * 转发的用户头像
     */
    private String forwaedFvatar;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 动态id
     */
    private Long dynamicId;
    /**
     * 动态用户的头像路径
     */
    private String avatar;
    /**
     * 定位地址
     */
    private String orientation;
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
