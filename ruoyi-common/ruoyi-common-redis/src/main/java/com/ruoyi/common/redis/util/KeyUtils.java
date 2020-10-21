package com.ruoyi.common.redis.util;

import com.ruoyi.common.redis.config.RedisKeyConf;

public class KeyUtils {

    public static final Integer key = 5;

    public static String getDynamicKey(Long id){
        Integer flag = Integer.valueOf(id%key+"");
        String result = null;
        switch (flag){
            case 0:
                result = RedisKeyConf.DYNAMIC_ARRAY_.name()+flag;
            case 1:
                result = RedisKeyConf.DYNAMIC_ARRAY_.name()+flag;
            case 2:
                result = RedisKeyConf.DYNAMIC_ARRAY_.name()+flag;
            case 3:
                result = RedisKeyConf.DYNAMIC_ARRAY_.name()+flag;
            case 4:
                result = RedisKeyConf.DYNAMIC_ARRAY_.name()+flag;
        }

        return result;
    }

    public static void  main(String[] args){
        System.out.println(getDynamicKey(1239L));
    }
}
