package com.ruoyi.system.domain.socket;

import cn.hutool.json.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 服务端推送消息实体
 * @Author zheng
 * @Date 2021/6/9 15:48
 * @Version 2.0
 */
@Data
@Accessors(chain = true)
public class PushMessageVO {

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息指向 type=0：多个用户ID；type=1：用户ID/Token
     */
    private String messageTarget;

    /**
     * 消息内容
     */
    private JSONObject messageInfo;
}
