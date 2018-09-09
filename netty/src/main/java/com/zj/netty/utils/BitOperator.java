package com.zj.netty.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class BitOperator {
    private static final String HEX = "0123456789abcdef";

    /**
     * 实现字节数组向十六进制的转换方法一
     *
     * @param bytes
     * @return
     */
    public static String byte2HexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 取出这个字节的高4位，然后与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            // 取出这个字节的低位，与0x0f与运算，得到一个0-15之间的数据，通过HEX.charAt(0-15)即为16进制数
            sb.append(HEX.charAt(b & 0x0f));
        }

        return sb.toString();
        //return HexStringUtils.toHexString(bytes);
    }
	/*public static String byte2HexStr(byte[] bytes)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < bytes.length; n++)
		{
			stmp = (Integer.toHexString(bytes[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}*/

    /**
     * 把一个整形该为byte
     *
     * @param value
     * @return
     * @throws Exception
     */
    public byte integerTo1Byte(int value) {
        return (byte) (value & 0xFF);
    }

    /**
     * 把一个整形该为1位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] integerTo1Bytes(int value) {
        byte[] result = new byte[1];
        result[0] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为2位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] integerTo2Bytes(int value) {
        byte[] result = new byte[2];
        result[0] = (byte) ((value >>> 8) & 0xFF);
        result[1] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为3位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] integerTo3Bytes(int value) {
        byte[] result = new byte[3];
        result[0] = (byte) ((value >>> 16) & 0xFF);
        result[1] = (byte) ((value >>> 8) & 0xFF);
        result[2] = (byte) (value & 0xFF);
        return result;
    }

    /**
     * 把一个整形改为4位的byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] integerTo4Bytes(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) ((value >>> 24) & 0xFF);
        result[1] = (byte) ((value >>> 16) & 0xFF);
        result[2] = (byte) ((value >>> 8) & 0xFF);
        result[3] = (byte) (value & 0xFF);
        return result;
    }

    public byte[] integerT4Bytes(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);
        targets[1] = (byte) ((res >> 8) & 0xff);
        targets[2] = (byte) ((res >> 16) & 0xff);
        targets[3] = (byte) (res >>> 24);
        return targets;
    }

    public static void main(String[] args) {
        BitOperator bitOperator = new BitOperator();
        byte[] bytes = bitOperator.integerTo4Bytes(0);
        for (int i = 0; i < bytes.length; i++) {

        }
    }


    /**
     * 把byte[]转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    public int byteToInteger(byte[] value) {
        int result;
        if (value.length == 1) {
            result = oneByteToInteger(value[0]);
        } else if (value.length == 2) {
            result = twoBytesToInteger(value);
        } else if (value.length == 3) {
            result = threeBytesToInteger(value);
        } else if (value.length == 4) {
            result = fourBytesToInteger(value);
        } else {
            result = fourBytesToInteger(value);
        }
        return result;
    }

    /**
     * 把一个byte转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    public int oneByteToInteger(byte value) {
        return (int) value & 0xFF;
    }

    /**
     * 把一个2位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public int twoBytesToInteger(byte[] value) {
        // if (value.length < 2) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        return ((temp0 << 8) + temp1);
    }

    /**
     * 把一个3位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public int threeBytesToInteger(byte[] value) {
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        return ((temp0 << 16) + (temp1 << 8) + temp2);
    }

    /**
     * 把一个4位的数组转化位整形,通常为指令用
     *
     * @param value
     * @return
     * @throws Exception
     */
    public int fourBytesToInteger(byte[] value) {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return ((temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个4位的数组转化位整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public long fourBytesToLong(byte[] value) throws Exception {
        // if (value.length < 4) {
        // throw new Exception("Byte array too short!");
        // }
        int temp0 = value[0] & 0xFF;
        int temp1 = value[1] & 0xFF;
        int temp2 = value[2] & 0xFF;
        int temp3 = value[3] & 0xFF;
        return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
    }

    /**
     * 把一个数组转化长整形
     *
     * @param value
     * @return
     * @throws Exception
     */
    public long bytes2Long(byte[] value) {
        long result = 0;
        int len = value.length;
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result += (value[i] & 0x0ff);
            } else {
                result += (value[i] & 0x0ff) << temp;
            }
        }
        return result;
    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] longToBytes(long value) {
        return longToBytes(value, 8);
    }

    /**
     * 把一个长整形改为byte数组
     *
     * @param value
     * @return
     * @throws Exception
     */
    public byte[] longToBytes(long value, int len) {
        byte[] result = new byte[len];
        int temp;
        for (int i = 0; i < len; i++) {
            temp = (len - 1 - i) * 8;
            if (temp == 0) {
                result[i] += (value & 0x0ff);
            } else {
                result[i] += (value >>> temp) & 0x0ff;
            }
        }
        return result;
    }

    /**
     * @param str
     * @return
     * @Description utf8字符串转字节数组
     * @Date 2018/8/14 16:22
     * @CreateBy wzj
     **/
    public byte[] stringToBytes(String str) {
        if (str != null) {
            return concatAll(str.getBytes(), new byte[]{0});
        }
        return null;
    }

    /**
     * @param str
     * @return
     * @Description 字符串转gbk编码字节数组
     * @Date 2018/8/14 16:31
     * @CreateBy wzj
     **/
    public byte[] stringToGbkBytes(String str) throws UnsupportedEncodingException {
        byte[] gbkBinArr = EncodingUtils.convert2GbkEncoding(str);
        return concatAll(gbkBinArr, new byte[]{0});//加入字符串结束标志
    }

    /**
     * @param str
     * @return
     * @Description 字符串转默认编码字节数组
     * @Date 2018/8/14 16:31
     * @CreateBy wzj
     **/
    public byte[] stringToDefaultCharsetBytes(String str) throws UnsupportedEncodingException {
        byte[] defaultEncoding = EncodingUtils.convert2DefaultEncoding(str);
        return concatAll(defaultEncoding, new byte[]{0});
    }

    /**
     * 得到一个消息ID
     *
     * @return
     * @throws Exception
     */
    public byte[] generateTransactionID() throws Exception {
        byte[] id = new byte[16];
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 0, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 2, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 4, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 6, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 8, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 10, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 12, 2);
        System.arraycopy(integerTo2Bytes((int) (Math.random() * 65536)), 0, id, 14, 2);
        return id;
    }

    /**
     * 把IP拆分位int数组
     *
     * @param ip
     * @return
     * @throws Exception
     */
    public int[] getIntIPValue(String ip) throws Exception {
        String[] sip = ip.split("[.]");
        // if (sip.length != 4) {
        // throw new Exception("error IPAddress");
        // }
        int[] intIP = {Integer.parseInt(sip[0]), Integer.parseInt(sip[1]), Integer.parseInt(sip[2]),
                Integer.parseInt(sip[3])};
        return intIP;
    }

    /**
     * 把byte类型IP地址转化位字符串
     *
     * @param address
     * @return
     * @throws Exception
     */
    public String getStringIPValue(byte[] address) throws Exception {
        int first = this.oneByteToInteger(address[0]);
        int second = this.oneByteToInteger(address[1]);
        int third = this.oneByteToInteger(address[2]);
        int fourth = this.oneByteToInteger(address[3]);

        return first + "." + second + "." + third + "." + fourth;
    }

    /**
     * 合并字节数组
     *
     * @param first
     * @param rest
     * @return
     */
    public byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    /**
     * 合并字节数组
     *
     * @param rest
     * @return
     */
    public byte[] concatAll(List<byte[]> rest) {
        int totalLength = 0;
        for (byte[] array : rest) {
            if (array != null) {
                totalLength += array.length;
            }
        }
        byte[] result = new byte[totalLength];
        int offset = 0;
        for (byte[] array : rest) {
            if (array != null) {
                System.arraycopy(array, 0, result, offset, array.length);
                offset += array.length;
            }
        }
        return result;
    }

    public float byte2Float(byte[] bs) {
        return Float.intBitsToFloat(
                (((bs[3] & 0xFF) << 24) + ((bs[2] & 0xFF) << 16) + ((bs[1] & 0xFF) << 8) + (bs[0] & 0xFF)));
    }

    public float byteBE2Float(byte[] bytes) {
        int l;
        l = bytes[0];
        l &= 0xff;
        l |= ((long) bytes[1] << 8);
        l &= 0xffff;
        l |= ((long) bytes[2] << 16);
        l &= 0xffffff;
        l |= ((long) bytes[3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * @param bs
     * @param start
     * @param end
     * @return
     * @Description 获取校验码，校验码为消息头开始，同后一字节异或，直到校验码前一个字节，占用一个字节。
     * @Date 2018/8/6 10:10
     * @CreateBy wzj
     **/
    public int getCheckSum4JT808(byte[] bs, int start, int end) {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("getCheckSum4JT808 error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes length=" + bs.length + ")");
        int cs = 0;
        for (int i = start; i < end; i++) {
            cs ^= bs[i];
        }
        return cs;
    }

    public int getBitRange(int number, int start, int end) {
        if (start < 0)
            throw new IndexOutOfBoundsException("min index is 0,but start = " + start);
        if (end >= Integer.SIZE)
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but end = " + end);

        return (number << Integer.SIZE - (end + 1)) >>> Integer.SIZE - (end - start + 1);
    }

    public int getBitAt(int number, int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("min index is 0,but " + index);
        if (index >= Integer.SIZE)
            throw new IndexOutOfBoundsException("max index is " + (Integer.SIZE - 1) + ",but " + index);

        return ((1 << index) & number) >> index;
    }

    public int getBitAtS(int number, int index) {
        String s = Integer.toBinaryString(number);
        return Integer.parseInt(s.charAt(index) + "");
    }

    @Deprecated
    public int getBitRangeS(int number, int start, int end) {
        String s = Integer.toBinaryString(number);
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < Integer.SIZE) {
            sb.insert(0, "0");
        }
        String tmp = sb.reverse().substring(start, end + 1);
        sb = new StringBuilder(tmp);
        return Integer.parseInt(sb.reverse().toString(), 2);
    }

    /**
     * 字符串转32长度字节数组，不足则以0填充
     *
     * @param str
     * @return
     */
    public byte[] get32ByteArrFromString(String str) {
        byte[] result = new byte[32];
        if (str == null) {
            for (int i = 0; i < 32; i++)
                result[i] = 0;
            return result;
        } else if (str.length() == 32) {
            return str.getBytes();
        } else {
            byte[] bytes = str.getBytes();
            for (int i = 0; i < bytes.length; i++)
                result[i] = bytes[i];
            for (int i = bytes.length; i < result.length; i++)
                result[i] = 0;
            return result;
        }
    }

}
