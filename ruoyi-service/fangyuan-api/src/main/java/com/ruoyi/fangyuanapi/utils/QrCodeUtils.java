package com.ruoyi.fangyuanapi.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

/**
 * 二维码生成工具类
 */
public class QrCodeUtils {
    private static final String CHARSET = "utf-8";
    public static final String FORMAT = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;

    // 用二维码生成新图片，新图片加高多少，比如加的字体大小为24，这里就设置成26
    private static final int FONT_SIZE_HEIGHT = 24;
    // 用二维码生成新图片，宽度
    private static final int BUFFEREDIMAGE_SIZE_WIDTH = QRCODE_SIZE;
    // 用二维码生成新图片，高度
    private static final int BUFFEREDIMAGE_SIZE_HEIGHT = QRCODE_SIZE + FONT_SIZE_HEIGHT;
    // LOGO宽度
    private static final int LOGO_WIDTH = 100;
    // LOGO高度
    private static final int LOGO_HEIGHT = 100;

    /**
     * 生成二维码
     *
     * @param content      二维码内容
     * @param logoPath     logo地址
     * @param needCompress 是否压缩logo
     * @return 图片
     * @throws Exception
     */
    public static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        QrCodeUtils.insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     *
     * @param source       二维码图片
     * @param logoPath     LOGO图片地址
     * @param needCompress 是否压缩
     * @throws IOException
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(logoPath);
            URLConnection conn = url.openConnection();
            inputStream = conn.getInputStream();
            Image src = ImageIO.read(inputStream);
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            if (needCompress) { // 压缩LOGO
                if (width > LOGO_WIDTH) {
                    width = LOGO_WIDTH;
                }
                if (height > LOGO_HEIGHT) {
                    height = LOGO_HEIGHT;
                }
                Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                src = image;
            }
            // 插入LOGO
            Graphics2D graph = source.createGraphics();
            int x = (QRCODE_SIZE - width) / 2;
            int y = (QRCODE_SIZE - height) / 2;
            graph.drawImage(src, x, y, width, height, null);
            Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
            graph.setStroke(new BasicStroke(3f));
            graph.draw(shape);
            graph.dispose();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * 生成二维码(内嵌LOGO)
     *
     * @param content      内容
     * @param logoPath     LOGO地址
     * @param needCompress 是否压缩LOGO
     * @param note   文字说明
     * @throws String   base64图片
     */
    public static String encode(String content, String logoPath , boolean needCompress)
            throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
//        BufferedImage bufferedImage = addNote(image, note);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        ImageIO.write(image, "png", baos);//写入流中
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        //        ImageIO.write(bufferedImage, "png", new File("D:/qrcode1.png"));
        return "data:image/jpg;base64,"+png_base64;
    }

    public static String encode(String content, String logoPath , boolean needCompress,String note)
            throws Exception {
        BufferedImage image = QrCodeUtils.createImage(content, logoPath, needCompress);
        BufferedImage bufferedImage = addNote(image, note);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        ImageIO.write(bufferedImage, "png", baos);//写入流中
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        //        ImageIO.write(bufferedImage, "png", new File("D:/qrcode1.png"));
        return "data:image/jpg;base64,"+png_base64;
    }

    /**
     * 给二维码下方添加说明文字
     * @param image 原二维码
     * @param note 说明文字
     * @return 带说明文字的二维码
     */
    public static BufferedImage addNote(BufferedImage image,String note){
        Image src = image.getScaledInstance(QRCODE_SIZE, QRCODE_SIZE,Image.SCALE_DEFAULT);
        BufferedImage tag;
        if (note.length()<=24){
            //生成新图片的大小
            tag = new BufferedImage(BUFFEREDIMAGE_SIZE_WIDTH, BUFFEREDIMAGE_SIZE_HEIGHT,BufferedImage.TYPE_INT_RGB);
        }else{
            //这句代码还没调试过，这里表示文字需要折行
            tag = new BufferedImage(200, 200,BufferedImage.TYPE_INT_RGB);
        }
        //设置低栏白边
        Graphics g1 = tag.getGraphics();
        //设置文字
        Graphics2D g2 = tag.createGraphics();
        Font font = new Font("微软雅黑",Font.BOLD,20);
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        if (note.length()<=24) {
            //下面这个26要和tag = new BufferedImage(330, 356,BufferedImage.TYPE_INT_RGB);356-330=26对上
            g1.fillRect(0, QRCODE_SIZE, QRCODE_SIZE, FONT_SIZE_HEIGHT);
            //文字在图片上的位置
            g2.drawString(note,/*QRCODE_SIZE/2-note.length()*8-14*/30, QRCODE_SIZE+font.getSize());
        }else{
            //这里的代码还没测试过
            g1.fillRect(0, QRCODE_SIZE, QRCODE_SIZE, 20);
            //下面两次表示文件需要换行显示
            g2.drawString(note.substring(0, 24),5, QRCODE_SIZE+font.getSize());
            g2.drawString(note.substring(24,note.length()), QRCODE_SIZE/2-(note.length()-24)*8-14, QRCODE_SIZE+font.getSize()*2+4);
        }
        g1.drawImage(src, 0, 0, null);
        g1.dispose();
        g2.dispose();
        image = tag;
        image.flush();
        return image;
    }


    /**
     * 获取指定文件的输入流，获取logo
     *
     * @param logoPath 文件的路径
     * @return
     */
    public static InputStream getResourceAsStream(String logoPath) {
        return QrCodeUtils.class.getResourceAsStream(logoPath);
    }

    public static void main(String[] args) throws Exception {
        String encode = QrCodeUtils.encode("https://fangyuancun.cn/shop/build?qrCodeId=160", "http://cdn.fangyuancun.cn/logo9.png", true,"yanfabu-ren001-dapeng");
        System.out.println(encode);
    }



}