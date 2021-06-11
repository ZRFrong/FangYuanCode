package com.ruoyi.fangyuantcp.tcp;


import com.ruoyi.fangyuantcp.utils.Crc16Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.List;
/*
*
* 代码解析类
* */
public class MyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        //创建字节数组,buffer.readableBytes可读字节长度
            byte[] b = new byte[buffer.readableBytes()];
        //复制内容到字节数组b
            buffer.readBytes(b);
        //字节数组转字符串
            String str = new String(b,"UTF-8");
        if (str.contains("dapeng")){
            System.out.println("大棚心跳为："+str);
            out.add(str);
        }else {
            String s = bytesToHexString(b);
            String code = s.substring(s.length() - 2, s.length());
            s.substring(0,s.length()-2);
            //System.out.println(str);
            out.add(s);
        }
    }

    public  static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    public static String toHexString1(byte[] b) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }

    public static String toHexString1(byte b) {
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1) {
            return "0" + s;
        } else {
            return s;
        }
    }


    public static void main(String[] args){
        String  s ="C81000000014282BC1003C000100D400CA00000000002202BB001E01F40032138801F401F401F401F400E00003000060D2";
        String code = s.substring(s.length() - 2, s.length());
        String str = s.substring(0, s.length() - 2);
        String s2 = "C81000000014282BC1003C000100D400CA00000000002202BB001E01F40032138801F401F401F401F400E000030000";
        byte[] crc16 = Crc16Util.getCrc16(s2.getBytes());
        String s1 = new String(crc16);
        System.out.println(s1);
        System.out.println(Crc16Util.byteTo16String(crc16).toUpperCase());
    }


}
