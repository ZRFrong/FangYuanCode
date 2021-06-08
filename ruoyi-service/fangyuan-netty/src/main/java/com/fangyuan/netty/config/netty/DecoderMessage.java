package com.fangyuan.netty.config.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * @Description: 消息编码处理器
 * @Author zheng
 * @Date 2021/6/7 13:55
 * @Version 1.0
 */
@Log4j2
public class DecoderMessage extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {

        //创建字节数组,buffer.readableBytes可读字节长度
        byte[] b = new byte[buffer.readableBytes()];
        //复制内容到字节数组b
        buffer.readBytes(b);
        //字节数组转字符串
        String str = new String(b,"UTF-8");
        if (str.contains("dapeng")){
            log.info("大棚心跳为："+str);
            out.add(str);
        }else {
            String s = bytesToHexString(b);
            log.info("主动上发的十六进制字符串为："+s);
            out.add(s);
        }
    }

    private static String bytesToHexString(byte[] bArray) {
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




}
