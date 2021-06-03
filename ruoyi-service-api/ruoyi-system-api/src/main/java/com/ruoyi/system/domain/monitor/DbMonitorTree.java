package com.ruoyi.system.domain.monitor;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description: 录像机-摄像头树形
 * @Author zheng
 * @Date 2021/6/2 10:05
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class DbMonitorTree {


    /**
     * 父ID （录像机ID）
     */
    private Long parentId;

    /**
     * 父名称 （录像机设备名称）
     */
    private String parentName;

    /**
     * 是否有下级列表
     */
    private boolean hasChild;

    /**
     * 子集列表 （通道列表）
     */
    private List<DbMonitorTree> child;


}
