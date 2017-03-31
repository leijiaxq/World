package com.vooda.world.utils;

import android.annotation.SuppressLint;

import com.vooda.world.contant.Contants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by leijiaxq
 * Data       2016/12/20 12:03
 * Describe
 */


public class ConvertUtil {

    private ConvertUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 字节数转合适内存大小
     * <p>保留3位小数</p>
     *
     * @param byteNum 字节数
     * @return 合适内存大小
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < Contants.KB) {
            return String.format("%.3fB", byteNum + 0.0005);
        } else if (byteNum < Contants.MB) {
            return String.format("%.3fKB", byteNum / Contants.KB + 0.0005);
        } else if (byteNum < Contants.GB) {
            return String.format("%.3fMB", byteNum / Contants.MB + 0.0005);
        } else {
            return String.format("%.3fGB", byteNum / Contants.GB + 0.0005);
        }
    }




    /**
     * inputStream转outputStream
     *
     * @param is 输入流
     * @return outputStream子类
     */
    public static ByteArrayOutputStream input2OutputStream(InputStream is) {
        if (is == null) return null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[Contants.KB];
            int len;
            while ((len = is.read(b, 0, Contants.KB)) != -1) {
                os.write(b, 0, len);
            }
            return os;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (is !=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * inputStream转byteArr
     *
     * @param is 输入流
     * @return 字节数组
     */
    public static byte[] inputStream2Bytes(InputStream is) {
        if (is == null) return null;
        return input2OutputStream(is).toByteArray();
    }
}
