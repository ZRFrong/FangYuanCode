package com.ruoyi.fangyuantcp.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.DbOperationVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface OperateGeneralService {


    R operationList(List<DbOperationVo> dbOperationVo) throws ExecutionException, InterruptedException;

    int operation(DbOperationVo dbOperationVo);
}
