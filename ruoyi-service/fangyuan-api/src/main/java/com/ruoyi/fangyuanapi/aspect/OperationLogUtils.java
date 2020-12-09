package com.ruoyi.fangyuanapi.aspect;

public class OperationLogUtils {
    private  static String[]  typs=new String[]{"1","2","3","4"};
    private static String[] handleNamecode = new String[]{"start", "start_stop", "down_stop", "down"};

    private static String[][] arrs = new String[][]{{"卷帘", "通风", "浇水", "补光"}, {"卷起", "卷起暂停", "放下", "放下暂停", "开始", "结束"}};

    public  static String toOperationText(String type, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        if (type.equals("1")||type.equals("2")){
            stringBuilder.append(gettxt1("1", handleName));
        }else {
            stringBuilder.append(gettxt2("2", handleName));
        }
        return stringBuilder.toString();
    }

    private static String gettxt2(String s, String handleName) {
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

    private static String gettxt1(String s, String handleName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < typs.length; i++) {
            if (typs[i].equals(s)) {
                for (int i1 = 0; i1 < handleNamecode.length; i1++) {
                    if (handleNamecode[i1].equals(handleName)) {
                        stringBuilder.append(arrs[0][i]);
                        stringBuilder.append("_"+arrs[1][i1]);
                        return stringBuilder.toString();
                    }
                }
            }
        }
        return stringBuilder.toString();
    }



}
