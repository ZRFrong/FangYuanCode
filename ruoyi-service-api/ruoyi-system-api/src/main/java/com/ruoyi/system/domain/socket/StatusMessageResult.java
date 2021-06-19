package com.ruoyi.system.domain.socket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mr.Zhao
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.domain.socket.StatusMessageResult.java
 * @Description 设备交互消息传递实体类
 * @createTime 2021年06月17日 18:47:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StatusMessageResult implements Serializable {

    private static final long serialVersionUID = -5809788578272943999L;


    private List<Long> landId;

    private Integer type;

    private Object equipmentMsg;



}
