package com.ruoyi.fangyuantcp.processingCode;

import cn.hutool.core.collection.CollectionUtil;
import com.ruoyi.system.domain.DbOperationVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 指令并行下发处理器
 * @Author zheng
 * @Date 2021/6/24 13:39
 * @Version 1.0
 */
@Component
@Log4j2
public class OrderConcurrentIssueHandler {


    /**
     * 批量处理下发
     * @param dbOperationVos 指令集合
     * @param callBack 返回结果
     * @param syncLock 同步锁
     */
    @Async("threadPoolTaskExecutor")
    public void batchHandle(List<DbOperationVo> dbOperationVos, Map<String, String> callBack,CountDownLatch syncLock){
        if(CollectionUtil.isNotEmpty(dbOperationVos)){
            dbOperationVos.forEach( v ->{
                singleHandle(v,callBack,null);
            });
            syncLock.countDown();
        }
    }

    /**
     * 单个处理下发
     * @param dbOperationVo 指令
     * @param callBack 返回结果
     * @param syncLock 同步锁
     */
    @Async("threadPoolTaskExecutor")
    public void singleHandle(DbOperationVo dbOperationVo, Map<String, String> callBack,CountDownLatch syncLock){
        try{
            if (dbOperationVo.getOperationTextType().equals("0") ) {
                SendCodeUtils.query(dbOperationVo);
            }
            else if (dbOperationVo.getOperationTextType().equals("9")){
                SendCodeUtils.queryText(dbOperationVo);
            }
            else {
                SendCodeUtils.queryType(dbOperationVo);
            }
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            log.error("OrderConcurrentIssueHandler.singleHandle 线程睡眠异常: {}",e);
        }catch (Exception e) {
            log.error("OrderConcurrentIssueHandler.singleHandle 指令执行异常: {}",e);
        } finally {
            if(syncLock != null)
                syncLock.countDown();
        }
    }
}
