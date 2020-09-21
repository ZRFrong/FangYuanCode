package com.ruoyi.common.utils.upload;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.sms.ResultEnum;

public class CheckResultUtils {

    /**
     * 根据对应的审核结果，返回对应的审核信息
     * @param result
     * @return r
     */
    public static R getResult(Integer result) {
        switch (result){
            case 0:
                R.error(ResultEnum.RESULT_REVIEW.getCode(),ResultEnum.RESULT_REVIEW.getMessage());
            case 1:
                R.error(ResultEnum.RESULT_BLOCK.getCode(),ResultEnum.RESULT_BLOCK.getMessage());
        }
        return null;
    }
}
