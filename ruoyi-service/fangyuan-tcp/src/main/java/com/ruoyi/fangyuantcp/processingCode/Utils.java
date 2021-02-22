package com.ruoyi.fangyuantcp.processingCode;

public class Utils {
    public static void main(String[] args) {
        String text="HANDLE:dapeng15_01_0302";
        System.out.println(text.split("_")[2].substring(0,2));
    }
}
