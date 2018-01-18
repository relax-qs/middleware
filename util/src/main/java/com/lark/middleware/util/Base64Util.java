package com.lark.middleware.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by houenxun on 16/5/16.
 *  base64 编码解码工具类
 */
public class Base64Util {
    private final static Logger logger = LogManager.getLogger(Base64Util.class);

    // 编码
    public static String getBase64(String str) {
        ;

        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("编码失败[str=" + str + "]", e);
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解码
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                logger.error("解密失败[str=" + s + "]", e);
            }
        }
        return result;
    }

    /**
     * 解码
     * @param s
     * @return
     */
    public static byte[] getBytesFromBase64(String s) {
        byte[] b = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
            } catch (Exception e) {
                logger.error("解密失败[str=" + s + "]", e);
            }
        }
        return b;
    }

    /**
     * safeUrlBase64Encode
     * @param source
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String safeUrlBase64Encode(String source) throws UnsupportedEncodingException {
        return safeUrlBase64Encode(source.getBytes("utf-8"));
    }

    /**
     * safeUrlBase64Encode
     * @param data
     * @return
     */
    public static String safeUrlBase64Encode(byte[] data) {
        String encodeBase64 = new BASE64Encoder().encode(data).replaceAll("\n", "").replaceAll("\r", "");
        String safeBase64Str = encodeBase64.replace('+', '-');
        safeBase64Str = safeBase64Str.replace('/', '_');
        safeBase64Str = safeBase64Str.replaceAll("=", "~");
        return safeBase64Str;
    }

    /**
     * safeUrlBase64Decode
     * @param safeBase64Str
     * @return
     * @throws IOException
     */
    public static String safeUrlBase64Decode(final String safeBase64Str) throws IOException {
        String base64Str = safeBase64Str.replace('-', '+');
        base64Str = base64Str.replace('_', '/');
        base64Str = base64Str.replace('~', '=');
        int mod4 = base64Str.length() % 4;
        if (mod4 > 0) {
            base64Str = base64Str + "====".substring(mod4);
        }
        byte[] data = new BASE64Decoder().decodeBuffer(base64Str);
        return new String(data, "utf-8");
    }
}
