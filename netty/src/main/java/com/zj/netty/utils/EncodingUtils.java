package com.zj.netty.utils;


import java.io.UnsupportedEncodingException;

/**
 * @ClassName EncodingUtils
 * @Description 编码工具类
 * @Author wzj
 * @Date 2018/7/25 17:07
 * @Version 1.0
 **/
public class EncodingUtils {


    /**
     * @param utf8Str
     * @return
     * @Description 将utf-8编码字符串，转换为gbk编码的字节数组
     * @Date 2018/7/25 17:12
     * @CreateBy wzj
     **/
    public static byte[] convert2GbkEncoding(String utf8Str) throws UnsupportedEncodingException {
        return convert2TargetEncoding(utf8Str, "gbk");
    }

    /**
     * @param utf8Str
     * @return
     * @Description 将utf-8编码，转换为默认编码的字节数组
     * @Date 2018/8/11 10:24
     * @CreateBy wzj
     **/
    public static byte[] convert2DefaultEncoding(String utf8Str) throws UnsupportedEncodingException {
        return convert2TargetEncoding(utf8Str, "GBK");
    }

    /**
     * @param utf8Str
     * @param targetCharset
     * @return
     * @Description 将utf-8编码转换为目标编码
     * @Date 2018/8/11 10:27
     * @CreateBy wzj
     **/
    public static byte[] convert2TargetEncoding(String utf8Str, String targetCharset) throws UnsupportedEncodingException {
        byte[] temp = utf8Str.getBytes("utf-8");
        byte[] gbkBinArr = new String(temp, "utf-8").getBytes(targetCharset);
        return gbkBinArr;
    }
}
