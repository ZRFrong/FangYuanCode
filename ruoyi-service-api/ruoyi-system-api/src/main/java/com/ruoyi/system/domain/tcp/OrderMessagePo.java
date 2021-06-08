package com.ruoyi.system.domain.tcp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 指令消息实体类
 * @Author zheng
 * @Date 2021/5/19 16:56
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@ApiModel
public class OrderMessagePo implements Serializable {


    /**
     * 消息ID
     */
    //@NotNull(message = "消息ID不能为空")
    private Long messageId;

    /**
     * 消息类型 （0-设备指令，1-IM）
     */
    //@NotNull(message = "消息类型不能为空")
    private Byte messageType;


    /**
     * 消息来源（消息发送来源，如用户ID）
     */
    //@NotNull(message = "消息来源不能为空")
    private Long form;

    /**
     * 消息发送目标（消息发送目标，如用户ID或设备id、心跳名称、消息内容等）
     */
    //@NotNull(message = "发送目标不能为空")
    private DbOperationByteOrderVo to;

    /**
     * 消息下发渠道
     */
    private String distributeChannel;



}
