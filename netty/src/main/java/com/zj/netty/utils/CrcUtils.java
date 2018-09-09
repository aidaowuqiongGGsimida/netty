package com.zj.netty.utils;


/**
 * @ClassName CrcUtils
 * @Description crc校验码工具类
 * @Author wzj
 * @Date 2018/9/6 10:18
 * @Version 1.0
 **/
public class CrcUtils {



    /**
     * @param bytes
     * @return
     * @Description CRC16-CCITT：CCITT标准的CRC-16校验码
     * @Date 2018/9/6 19:55
     * @CreateBy wzj
     **/
    public static int CRC16CCITT(byte[] bytes) {
        System.out.println("ccitt:" + HexStringUtils.toHexStringFormat(bytes));
        int crc = 0xffff; // initial value
        int polynomial = 0x1021; // poly value
        for (int index = 0; index < bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit)
                    crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        //输出String字样的16进制
        String strCrc = Integer.toHexString(crc).toUpperCase();
        System.out.println(strCrc);
        return crc;
    }


}
