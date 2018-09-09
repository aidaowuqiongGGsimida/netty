package com.zj.netty.service.codec;


import com.zj.netty.entity.*;
import com.zj.netty.utils.*;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName MsgEncoder
 * @Description 组报文
 * @Author wzj
 * @Date 2018/9/4 16:24
 * @Version 1.0
 **/
public class MsgEncoder {

    private BitOperator bitOperator = new BitOperator();
    private BCD8421Operater bcd8421Operater = new BCD8421Operater();


    /**
     * @param vehicleInfoEntity
     * @param t
     * @param msgSn
     * @return
     * @Description 车辆数据编码
     * @Date 2018/9/5 19:23
     * @CreateBy wzj
     **/
    private <T> byte[] encoder4VehicleData(VehicleInfoMsg vehicleInfoEntity, T t, long msgSn, int msgId) {
        try {
            // 消息体
            //分两部分，一部分是基础数据，一部分为附加数据
            //附加数据
            byte[] extraData = encode2Bytes(t);
            //基础数据
            //设置后续数据长度
            vehicleInfoEntity.setDataLength(extraData.length);

            byte[] baseData = encode2Bytes(vehicleInfoEntity);

            byte[] body = bitOperator.concatAll(baseData, extraData);

            int length = body.length + 26;

            //生成报文头
            byte[] header = generateMsgHeader(length, msgSn, msgId);

            byte[] headerAndBody = bitOperator.concatAll(header, body);
            //获取CRC校验码
            int checkSum = CrcUtils.CRC16CCITT(headerAndBody);
//            short checkSum = ProtocolUtils.getCheckSum4JT809(headerAndBody);

            byte[] resultBytes = generateFullMsg(headerAndBody, checkSum);

            System.out.println(HexStringUtils.toHexStringFormat(resultBytes));

            return resultBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param msgPrimaryLinkLogin
     * @return
     * @Description //7.11 主链路登录请求数据
     * @Date 2018/9/5 16:06
     * @CreateBy wzj
     **/
    public byte[] encoder4Login(PrimaryLinkLoginMsg msgPrimaryLinkLogin, long msgSn) {
        return encoder(msgPrimaryLinkLogin, msgSn, MsgIdConstants.LOGIN_MSG_ID);
    }


    /**
     * @param primaryLinkLogoutMsg
     * @param msgSn
     * @return
     * @Description 7.13 主链路注销
     * @Date 2018/9/7 13:45
     * @CreateBy wzj
     **/
    public byte[] encoder4Logout(PrimaryLinkLogoutMsg primaryLinkLogoutMsg, long msgSn) {
        return encoder(primaryLinkLogoutMsg, msgSn, MsgIdConstants.LOGOUT_MSG_ID);
    }

    /**
     * @param msgSn
     * @return
     * @Description 7.15 主链路连接保持请求
     * @Date 2018/9/7 18:18
     * @CreateBy wzj
     **/
    public byte[] encoder4KeepLive(long msgSn) {
        return encoder(null, msgSn, MsgIdConstants.KEEP_LIVE_MSG_ID);
    }

    /**
     * @param
     * @return
     * @Description 7.17 主链路断开通知
     * @Date 2018/9/7 18:19
     * @CreateBy wzj
     **/
    public byte[] encoder4PrimaryLinkBreak(PrimaryLinkBreakOutNotifyMsg primaryLinkBreakOutNotifyMsg, long msgSn) {
        return encoder(primaryLinkBreakOutNotifyMsg, msgSn, MsgIdConstants.BREAK_OUT_NOTIFY_MSG_ID);
    }

    /**
     * @param
     * @return
     * @Description 7.1.8 下级平台主动关闭主从链路通知
     * @Date 2018/9/7 18:22
     * @CreateBy wzj
     **/
    public byte[] encoder4InferiorPlatformCloseLink(InferiorPlatformCloseLinkNotifyMsg inferiorPlatformCloseLinkNotifyMsg, long msgSn) {
        return encoder(inferiorPlatformCloseLinkNotifyMsg, msgSn, MsgIdConstants.INFERIOR_PLATFORM_CLOSE_LINK_NOTIFY_MSG_ID);
    }

    /**
     * @param subordinationLinkConnectReplyMsg
     * @param msgSn
     * @return
     * @Description 7.1.10 从链路连接应答
     * @Date 2018/9/7 18:27
     * @CreateBy wzj
     **/
    public byte[] encoder4SubordinationLinkConnectReply(SubordinationLinkConnectReplyMsg subordinationLinkConnectReplyMsg, long msgSn) {
        return encoder(subordinationLinkConnectReplyMsg, msgSn, MsgIdConstants.SUBORDINATION_LINK_CONNECT_REPLY_MSG_ID);
    }

    /**
     * @param msgSn
     * @return
     * @Description 7.1.12 从链路注销应答
     * @Date 2018/9/7 18:30
     * @CreateBy wzj
     **/
    public byte[] encoder4SubordinationLinkLogoutReply(long msgSn) {
        return encoder(null, msgSn, MsgIdConstants.SUBORDINATION_LINK_LOGOUT_REPLY_MSG_ID);
    }

    /**
     * @param msgSn
     * @return
     * @Description 7.1.14 从链路连接保持应答
     * @Date 2018/9/7 18:31
     * @CreateBy wzj
     **/
    public byte[] encoder4SubordinationLinkKeepLiveReply(long msgSn) {
        return encoder(null, msgSn, MsgIdConstants.SUBORDINATION_LINK_KEEP_LIVE_REPLY_MSG_ID);
    }

    /**
     * @param
     * @return
     * @Description 组拼上传车辆定位信息报文
     * @Date 2018/9/4 16:25
     * @CreateBy wzj
     **/
    public byte[] encoder4Location(VehicleInfoMsg vehicleInfoEntity, VehicleLocationReqMsg vehicleLocationEntity, long msgSn) {
        //设置子业务id
        vehicleInfoEntity.setChildBusinessMsgId(MsgIdConstants.VEHICLE_LOCATION_SON_MSG_ID);

        return encoder4VehicleData(vehicleInfoEntity, vehicleLocationEntity, msgSn, MsgIdConstants.VEHICLE_LOCATION_MSG_ID);
    }

    //7.3.1 实时上传车辆定位信息消息
    public byte[] encoder4TerminalAlarm(VehicleInfoMsg vehicleInfoEntity, VehicleAlarmReqMsg vehicleAlarmEntity, long msgSn) {
        //设置子业务id
        vehicleInfoEntity.setChildBusinessMsgId(MsgIdConstants.TERMINAL_ALARM_DATA_SON_MSG_ID);
        return encoder4VehicleData(vehicleInfoEntity, vehicleAlarmEntity, msgSn, MsgIdConstants.TERMINAL_ALARM_DATA_MSG_ID);
    }

    /**
     * @param t
     * @param msgSn
     * @return
     * @Description 编码
     * @Date 2018/9/5 18:05
     * @CreateBy wzj
     **/
    private <T> byte[] encoder(T t, long msgSn, int msgId) {
        try {
            // 消息体
            byte[] body = encode2Bytes(t);

            int length = body.length + 26;

            byte[] header = generateMsgHeader(length, msgSn, msgId);

            byte[] headerAndBody = bitOperator.concatAll(header, body);
            //获取CRC校验码
//            short checkSum = ProtocolUtils.getCheckSum4JT809(headerAndBody);
            short checkSum = (short) CrcUtils.CRC16CCITT(headerAndBody);
            byte[] resultBytes = generateFullMsg(headerAndBody, checkSum);

            System.out.println(HexStringUtils.toHexStringFormat(resultBytes));
            System.out.println(HexStringUtils.toHexString(resultBytes));

            return resultBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param msgLength
     * @param msgSn
     * @return
     * @Description 生成消息头
     * @Date 2018/9/5 17:13
     * @CreateBy wzj
     **/
    public byte[] generateMsgHeader(int msgLength, long msgSn, short msgId) throws Exception {
        // 消息头
        MsgHeader msgHeader = new MsgHeader();
        // 设置消息长度  头标志 1、消息头22、消息体、尾标志1  = 消息体长度+ 24
        msgHeader.setMsgLength(msgLength);
        // 设置序列号
        msgHeader.setMsgSn(msgSn);
        //业务数据类型
        msgHeader.setMsgId(msgId);

        return encode2Bytes(msgHeader);
    }

    /**
     * @param msgLength
     * @param msgSn
     * @param msgId
     * @return
     * @Description 生成消息头
     * @Date 2018/9/4 17:01
     * @CreateBy wzj
     **/
    public byte[] generateMsgHeader(int msgLength, long msgSn, int msgId) {
        /*List<byte[]> item = new ArrayList<>();
        item.add(bitOperator.integerTo4Bytes(msgLength));
        item.add(bitOperator.integerTo4Bytes(msgSn));
        item.add(bitOperator.integerTo2Bytes(msgId));
        //下级平台接入码
        item.add(bitOperator.integerTo4Bytes(Constants.MSG_GNSSCENTERID));
        //版本
        item.add(Constants.VERSION_FLAG);
        //加密标识
        item.add(new byte[]{Constants.ENCRYPT_FLAG});
        //加密秘钥
        item.add(bitOperator.integerTo4Bytes(Constants.ENCRYPT_KEY));
        return bitOperator.concatAll(item);*/
        return null;
    }

    /**
     * @param headAndBody
     * @param checkCode
     * @return
     * @Description 生成完整报文，并转义
     * @Date 2018/9/5 16:58
     * @CreateBy wzj
     **/
    private byte[] generateFullMsg(byte[] headAndBody, int checkCode) throws Exception {

        byte[] noEscapedBytes = this.bitOperator.concatAll(Arrays.asList(//
                new byte[]{MsgIdConstants.START_IDENTIFY}, // 0x5b
                headAndBody, // 消息头+ 消息体
                DataTypeParseUtils.unsignedWord2Bytes(checkCode), // 校验码
                new byte[]{MsgIdConstants.END_IDENTIFY}// 0x5d
        ));
        // 转义
        return ProtocolUtils.doEscape4Send(noEscapedBytes);
    }

    /**
     * @param t
     * @return
     * @Description 编码，将实体类转成字节数组
     * @Date 2018/9/5 15:07
     * @CreateBy wzj
     **/
    public <T> byte[] encode2Bytes(T t) throws Exception {

        if (t == null) {
            return new byte[0];
        }
//        Field[] superField = t.getClass().getFields();
        Field[] fields = t.getClass().getDeclaredFields();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        //先写父类的字段，注意，父类字段在前面
//        writeField2Array(t, superField, outputStream);

        //再写子类中的字段
        writeField2Array(t, fields, outputStream);

        return outputStream.toByteArray();
    }

    /**
     * @param t
     * @param fields
     * @param outputStream
     * @return
     * @Description 将指定字段写入到ByteArrayOutputStream对象
     * @Date 2018/9/5 15:07
     * @CreateBy wzj
     **/
    private <T> void writeField2Array(T t, Field[] fields, ByteArrayOutputStream outputStream) throws Exception {

        for (Field field : fields) {
            String type = field.getType().getName();
            field.setAccessible(true);
            Object obj = field.get(t);

            if ("long".equals(type)) {
                //DWORD
                outputStream.write(DataTypeParseUtils.unsignedDWord2Bytes((long) obj));
            } else if ("short".equals(type)) {
                //BYTE
                outputStream.write(DataTypeParseUtils.unsignedByte2Bytes((short) obj));
            } else if ("int".equals(type)) {
                //WORD
                outputStream.write(DataTypeParseUtils.unsignedWord2Bytes((int) obj));
            } else if ("java.lang.String".equals(type)) {
                String str = (String) obj;

                if (str == null) {
                    throw new Exception(t.getClass().getName() + "." + field.getName() + " is not null!");
                }
                try {
                    int length = StrUtils.getSpecialValueLength(t, field.getName());
                    byte[] bytes = DataTypeParseUtils.string2DesignatedLengthBytes(str, length);
                    outputStream.write(bytes);
                } catch (Exception e) {
                    //如果没有设置固定字符串长度，则以0作为结束标志
                    outputStream.write(EncodingUtils.convert2DefaultEncoding(str));
                    //加入字符串结束标志
                    outputStream.write(0);
                }

            } else if ("com.xmwx.entity.Bcd".equals(type)) {
                Bcd bcd = (Bcd) obj;
                byte[] bytes = bcd8421Operater.string2Bcd(bcd.getValue());
                outputStream.write(bytes);
            } else if ("com.xmwx.entity.ByteArr".equals(type)) {
                ByteArr byteArr = (ByteArr) obj;

                if (byteArr == null) {
                    throw new Exception(t.getClass().getName() + ".ByteArr " + field.getName() + " is not null!");
                }

                byte[] bs = byteArr.getBs();
                if (bs == null) {
                    throw new Exception(ByteArr.class.getName() + "." + field.getName() + ".byte[] is not null");
                }
                //获取数据长度
                int length = StrUtils.getSpecialValueLength(t, field.getName());
                if (bs.length != length) {
                    throw new Exception(ByteArr.class.getName() + "." + field.getName() + ".byte[] length not match! require " + length);
                }
                outputStream.write(bs);
            }
        }
    }


}
