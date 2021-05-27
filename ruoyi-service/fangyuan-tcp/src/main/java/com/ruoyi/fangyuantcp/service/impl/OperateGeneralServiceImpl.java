package com.ruoyi.fangyuantcp.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.fangyuantcp.service.OperateGeneralService;
import com.ruoyi.fangyuantcp.processingCode.OpcodeTextConf;
import com.ruoyi.fangyuantcp.processingCode.SendCodeListUtils;
import com.ruoyi.fangyuantcp.processingCode.SendCodeUtils;
import com.ruoyi.system.domain.DbOperationVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OperateGeneralServiceImpl implements OperateGeneralService {


    private SendCodeUtils sendCodeUtils = new SendCodeUtils();

    /*
     * 循环执行请求
     * */
    @Override
    public R operationList(List<DbOperationVo> dbOperationVo) throws ExecutionException, InterruptedException {
//       根据心跳分组
        Map<String, List<DbOperationVo>> mps = dbOperationVo.stream().collect(Collectors.groupingBy(DbOperationVo::getHeartName));
//         多个map依次执行（多线程）
        R r = SendCodeListUtils.queryIoList(mps);

        return r;
    }


    /*
     * 操作设备
     * */
    @Override
    public int operation(DbOperationVo dbTcpClient) {
//        发送指令
        int query = SendCodeUtils.queryType(dbTcpClient);

        return query;
    }

}
