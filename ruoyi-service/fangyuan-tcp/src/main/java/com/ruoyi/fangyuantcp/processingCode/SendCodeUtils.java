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
     * 普通操作指令发送  05
     * */
    public static int query05(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "05," + tcpOrder.getOperationText();
        return basisUtils.operateCode(text, tcpOrder);
    }

    /*
     * 普通操作指令发送  06  自动状态设置更改
     * */
    public static int query06(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "06," + tcpOrder.getOperationText();
        return basisUtils.operateCode(text, tcpOrder);
    }

    /*
     * 普通操作指令发送子线程   不指定操作码
     * */
    public static int query(DbOperationVo tcpOrder) {
        String text = tcpOrder.getOperationText();
        return basisUtils.operateCode(text, tcpOrder);
    }


    /*
     * 状态查询指令发送03
     * */
    public static int query03(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "03," + tcpOrder.getOperationText();
        return basisUtils.operateCode(text, tcpOrder);
    }


    /*
     *状态操作指令发送01
     * */
    public static int query01(DbOperationVo tcpOrder) {
        String text = tcpOrder.getFacility() + "," + "01," + tcpOrder.getOperationText();
        return basisUtils.operateCode(text, tcpOrder);
    }


    public void queryNoWait(DbOperationVo tcpOrder,int type) {
        String text = tcpOrder.getFacility() + ",0" + type+"," + tcpOrder.getOperationText();
         basisUtils.operateCodeNoWait(text, tcpOrder);
    }
}
