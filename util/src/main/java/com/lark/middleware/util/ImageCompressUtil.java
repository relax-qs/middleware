package com.lark.middleware.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;

import net.coobird.thumbnailator.Thumbnails;

@SuppressWarnings("restriction")
public class ImageCompressUtil {

    private static final Logger log = LogManager.getLogger(ImageCompressUtil.class);

    /**
     * 按指定大小压缩
     *
     * @param source
     * @param dest
     * @param width
     * @param height
     */
    public static boolean resizeImageBySize(String source, String dest, int width, int height) {
        boolean flag = false;
        try {

            // 加载原图
            File fileSource = new File(source);
            Image image = ImageIO.read(fileSource);

            // 缩略图画板
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            tag.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            // 生产目标文件路径
            File targetFile = new File(dest);
            OutputStream outputStream = new FileOutputStream(targetFile);

            // 图片质量
            JPGOptions options = new JPGOptions();
            options.setQuality(100);

            // 写图片流 Jimi.getEncoderTypes()[3]即获取mini-type按jpg格式压缩
            JimiWriter writer = Jimi.createJimiWriter(Jimi.getEncoderTypes()[3], outputStream);
            writer.setSource(tag);
            writer.setOptions(options);
            writer.putImage(outputStream);
            outputStream.close();
            flag = true;
        } catch (JimiException e) {
            flag = false;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * @param srcFile  原图路径
     * @param desFile  压缩图片保存路径
     */
    public static boolean compress(String srcFile, String desFile, int outputWidth, int outputHeight) {
        boolean flag = false;
        try {
            //            byte[] imageData = toByteArray(new FileInputStream(srcFile));
            //            BufferedImage image = toBufferedImage(imageData);
            //            //输入图片
            //            File outFile = new File(desFile);
            //            createPath(outFile, true);
            //            resizeImage(image, outputWidth, outputHeight, 0.9f, outFile);
            resizePNG(srcFile, desFile, outputWidth, outputHeight, true);
            flag = true;
        } catch (Exception e) {
            log.error("压缩图片失败", e);
            flag = false;
        }
        return flag;
    }

    //把原图转换成二进制
    private static byte[] toByteArray(InputStream input) {
        if (input == null) {
            return null;
        }
        ByteArrayOutputStream output = null;
        byte[] result = null;
        try {
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 100];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }
            result = output.toByteArray();
            if (output != null) {
                output.close();
            }
        } catch (Exception e) {
        }
        return result;
    }

    //把二进制转换成图片
    private static BufferedImage toBufferedImage(byte[] imagedata) {
        Image image = Toolkit.getDefaultToolkit().createImage(imagedata);
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    /**
     * 创建输出目录
     * @param file
     * @param asFile
     * @throws IOException
     */
    private static void createPath(File file, boolean asFile) throws IOException {
        String path = file.getAbsolutePath();
        String dirPath;
        if (asFile)
            dirPath = path.substring(0, path.lastIndexOf(File.separator));
        else {
            dirPath = path;
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (asFile)
            file.createNewFile();
    }

    /**
     * 借助第三方工具输出图片
     * @param sourceImage
     * @param width
     * @param height
     * @param quality
     * @param destFile
     * @throws IOException
     */
    private static void resizeImage(final BufferedImage sourceImage, final int width, final int height, final float quality,
                                    final File destFile) throws IOException {
        try {
            Thumbnails.of(sourceImage).size(width, height).keepAspectRatio(false).outputQuality(quality).toFile(destFile);
        } catch (IllegalStateException e) {
            Thumbnails.of(sourceImage).size(width, height).keepAspectRatio(false).toFile(destFile);
        }
    }

    public static void resizePNG(String fromFile, String toFile, int outputWidth, int outputHeight, boolean proportion) {
        try {
            File f2 = new File(fromFile);

            BufferedImage bi2 = ImageIO.read(f2);
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放
            if (proportion == true) {
                // 为等比缩放计算输出的图片宽度及高度
                double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
                double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
                // 根据缩放比率大的进行缩放控制
                double rate = rate1 < rate2 ? rate1 : rate2;
                newWidth = (int) (((double) bi2.getWidth(null)) / rate);
                newHeight = (int) (((double) bi2.getHeight(null)) / rate);
            } else {
                newWidth = outputWidth; // 输出的图片宽度
                newHeight = outputHeight; // 输出的图片高度
            }
            BufferedImage to = new BufferedImage(newWidth, newHeight,

                BufferedImage.TYPE_INT_RGB);

            Graphics2D g2d = to.createGraphics();

            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,

                Transparency.TRANSLUCENT);

            g2d.dispose();

            g2d = to.createGraphics();

            Image from = bi2.getScaledInstance(newWidth, newHeight, bi2.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();

            ImageIO.write(to, "png", new File(toFile));

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
