package com.ruoyi.fangyuantcp.processingCode;

import cn.hutool.core.thread.ThreadUtil;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.concurrent.ExecutorService;

/**
 * @Description: sd
 * @Author zheng
 * @Date 2021/2/26 17:33
 * @Version 1.0
 */
public class demo {

    private static ExecutorService executorService = null;

    public static void main(String[] args) throws InterruptedException {
        executorService = ThreadUtil.newExecutor();
        for (int i = 0; i < 3; i++) {
            executorService.execute(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Thread.yield();
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getId() + "" + new Date());
                }
            });
            Thread.sleep(1000);
        }

    }

}
