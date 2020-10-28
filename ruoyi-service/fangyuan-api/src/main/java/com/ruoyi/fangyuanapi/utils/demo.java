package com.ruoyi.fangyuanapi.utils;

import java.io.*;


public class demo {

    public static void main(String[] args) {

        try {
            FileInputStream fileInputStream = new FileInputStream("D:\\3390.txt");
            InputStreamReader gdk = new InputStreamReader(fileInputStream, "gbk");

            StringBuilder stringBuilder = new StringBuilder();
            int read = 0;
//            判断是否读完
            while ((gdk.read()) != -1) {
            char[] b = new char[1024];
                read = gdk.read(b);
                stringBuilder.append(b);
            }
//            String[] split = new String(b, 0, read).replace("\n", "").replace("\r", "").split("");
            String[] split = stringBuilder.toString().replace("\n", "").replace("\r", "").split("");
            int num = 0;
            for (String s : split) {
                if (s.equals("山")) {
                    num++;
                }
            }
//            System.out.println(new String(b,0,read));
            System.out.println(String.format("文字： %s | 个数：%d", "山", num));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
