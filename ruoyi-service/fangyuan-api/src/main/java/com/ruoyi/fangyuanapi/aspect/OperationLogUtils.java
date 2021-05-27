package com.ruoyi.fangyuanapi.aspect;

import com.ruoyi.fangyuanapi.conf.OperationConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OperationLogUtils {

    @Autowired
    private OperationConf operationConf;
    private   String[]  typs = null;
    private  String[] handleNamecode = null;
    private  String[][] arrs = null;

//    private  static String[]  typs=new String[]{"1","2","3","4"};
//    private static String[] handleNamecode = new String[]{"start", "start_stop",  "down","down_stop"};
//    private static String[][] arrs = new String[][]{{"卷帘", "通风", "补光", "浇水"}, {"卷起", "暂停", "放下", "暂停", "开始", "结束"}};

    public   String toOperationText(String type, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        init();
        if (type.equals("1")||type.equals("2")){
            stringBuilder.append(gettxt1("1", handleName));
        }else {
            stringBuilder.append(gettxt2("2", handleName));
        }
        return stringBuilder.toString();
    }

    private void init(){
        typs = operationConf.getTyps();
        handleNamecode = operationConf.getHandleNamecode();
        arrs = operationConf.getArrs();
    }

    private  String gettxt2(String s, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < typs.length; i++) {
            if (typs[i].equals(s)) {
                for (int i1 = 0; i1 < handleNamecode.length; i1++) {
                    if (handleNamecode[i1].equals(handleName)) {
                        if (i1 == 0) {
                            stringBuilder.append(arrs[i][4]);
                        } else if (i1 == 4) {
                            stringBuilder.append(arrs[i][5]);
                        }
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    private  String gettxt1(String s, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < typs.length; i++) {
            if (typs[i].equals(s)) {
                for (int i1 = 0; i1 < handleNamecode.length; i1++) {
                    if (handleNamecode[i1].equals(handleName)) {
                        stringBuilder.append(arrs[1][i1]);
                        return stringBuilder.toString();
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args){

    }


}
