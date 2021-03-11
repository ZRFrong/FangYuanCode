package com.ruoyi.fangyuantcp.processingCode;



import com.ruoyi.system.domain.DbOperationVo;
import lombok.extern.log4j.Log4j2;


/*
 * 硬件端发送指令工具类   单设备单指令
 * */
@Log4j2
public class SendCodeUtils {

private   static SendBasisUtils basisUtils=new SendBasisUtils();

    /*
     * 普通操作指令发送子线程   不指定操作类型
     * */
    public static int query(DbOperationVo tcpOrder) {
        String text = tcpOrder.getOperationText();
        return basisUtils.operateCode(text, tcpOrder);
    }
    /*
     * 普通操作指令发送子线程   不指定操作类型
     * */
    public static int queryText(DbOperationVo tcpOrder) {
        String text = tcpOrder.getOperationText();
        return basisUtils.operateCodetest(text, tcpOrder);
    }
    /*
     * 普通操作指令发送子线程   指定操作类型
     * */
    public static int queryType(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() +","+ tcpOrder.getOperationTextType()+"," + tcpOrder.getOperationText();
        return basisUtils.operateCode(text, tcpOrder);
    }


    public static void queryNoWait(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() +","+ tcpOrder.getOperationTextType()+"," + tcpOrder.getOperationText();
         basisUtils.operateCodeNoWait(text, tcpOrder);
    }
}
