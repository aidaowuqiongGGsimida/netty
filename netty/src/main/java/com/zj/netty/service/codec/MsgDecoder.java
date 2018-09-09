package com.zj.netty.service.codec;


import com.zj.netty.entity.ByteArr;
import com.zj.netty.entity.MsgHeader;
import com.zj.netty.entity.PackageData;
import com.zj.netty.utils.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * @ClassName MsgDecoder
 * @Description 解报文
 * @Author wzj
 * @Date 2018/9/4 16:23
 * @Version 1.0
 **/
public class MsgDecoder {

    private BCD8421Operater bcd8421Operater = new BCD8421Operater();


    /**
     * @param bs
     * @return
     * @Description 从原报文获取头部和消息体
     * @Date 2018/9/6 17:14
     * @CreateBy wzj
     **/
    public byte[] getHeaderAndBody(byte[] bs) {
        //剥离头尾
        byte[] headerAndBody = new byte[bs.length - 4];
        System.arraycopy(bs, 1, headerAndBody, 0, headerAndBody.length);
        return headerAndBody;
    }

    /**
     * @param headAndBody
     * @param checkCode
     * @return
     * @Description 验证校验码
     * @Date 2018/9/6 17:10
     * @CreateBy wzj
     **/
    public boolean verifyCheckCode(byte[] headAndBody, int checkCode) {
        int check = CrcUtils.CRC16CCITT(headAndBody);
        return check == checkCode;
    }

    /**
     * @param headerAndBody
     * @return
     * @Description 获取消息体
     * @Date 2018/9/6 17:24
     * @CreateBy wzj
     **/
    private byte[] getBody(byte[] headerAndBody) {
        byte[] body = new byte[headerAndBody.length - 22];
        System.arraycopy(headerAndBody, 22, body, 0, body.length);
        return body;
    }

    /**
     * @param headerAndBody
     * @return
     * @Description 获取头部数据
     * @Date 2018/9/6 17:23
     * @CreateBy wzj
     **/
    private byte[] getHeader(byte[] headerAndBody) {
        byte[] header = new byte[22];
        System.arraycopy(headerAndBody, 0, header, 0, header.length);
        return header;
    }

    /**
     * @param binData
     * @param clazz
     * @return
     * @Description 字节数据转化成实体类
     * @Date 2018/9/6 17:21
     * @CreateBy wzj
     **/
    public <T> T decodeEntityFromBytes(byte[] binData, Class clazz) throws Exception {

        //声明对象
        T t = (T) clazz.newInstance();

        Field[] fields = clazz.getDeclaredFields();
        //当前位置
        int currentPos = 0;
        for (Field field : fields) {
            String type = field.getType().getName();
            field.setAccessible(true);
            if ("long".equals(type)) {
                //DWORD
                long l = DataTypeParseUtils.parseUnsignedDWordFromBytes(binData, currentPos);
                field.setLong(t, l);
                //这里是无符号整型DWORD 4个字节，long的正数能够存
                currentPos += 4;
            } else if ("short".equals(type)) {
                //BYTE
                short shortData = DataTypeParseUtils.parseUnsignedByteFromBytes(binData[currentPos]);
                field.setShort(t, shortData);
                //这里是无符号BYTE 1个字节，short的正数能够存
                currentPos += 1;
            } else if ("int".equals(type)) {
                //WORD
                int intData = DataTypeParseUtils.parseUnsignedWordFromBytes(binData, currentPos);
                field.setInt(t, intData);
                //这里是无符号WORD 2个字节，int的正数能够存
                currentPos += 2;
            } else if ("java.lang.String".equals(type)) {
                int length = binData.length;
                int stringIndex = length;
                for (int i = currentPos; i < length; i++) {
                    if (binData[i] == 0) {
                        stringIndex = i;
                    }
                }
                int stringLength = stringIndex - currentPos;
                String str = new String(binData, currentPos, stringLength);
                field.set(t, str);
                //记得加上0结束标志
                currentPos += stringLength + 1;
            } else if ("com.xmwx.entity.Bcd".equals(type)) {

            } else if ("com.xmwx.entity.MsgHeader".equals(type)) {
                byte[] header = getHeader(binData);
                MsgHeader msgHeader = decodeEntityFromBytes(header, MsgHeader.class);
                field.set(t, msgHeader);
                currentPos += 22;
            } else if ("com.xmwx.entity.ByteArr".equals(type)) {
                int length = StrUtils.getSpecialValueLength(t, field.getName());
                byte[] array = new byte[length];
                System.arraycopy(binData, currentPos, array, 0, array.length);
                ByteArr byteArr = new ByteArr(array);
                byteArr.setValue(new String(array));
                field.set(t, byteArr);
                currentPos += length;
            }
        }
        return (T) t;
    }

    /**
     * @param binData
     * @return
     * @Description 获取校验码
     * @Date 2018/9/6 17:18
     * @CreateBy wzj
     **/
    private int getCheckCode(byte[] binData) {
        byte data1 = binData[binData.length - 3];
        byte data2 = binData[binData.length - 2];
        return DataTypeParseUtils.parseUnsignedWordFromBytes(new byte[]{data1, data2}, 0);
    }


    /**
     * @param bytes
     * @return
     * @Description 转义并返回数据包实体
     * @Date 2018/9/6 18:20
     * @CreateBy wzj
     **/
    public PackageData getPackageData(byte[] bytes) throws Exception {
        //转义
        bytes = ProtocolUtils.doEscape4Receive(bytes);
        //获取校验码
        int checkCode = getCheckCode(bytes);
        //获取头部跟消息体
        byte[] headerAndBody = getHeaderAndBody(bytes);
        //TODO 暂时不验证验证码
        if (verifyCheckCode(headerAndBody, checkCode)) {

        } else {
            System.out.println("校验码不正确");
        }
        MsgHeader msgHeader = decodeEntityFromBytes(headerAndBody, MsgHeader.class);

        PackageData packageData = new PackageData();

        packageData.setMsgHeader(msgHeader);

        packageData.setBodyData(getBody(headerAndBody));

        return packageData;
    }
}
