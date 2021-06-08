package com.ruoyi.message.annotation;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChannelConstraint {

    /**
     * 消息下发渠道标识
     * @return 标识
     */
    String distributeChannel() ;
}
