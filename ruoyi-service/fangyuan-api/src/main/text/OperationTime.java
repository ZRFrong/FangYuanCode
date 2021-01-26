import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.HttpUtil;
import com.ruoyi.system.domain.DbOperationVo;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOError;
import java.util.*;

/*
 * 操作时间测试
 * */
@Log4j2
public class OperationTime {

    /*
     * */

    private String[] arr = {"00,40,255,00", "00,41,255,00", "00,42,255,00", "00,43,255,00", "00,44,255,00", "00,45,255,00"};
    private String[] arr1 = {"00,00,255,00", "00,02,255,00", "00,04,255,00", "00,06,255,00", "00,08,255,00", "00,10,255,00"};
    private String[] arr2 = {"00,01,255,00", "00,03,255,00", "00,05,255,00", "00,07,255,00", "00,09,255,00", "00,11,255,00"};

    @Test
    public void doGetTestWayOne() throws Exception {
        int iOk = 0;
        int shortestTime = 0;
        int LondTime = 0;
        int averageTime = 0;
        int totalTime = 0;
        int iError = 0;
        int shortestTimeError = 0;
        int LondTimeError = 0;
        int averageTimeError = 0;
        int totalTimeError = 0;

        int count = 0;

        for (int i2 = 0; i2 <1000 ; i2++) {
            
            count++;
            Random random1 = new Random();
            String text = "";
            String handleName = "";
            String name = "1";
            String id = "132";


            int i = random1.nextInt(3);
            switch (i) {
                case (0):
//                    卷起
                    text = arr1[random1.nextInt(arr1.length)];
                    handleName = "start";
                    break;
                case (1):
//                   暂停
                    text = arr[random1.nextInt(arr1.length)];
                    handleName = "start_stop";
                    break;
                case (2):
//                    放下
                    text = arr2[random1.nextInt(arr1.length)];
                    handleName = "down";
                    break;
            }
            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
            String method = "GET";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("TOKEN", "460DD35443B5A9873AFDB5A520D7BE2BD2D2188531944C3E91F8CB46793C858D1333C144F95523D5AB2FD1B3BB3F0DACE57DDC4411099D4063CF36638D0ADE31");
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("text", text);
            querys.put("id", id);
            querys.put("name", name);
            querys.put("handleName", handleName);
            querys.put("type", "0");

            String host = "http://47.99.133.95:9527";
            String path = "/fangyuanapi/operateWeChat/operate";
            long time = new Date().getTime();
            HttpResponse httpResponse = HttpUtil.doGet(host, path, method, headers, querys);

            String s = EntityUtils.toString(httpResponse.getEntity());
            Map<String, Object> parse = (Map<String, Object>) JSONObject.parse(s);
            System.out.println(parse);
            if (parse.get("code").equals(200)) {
//                成功次数
                long time1 = new Date().getTime();
                //                平均耗时；
                iOk++;

                int consuming = (int) (time1 - time);
                averageTime += consuming;
//                最低耗时；
                if (shortestTime > consuming||shortestTime==0) {
                    shortestTime = consuming;
                }
//                最高耗时；
                if (LondTime < consuming) {
                    LondTime = consuming;
                }

                log.info("操作指令" + text + "成功返回" + "用时" + consuming);

            } else if (parse.get("code").equals(500)) {
//                失败次数
                long time1 = new Date().getTime();
                //                平均耗时；
                iError++;

                int consuming = (int) (time1 - time);
                averageTimeError += consuming;
//                最低耗时；
                if (shortestTimeError > consuming||shortestTimeError==0) {
                    shortestTimeError = consuming;
                }
//                最高耗时；
                if (LondTimeError < consuming) {
                    LondTimeError = consuming;
                }
                log.info("操作指令" + text + "失败返回" + "用时" + consuming);
            }

        }
        log.info("一共执行的请求次数" + count);
        log.info("成功的请求次数" + iOk);
        log.info("成功的最长用时" + LondTime);
        log.info("成功的最短用时" + shortestTime);
        log.info("成功的平均用时" + averageTime / iOk);
        log.info("========================================");
        log.info("失败的请求次数" + iError);
        log.info("失败的最长用时" + LondTimeError);
        log.info("失败的最短用时" + shortestTimeError);
        log.info("失败的平均用时" + averageTimeError / iError);
    }


    /*
    *2g
    * */
    @Test
    public void doGetTestWayTwo() throws Exception {
        int iOk = 0;
        int shortestTime = 0;
        int LondTime = 0;
        int averageTime = 0;
        int totalTime = 0;
        int iError = 0;
        int shortestTimeError = 0;
        int LondTimeError = 0;
        int averageTimeError = 0;
        int totalTimeError = 0;

        int count = 0;

        for (int i2 = 0; i2 <1000 ; i2++) {

            count++;
            Random random1 = new Random();
            String text = "";
            String handleName = "";
            String name = "1";
            String id = "132";


            int i = random1.nextInt(3);
            switch (i) {
                case (0):
//                    卷起
                    text = arr1[random1.nextInt(arr1.length)];
                    handleName = "start";
                    break;
                case (1):
//                   暂停
                    text = arr[random1.nextInt(arr1.length)];
                    handleName = "start_stop";
                    break;
                case (2):
//                    放下
                    text = arr2[random1.nextInt(arr1.length)];
                    handleName = "down";
                    break;
            }
            // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
            String method = "GET";
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("TOKEN", "460DD35443B5A9873AFDB5A520D7BE2BD2D2188531944C3E91F8CB46793C858D1333C144F95523D5AB2FD1B3BB3F0DACE57DDC4411099D4063CF36638D0ADE31");
            Map<String, String> querys = new HashMap<String, String>();
            querys.put("text", text);
            querys.put("id", id);
            querys.put("name", name);
            querys.put("handleName", handleName);
            querys.put("type", "0");

            String host = "http://47.99.133.95:9527";
            String path = "/fangyuanapi/operateWeChat/operate";
            long time = new Date().getTime();
            HttpResponse httpResponse = HttpUtil.doGet(host, path, method, headers, querys);

            String s = EntityUtils.toString(httpResponse.getEntity());
            Map<String, Object> parse = (Map<String, Object>) JSONObject.parse(s);
            System.out.println(parse);
            if (parse.get("code").equals(200)) {
//                成功次数
                long time1 = new Date().getTime();
                //                平均耗时；
                iOk++;

                int consuming = (int) (time1 - time);
                averageTime += consuming;
//                最低耗时；
                if (shortestTime > consuming||shortestTime==0) {
                    shortestTime = consuming;
                }
//                最高耗时；
                if (LondTime < consuming) {
                    LondTime = consuming;
                }

                log.info("操作指令" + text + "成功返回" + "用时" + consuming);

            } else if (parse.get("code").equals(500)) {
//                失败次数
                long time1 = new Date().getTime();
                //                平均耗时；
                iError++;

                int consuming = (int) (time1 - time);
                averageTimeError += consuming;
//                最低耗时；
                if (shortestTimeError > consuming||shortestTimeError==0) {
                    shortestTimeError = consuming;
                }
//                最高耗时；
                if (LondTimeError < consuming) {
                    LondTimeError = consuming;
                }
                log.info("操作指令" + text + "失败返回" + "用时" + consuming);
            }

        }
        log.info("一共执行的请求次数" + count);
        log.info("成功的请求次数" + iOk);
        log.info("成功的最长用时" + LondTime);
        log.info("成功的最短用时" + shortestTime);
        log.info("成功的平均用时" + averageTime / iOk);
        log.info("========================================");
        log.info("失败的请求次数" + iError);
        log.info("失败的最长用时" + LondTimeError);
        log.info("失败的最短用时" + shortestTimeError);
        log.info("失败的平均用时" + averageTimeError / iError);
    }

}
