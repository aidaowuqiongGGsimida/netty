package com.zj.netty.utils;

/**
 * @ClassName Constants
 * @Description TODO
 * @Author wzj
 * @Date 2018/9/4 16:55
 * @Version 1.0
 **/
public class Constants {

    //下级平台接入码
    public final static int MSG_GNSSCENTERID = 2620;
    //v1.0.0版本
    public final static byte[] VERSION_FLAG = new byte[]{1, 0, 0};
    //是否加密 0为不加密 1为加密
    public final static byte ENCRYPT_FLAG = 0;
    //数据加密秘钥
    public final static int ENCRYPT_KEY = 0;

    //7.2.1 实时上传车辆定位信息中的  加密标识
    public final static byte UPLOAD_LOCATION_ENCRYPT_FLAG = 0;
}
