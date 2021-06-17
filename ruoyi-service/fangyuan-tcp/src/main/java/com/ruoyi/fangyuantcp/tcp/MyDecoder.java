package com.ruoyi.fangyuantcp.tcp;
import com.ruoyi.common.enums.PatternEnum;
import com.ruoyi.common.utils.HeartbeatUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.fangyuantcp.processingCode.ReceiveUtils;
import com.ruoyi.fangyuantcp.service.IDbTcpClientService;
import com.ruoyi.fangyuantcp.utils.Crc16Util;
import com.ruoyi.system.domain.DbTcpClient;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
*
* 代码解析类
* */
@Slf4j
public class MyDecoder extends ByteToMessageDecoder {

    private IDbTcpClientService tcpClientService = SpringUtils.getBean(IDbTcpClientService.class);
    
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
            System.out.println(s);
            if (Crc16Util.checkCrc(s)){
                out.add(s);
            }else {
                log.warn("上发错误数据 "+ReceiveUtils.getname(ctx)+"："+s);
            }
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
        String s2 = "C8,10,00,00,00,14,28,2B,C1,00,3C,00,01,00,D4,00,CA,00,00,00,00,00,22,02,BB,00,1E,01,F4,00,32,13,88,01,F4,01,F4,01,F4,01,F4,00,E0,00,03,00,00";
        String[] aByte = Crc16Util.to_byte(s2.split(""));
        String s3 = "01,03,00,00,00,01";
        String s3crc = "840A";
        List<String> strings = new ArrayList<>();
        String[] split = s2.split(",");
        for (String s5 : split) {
            String s1 = Integer.toHexString(Integer.parseInt(s5,16)).toUpperCase();
            String tmp = StringUtils.leftPad(s1, 4, '0');
            strings.add(tmp);
        }
        String[] array = strings.toArray(new String[strings.size()]);
        byte[] data1 = Crc16Util.getData(Crc16Util.to_byte(array));
        System.out.println(Crc16Util.checkCrc("010302024E3910"));
        System.out.println(HeartbeatUtils.checkStr("C8100001001B36145D04B00005010E01F9001100120008023501C8007B006400000000005A01F401F400000000000000000000002D000D000E00100013147E", PatternEnum.CODE_C810.getPattern()));
        String str3 = "^\\d{2}0302[A-Za-z0-9]*$";
        System.out.println(HeartbeatUtils.checkStr("010302024E3910", str3));

    }


}
