package com.ruoyi.fangyuanapi.dto;

import com.ruoyi.common.utils.IdWorker;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SkuDto {
    private Long id;
    private Long cid;
    private Integer num;

    public static void main(String[] args){
        IdWorker worker = new IdWorker();
        Date date = new Date();
        long time = date.getTime();
        System.out.println(time);

    }
}
