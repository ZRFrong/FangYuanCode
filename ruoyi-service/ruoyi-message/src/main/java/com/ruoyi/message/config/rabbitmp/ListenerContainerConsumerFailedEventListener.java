package com.ruoyi.message.config.rabbitmp;

import cn.hutool.core.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.listener.ListenerContainerConsumerFailedEvent;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Description: mq容器断开重连监听
 * @Author zheng
 * @Date 2021/5/25 11:47
 * @Version 1.0
 */
@Log4j2
@Component
public class ListenerContainerConsumerFailedEventListener implements ApplicationListener<ListenerContainerConsumerFailedEvent> {
    @Override
    public void onApplicationEvent(ListenerContainerConsumerFailedEvent event) {
        if (event.isFatal()) {
            log.error(String.format("Stopping container from aborted consumer. Reason::%s.",
                    event.getReason()), event.getThrowable());
            SimpleMessageListenerContainer container = (SimpleMessageListenerContainer) event.getSource();
            String queueNames = Arrays.toString(container.getQueueNames());
            // 重启
            try {
                restart(container);
                log.info("重启队列{}的监听成功！", queueNames);
            } catch (Exception e) {
                log.error(String.format("重启队列%s的监听失败！", queueNames), e);
            }
        }
    }

    private void restart(SimpleMessageListenerContainer container) {
        // 暂停30s
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        Assert.state(!container.isRunning(), String.format("监听容器%s正在运行！", container));
        container.start();
    }

}
