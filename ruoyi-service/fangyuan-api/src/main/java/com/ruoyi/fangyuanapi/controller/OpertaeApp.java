package com.ruoyi.fangyuanapi.controller;

import com.ruoyi.fangyuanapi.service.*;
import com.ruoyi.system.domain.DbEquipment;
import com.ruoyi.system.domain.DbIntelligenceControlRelationship;
import com.ruoyi.system.domain.DbProductBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description: demo
 * @Author zheng
 * @Date 2021/4/8 17:05
 * @Version 1.0
 */
@RestController
@RequestMapping("text")
public class OpertaeApp {

    @Autowired
    private IDbEquipmentService dbEquipmentService;
    //    private IDbEquipmentService dbEquipmentService = SpringUtils.getBean(IDbEquipmentService.class);
    @Autowired
    private IDbProductBatchService dbForwardService;
    @Autowired
    private IDbEquipmentComponentService dbEquipmentComponentService;
    @Autowired
    private IDbIntelligenceControlRelationshipService dbIntelligenceControlRelationshipService;

    @GetMapping("generate")
    public void generate() {


        String arr = "157,160,161,169,170,171,150,177,180,183,184,185,186,187";
        ArrayList<DbEquipment> objects = new ArrayList<>();
        Arrays.asList(arr.split(",")).stream().forEach(ite -> {
            DbIntelligenceControlRelationship intelligenceControlRelationship = new DbIntelligenceControlRelationship();
            DbProductBatch dbProductBatch = dbForwardService.selectDbProductBatchById(1l);
            intelligenceControlRelationship.setDbEquipmentComponentIds(dbProductBatch.getEquipmentComponentIds());
            DbEquipment dbEquipment = dbEquipmentService.selectDbEquipmentById(Long.valueOf(ite));
            intelligenceControlRelationship.setDbEquipmentIds(dbEquipment.getEquipmentId());
            intelligenceControlRelationship.setDbEquipmentTempId(dbEquipment.getEquipmentTemplateId());
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            Arrays.asList(dbProductBatch.getEquipmentComponentIds().split(",")).stream().forEach(ite2 -> {
                stringBuilder.append(dbEquipmentComponentService.selectDbEquipmentComponentById(Long.valueOf(ite2)).getFunctionLogo());
                stringBuilder2.append(dbEquipmentComponentService.selectDbEquipmentComponentById(Long.valueOf(ite2)).getId());
            });
            intelligenceControlRelationship.setDbFunctionDisplay(stringBuilder.toString());
            intelligenceControlRelationship.setDbEquipmentComponentIds(stringBuilder2.toString());
            intelligenceControlRelationship.setDbProductBathId(1l);
            dbIntelligenceControlRelationshipService.insertDbIntelligenceControlRelationship(intelligenceControlRelationship);

        });


    }
}
