package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sms.ResultEnum;
import com.ruoyi.system.feign.RemoteDeptService;
import com.ruoyi.system.feign.SendSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wxUser")
public class UserController {


    @Autowired
    private RemoteDeptService remoteDeptService;




    @PostMapping("wxRegister}")

    public R wxLogin(Map<String,String> requst){
        if (StringUtils.isEmpty(requst)){
            return R.error(ResultEnum.PARAMETERS_ERROR.getCode(),ResultEnum.PARAMETERS_ERROR.getMessage());
        }

        return null;
    }



    @PostMapping("wxAward")
    public R wxAward(String code,String  image,String nickname,int userFrom){
        return null;
    }

}
