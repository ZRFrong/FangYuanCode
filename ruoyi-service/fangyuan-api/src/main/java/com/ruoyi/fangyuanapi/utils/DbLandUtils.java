package com.ruoyi.fangyuanapi.utils;

import javax.naming.Name;

public class DbLandUtils {
    private static final String NAME = "方圆村";
    public static String getLnadName(Integer num){
        switch (num){
            case 1:
                return NAME+"一号地";
            case 2:
                return NAME+"二号地";
            case 3:
                return NAME+"三号地";
            case 4:
                return NAME+"四号地";
            case 5:
                return NAME+"五号地";
            case 6:
                return NAME+"六号地";
        }
        return null;
    }
    public static void main(String[] args){
        String name = getLnadName(7);
        System.out.println(name);
        System.out.println(0/0);
    }
}
