package com.ruoyi.common.enums;

/**
 * @Description: 消息类别
 * @Author zheng
 * @Date 2021/5/19 17:25
 * @Version 1.0
 */
public enum MessageTypeEnum {

    Equipment((byte) 0 , "设备"),
    IM((byte) 1 , "IM"),
    ;

    /**
     * 标识码
     */
    private Byte code;
    /**
     * 对应文本内容
     */
    private String text;

    MessageTypeEnum(Byte code,String text){
        this.code = code;
        this.text = text;
    }

    public Byte getCode(){
        return this.code;
    }

    public String getText(){
        return this.text;
    }
}
