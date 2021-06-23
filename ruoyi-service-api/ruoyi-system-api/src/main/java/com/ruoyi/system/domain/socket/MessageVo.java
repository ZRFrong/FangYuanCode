package com.ruoyi.system.domain.socket;

import lombok.*;

import java.io.Serializable;

/**
 * @author Mr.Zhao
 * @version 2.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.system.domain.socket.VentilateMessageVo.java
 * @Description 设备在线 本地远程  开关状态 进度改变
 * @createTime 2021年06月17日 20:31:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
public class MessageVo implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    /**
     * 设备状态
     * */
    private Integer status;

    /**
     * 设备状态
     * */
    private Long functionId;

}
