package com.ruoyi.fangyuanapi.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@RefreshScope
public class OperationConf {

    @Value("#{${com.fangyuan.operation.typs}}")
    private Map<String,String> typs;

    @Value("#{'${com.fangyuan.operation.handleNamecode}'.split(',')}")
    private List<String> handleNamecode;

    @Value("#{'${com.fangyuan.operation.arrs}'.split(',')}")
    private List<String> arrs;


    public Map<String,String> getTypsMap() {
        return typs;
    }


    public String[] getTyps() {
        return typs.keySet().toArray(new String[typs.size()]);
    }

    public void setTyps(Map<String, String> typs) {
        this.typs = typs;
    }

    public String[] getHandleNamecode() {
        return handleNamecode.toArray(new String[handleNamecode.size()]);
    }

    public void setHandleNamecode(List<String> handleNamecode) {
        this.handleNamecode = handleNamecode;
    }

    public String[][] getArrs() {
        String[] values = typs.values().toArray(new String[typs.size()]);
        List<String[]> list =new ArrayList<>();
        list.add(values);
        String[] array = arrs.toArray(new String[arrs.size()]);
        list.add(array);
        String[][] objects = list.toArray(new String[1][1]);
        System.out.println(Arrays.toString(objects));
        return objects;
    }

    public void setArrs(List<String> arrs) {
        this.arrs = arrs;
    }
}
