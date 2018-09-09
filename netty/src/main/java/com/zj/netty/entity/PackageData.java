package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName PackageData
 * @Description 包数据
 * @Author wzj
 * @Date 2018/9/6 18:14
 * @Version 1.0
 **/
@Data
public class PackageData {
    //消息头
    private MsgHeader msgHeader;
    //消息体
    private byte[] bodyData;
}
