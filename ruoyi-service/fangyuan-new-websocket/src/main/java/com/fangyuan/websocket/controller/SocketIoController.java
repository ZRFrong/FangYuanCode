package com.fangyuan.websocket.controller;

import com.fangyuan.websocket.config.socket.service.SocketIoService;
import com.ruoyi.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: socket操作控制层
 * @Author zheng
 * @Date 2021/6/21 13:51
 * @Version 1.0
 */
@RestController
public class SocketIoController {


    @Autowired
    private SocketIoService socketIoService;

    /**
     * 通过用户Id关闭连接
     * @param userId 用户ID
     * @return 操作结果
     */
    /*@GetMapping("closeByUserId/{userId}")
    public R closeByUserId(@PathVariable("userId") String userId){
        socketIoService.closeConnectByUserId(userId);
        return R.ok();
    }*/

    /**
     * 通过sessionId关闭连接
     * @param sessionId 连接ID
     * @return 操作结果
     */
    @GetMapping("closeBySessionId/{sessionId}")
    public R closeBySessionId(@PathVariable("sessionId") String sessionId){
        socketIoService.closeConnectBySessionId(sessionId);
        return R.ok();
    }
}
