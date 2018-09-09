package com.zj.netty.utils;


import java.io.ByteArrayOutputStream;

/**
 * @ClassName ProtocolUtils
 * @Description 协议工具类
 * @Author wzj
 * @Date 2018/9/4 17:34
 * @Version 1.0
 **/
public class ProtocolUtils {

    /**
     * @param bs
     * @return
     * @Description 发送前报文转义
     * @Date 2018/9/4 17:35
     * @CreateBy wzj
     **/
    public static byte[] doEscape4Send(byte[] bs) throws Exception {

        if (bs == null || bs.length < 2) {
            throw new Exception("转义数据非法，数据为：" + HexStringUtils.toHexStringFormat(bs));
        }
        ByteArrayOutputStream baos = null;

        int dataLength = bs.length;
        try {
            baos = new ByteArrayOutputStream();

            //头标识不处理
            baos.write(bs[0]);

            for (int i = 1; i < dataLength - 1; i++) {
                if (bs[i] == 0x5b) {
                    baos.write(0x5a);
                    baos.write(0x01);
                } else if (bs[i] == 0x5a) {
                    baos.write(0x5a);
                    baos.write(0x02);
                } else if (bs[i] == 0x5d) {
                    baos.write(0x5e);
                    baos.write(0x01);
                } else if (bs[i] == 0x5e) {
                    baos.write(0x5e);
                    baos.write(0x02);
                } else {
                    baos.write(bs[i]);
                }
            }
            //尾标识不处理
            baos.write(bs[dataLength - 1]);
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }

    /**
     * @param bs
     * @return
     * @Description 接收后报文转义
     * @Date 2018/9/4 17:35
     * @CreateBy wzj
     **/
    public static byte[] doEscape4Receive(byte[] bs) throws Exception {
        if (bs == null || bs.length < 2) {
            throw new Exception("转义数据非法，数据为：" + HexStringUtils.toHexStringFormat(bs));
        }
        ByteArrayOutputStream baos = null;

        int dataLength = bs.length;
        try {
            baos = new ByteArrayOutputStream();

            //头标识不处理
            baos.write(bs[0]);

            for (int i = 1; i < dataLength - 1; i++) {

                if (bs[i] == 0x5a && bs[i + 1] == 0x01) {
                    baos.write(0x5b);
                    i++;
                } else if (bs[i] == 0x5a && bs[i + 1] == 0x02) {
                    baos.write(0x5a);
                    i++;
                } else if (bs[i] == 0x5e && bs[i + 1] == 0x01) {
                    baos.write(0x5d);
                    i++;
                } else if (bs[i] == 0x5e && bs[i + 1] == 0x02) {
                    baos.write(0x5e);
                    i++;
                } else {
                    baos.write(bs[i]);
                }

            }
            //尾标识不处理
            baos.write(bs[dataLength - 1]);
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }

    /**
     * @param bs
     * @return
     * @Description 异或消息头和消息体，生成校验码
     * @Date 2018/9/5 17:05
     * @CreateBy wzj
     **/
    public static short getCheckSum4JT809(byte[] bs) {

        short cs = 0;
        for (int i = 0; i < bs.length; i++) {
            cs ^= bs[i];
        }
        return cs;
    }
}
