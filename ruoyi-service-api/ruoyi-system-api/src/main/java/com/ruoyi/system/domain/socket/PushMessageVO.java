package com.ruoyi.system.domain.socket;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

/**
 * @Description: 服务端推送消息实体
 * @Author zheng
 * @Date 2021/6/9 15:48
 * @Version 2.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class PushMessageVO implements Serializable {

    private static final long serialVersionUID = -5809782578272945999L;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息指向-用户ID ,多个用逗号分割
     */
    private String messageTarget;

    /**
     * 消息内容
     */
    private JSONObject messageInfo;
}
