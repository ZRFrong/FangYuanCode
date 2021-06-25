package com.ruoyi.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author MR.zhao
 * @version 1.0.0
 * @ClassName ruoyi-cloud.com.ruoyi.common.enums.PatternEnum.java
 * @Description TODO
 * @createTime 2021年06月16日 16:27:00
 */

public enum PatternEnum {
    /**
     * 手动自动返回 check
     * */
    CODE_0302("^\\d{2}0302[A-Za-z0-9]*$"),
    /**
     * 传感器状态返回 check
     * */
    CODE_030C("^\\d{2}030C[A-Za-z0-9]*$"),
    /**
     * 操作响应 check
     * */
    CODE_0105("^0105[A-Za-z0-9]*$"),
    /**
     * 通风自动手动状态 check
     * */
    CODE_0101("^\\d{2}0101[A-Za-z0-9]*$"),
    /**
     * 自动通风开关温度返回 check
     * */
    CODE_0304("^\\d{2}0304[A-Za-z0-9]*$"),
    /**
     * 写自动通风状态返回 check
     * */
    CODE_0106("^0106[A-Za-z0-9]*$"),
    /**
     * 主动上发状态返回 check
     * */
    CODE_C810("^C810[A-Za-z0-9]*$"),
    /**
     * 卷膜一号百分比操作指令反馈 check
     * */
    CODE_10003C("^0110003C[A-Za-z0-9]*$"),
    /**
     * 卷膜二号百分比操作指令反馈 check
     * */
    CODE_10003E("^0110003E[A-Za-z0-9]*$"),
    /**
     * 补光定时操作指令反馈 check
     * */
    CODE_1000E8("^011000E8[A-Za-z0-9]*$");

    private String Pattern;

    PatternEnum(String pattern) {
        Pattern = pattern;
    }

    public String getPattern() {

        return Pattern;
    }

    public void setPattern(String pattern) {
        Pattern = pattern;
    }
}
