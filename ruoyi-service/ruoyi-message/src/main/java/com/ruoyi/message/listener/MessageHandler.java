package com.ruoyi.message.listener;

import com.ruoyi.common.json.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.message.annotation.ChannelConstraint;
import com.ruoyi.system.domain.tcp.OrderMessagePo;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 消息下发处理器入口
 * @Author zheng
 * @Date 2021/5/14 11:21
 * @Version 1.0
 */
@Component
@Log4j2
public class MessageHandler implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final Map<String, MessageService> processorMap = new HashMap<>(2);

    /**
     * 消息处理下发处理
     * @param message 指令消息内容 , 默认最后一个元素为下发区分标识 mq或netty
     */
    @SneakyThrows
    void doProcessor(String message) {
        log.info("MessageHandle.doProcessor.message:[{}]", message);
        if (StringUtils.isNotEmpty(message)) {
            OrderMessagePo messagePo = JSON.unmarshal(message, OrderMessagePo.class);
            MessageService messageService = processorMap.get(messagePo.getDistributeChannel());
            if(messageService == null) throw new RuntimeException("消息无对应处理器");
            messageService.doMessage(messagePo);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * bean加载后初始化处理
     ** @throws Exception
     */
    @Override
    @SneakyThrows
    public void afterPropertiesSet() throws Exception {
        // 消息处理器装载
        messageHandlerAssembly();
    }

    // 消息处理器装配
    private void messageHandlerAssembly(){
        Map<String, MessageService> beansOfType = applicationContext.getBeansOfType(MessageService.class);
        beansOfType.values().forEach(v -> {
            ChannelConstraint annotation = v.getClass().getAnnotation(ChannelConstraint.class);
            String distributeChannel = annotation.distributeChannel();
            if(!Objects.isNull(processorMap.put(distributeChannel, v))){
                log.warn("消息处理器[{}]重复装配!",distributeChannel);
            }
        });
        log.info("消息下发处理器装载完成 processorMap:[{}]", processorMap);
    }
}
