package com.zj.netty.entity;

import com.zj.netty.utils.Constants;
import lombok.Data;

/**
 * @ClassName MsgHeader
 * @Description 消息头
 * @Author wzj
 * @Date 2018/9/5 13:42
 * @Version 1.0
 **/
@Data
public class MsgHeader {

    //消息长度
    public long msgLength = 0;
    //消息序列号
    public long msgSn = 0;
    //业务数据类型
    public int msgId = 0;
    //下级平台接入码
    public long msgGnsscenterId = Constants.MSG_GNSSCENTERID;
    //协议版本号
    public ByteArr versionFlag = new ByteArr(Constants.VERSION_FLAG, "");
    //报文加密标志位
    public short encryptFlag = Constants.ENCRYPT_FLAG;
    //数据加密的秘钥
    public long encryptKey = Constants.ENCRYPT_KEY;

    public int getVersionFlagLength() {
        return 3;
    }

}
