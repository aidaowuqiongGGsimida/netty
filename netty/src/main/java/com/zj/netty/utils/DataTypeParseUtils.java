package com.zj.netty.utils;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @ClassName DataTypeParseUtils
 * @Description 数据类型转换工具
 * @Author wzj
 * @Date 2018/9/5 14:42
 * @Version 1.0
 **/
public class DataTypeParseUtils {

    private static BCD8421Operater bcd8421Operater = new BCD8421Operater();
    private static Logger LOG = Logger.getLogger(DataTypeParseUtils.class);
    /**
     * @param str
     * @param length
     * @return
     * @Description 字符串转指定长度字节数组，多则丢弃，少则补0
     * @Date 2018/9/6 20:29
     * @CreateBy wzj
     **/
    public static byte[] string2DesignatedLengthBytes(String str, int length) {
        byte[] bytes = null;
        try {
            bytes = EncodingUtils.convert2DefaultEncoding(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int bytesLength = bytes.length;
        if (bytesLength == length) {
            return bytes;
        } else if (bytesLength > length) {
            byte[] result = new byte[length];
            System.arraycopy(bytes, 0, result, 0, length);
            return result;
        } else if (bytesLength < length) {
            byte[] result = new byte[length];
            System.arraycopy(bytes, 0, result, 0, bytesLength);
            for (int i = bytesLength; i < length; i++) {
                result[i] = 0;
            }
            return result;
        }
        return null;
    }

    /**
     * @param s
     * @return
     * @Description short --> byte[] 相当于无符号的BYTE
     * @Date 2018/9/6 20:29
     * @CreateBy wzj
     **/
    public static byte[] unsignedByte2Bytes(short s) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        return removeFrontBit(buffer.putShort(s).array(), 1);
    }


    /**
     * @param i
     * @return
     * @Description int --> byte[]  相当于无符号WORD
     * @Date 2018/9/6 20:30
     * @CreateBy wzj
     **/
    public static byte[] unsignedWord2Bytes(int i) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        return removeFrontBit(buffer.putInt(i).array(), 2);
    }

    /**
     * @param l
     * @return
     * @Description long --> byte[]   相当于无符号DWORD
     * @Date 2018/9/6 20:30
     * @CreateBy wzj
     **/
    public static byte[] unsignedDWord2Bytes(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        return removeFrontBit(buffer.putLong(l).array(), 4);
    }


    /**
     * @param bs
     * @param length
     * @return
     * @Description 移除前n个数组
     * @Date 2018/9/7 16:49
     * @CreateBy wzj
     **/
    static byte[] removeFrontBit(byte[] bs, int length) {
        byte[] result = new byte[length];
        System.arraycopy(bs, bs.length - length, result, 0, length);
        return result;
    }

    /**
     * @param data
     * @param startIndex
     * @param lenth
     * @return
     * @Description bcd[] --> String
     * @Date 2018/9/6 20:31
     * @CreateBy wzj
     **/
    private static String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth) {
        return parseBcdStringFromBytes(data, startIndex, lenth, null);
    }

    /**
     * @param data
     * @param startIndex
     * @param lenth
     * @return
     * @Description bcd[] --> String
     * @Date 2018/9/6 20:31
     * @CreateBy wzj
     **/
    private static String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth, String defaultVal) {
        try {
            byte[] tmp = new byte[lenth];
            System.arraycopy(data, startIndex, tmp, 0, lenth);
            return bcd8421Operater.bcd2String(tmp);
        } catch (Exception e) {
            LOG.info("解析BCD(8421码)出错:" + e.getMessage(), e);
            return defaultVal;
        }
    }

    /**
     * @param bs
     * @return
     * @Description byte --> short
     * @Date 2018/9/6 20:31
     * @CreateBy wzj
     **/
    public static short parseUnsignedByteFromBytes(byte bs) {
        byte[] arr = {0, bs};
        ByteBuffer buffer = ByteBuffer.wrap(arr, 0, 2);
        return buffer.getShort();
    }


    /**
     * @param bs
     * @param offset
     * @return
     * @Description 字节数组转整型
     * @Date 2018/9/6 20:16
     * @CreateBy wzj
     **/
    public static int parseUnsignedWordFromBytes(byte[] bs, int offset) {
        byte[] arr = new byte[4];
        System.arraycopy(bs, offset, arr, 2, 2);
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        return buffer.getInt();

    }

    /**
     * @param bs
     * @return
     * @Description 字节数据转无符号整型
     * @Date 2018/9/7 17:45
     * @CreateBy wzj
     **/
    public static int parseUnsignedWordFromBytes(byte[] bs) {
        return parseUnsignedWordFromBytes(bs, 0);
    }

    /**
     * @param bs
     * @param offset
     * @return
     * @Description byte[] --> long 允许从数组某个位置开始
     * @Date 2018/9/6 20:31
     * @CreateBy wzj
     **/
    public static long parseUnsignedDWordFromBytes(byte[] bs, int offset) {
        byte[] arr = new byte[8];
        System.arraycopy(bs, offset, arr, 4, 4);
        return parseLongFromBytes(arr, false);
    }

    /**
     * @param bs
     * @return
     * @Description 字节数组转DWORD
     * @Date 2018/9/7 17:47
     * @CreateBy wzj
     **/
    public static long parseUnsignedDWordFromBytes(byte[] bs) {
        return parseUnsignedDWordFromBytes(bs, 0);
    }

    /**
     * @param bs
     * @param littleEndian
     * @return
     * @Description byte[] --> long 允许设置大小端
     * @Date 2018/9/6 20:32
     * @CreateBy wzj
     **/
    public static long parseLongFromBytes(byte[] bs, boolean littleEndian) {
        // 将byte[] 封装为 ByteBuffer
        ByteBuffer buffer = ByteBuffer.wrap(bs);
        if (littleEndian) {
            // ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
            // ByteBuffer 默认为大端(BIG_ENDIAN)模式
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        return buffer.getLong();
    }

}
