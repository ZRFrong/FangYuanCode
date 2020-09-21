package com.ruoyi.common.utils;


import java.util.Arrays;

/*
 *
 * 唯一邀请码   唯一id  转换为6位的子串
 * */
public class PassDemo {

    /**
     * 随机字符串
     */
    private static final char[] CHARS = new char[]{'F', 'L', 'G', 'W', '5', 'X', 'C', '3',
            '9', 'Z', 'M', '6', '7', 'Y', 'R', 'T', '2', 'H', 'S', '8', 'D', 'V', 'E', 'J', '4', 'K',
            'Q', 'P', 'U', 'A', 'N', 'B'};

    private final static int CHARS_LENGTH = 32;
    /**
     * 邀请码长度
     */
    private final static int CODE_LENGTH = 6;

    /**
     * 随机数据
     */
    private final static long SLAT = 1234561L;

    /**
     * PRIME1 与 CHARS 的长度 L互质，可保证 ( id * PRIME1) % L 在 [0,L)上均匀分布
     */
    private final static int PRIME1 = 3;

    /**
     * PRIME2 与 CODE_LENGTH 互质，可保证 ( index * PRIME2) % CODE_LENGTH  在 [0，CODE_LENGTH）上均匀分布
     */
    private final static int PRIME2 = 11;

    /**
     * 生成邀请码
     *
     * @param id 唯一的id主键
     * @return code
     */
    public static String gen(Long id) {
        //补位   将id*3+1234561L
        id = id * PRIME1 + SLAT;
        //将 id 转换成32进制的值
        long[] b = new long[CODE_LENGTH];
        //32进制数
        b[0] = id;
        for (int i = 0; i < CODE_LENGTH - 1; i++) {
//            后几位都等于前一位除以第几位
            b[i + 1] = b[i] / CHARS_LENGTH;
            //按位扩散
            b[i] = (b[i] + i * b[0]) % CHARS_LENGTH;
        }
//        最后一位校验
        b[5] = (b[0] + b[1] + b[2] + b[3] + b[4]) * PRIME1 % CHARS_LENGTH;

        //进行混淆
        long[] codeIndexArray = new long[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeIndexArray[i] = b[i * PRIME2 % CODE_LENGTH];
        }

        StringBuilder buffer = new StringBuilder();
//        把long值
        Arrays.stream(codeIndexArray).boxed().map(Long::intValue).map(t -> CHARS[t]).forEach(buffer::append);
//或者写法
//        for (Long l : codeIndexArray) {
//            int i = l.intValue();
//            char aChar = CHARS[i];
//            buffer.append(aChar);
//        }


        return buffer.toString();
    }

    /**
     * 将邀请码解密成原来的id
     *
     * @param code 邀请码
     * @return id
     */
    public static Long decode(String code) {
//        不是6位 out
        if (code.length() != CODE_LENGTH) {
            return null;
        }
        //将字符还原成对应数字
        long[] a = new long[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++) {
            char c = code.charAt(i);
            int index = findIndex(c);
            if (index == -1) {
                //异常字符串
                return null;
            }
            a[i * PRIME2 % CODE_LENGTH] = index;
        }

        long[] b = new long[CODE_LENGTH];
        for (int i = CODE_LENGTH - 2; i >= 0; i--) {
            b[i] = (a[i] - a[0] * i + CHARS_LENGTH * i) % CHARS_LENGTH;
        }

        long res = 0;
        for (int i = CODE_LENGTH - 2; i >= 0; i--) {
            res += b[i];
            res *= (i > 0 ? CHARS_LENGTH : 1);
        }
        return (res - SLAT) / PRIME1;
    }

    /**
     * 查找对应字符的index
     *
     * @param c 字符
     * @return index
     */
    private static int findIndex(char c) {
        for (int i = 0; i < CHARS_LENGTH; i++) {
            if (CHARS[i] == c) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        String gen = gen(14521l);
        System.out.println(decode(gen));
      /*  List<String> strings = Arrays.asList("1", "5", "6");
        strings.forEach(PassDemo::sout);
        StringBuilder buffer = new StringBuilder();
        System.out.println(strings);*/
//        Long decode = decode("5HHHA4");
//        System.out.println(decode);
        String s = gen(14521l);
        System.out.println(s);
    }
    public  static  void sout(String s){
        System.out.println(s);
    }
    public  static  String out(String s){
        if (s=="5"){
            return s;
        }else {
            return  null;
        }
    }

}
