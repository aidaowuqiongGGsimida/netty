package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName SubordinationLinkConnectReplyMsg
 * @Description 从链路连接应答
 * @Author wzj
 * @Date 2018/9/7 18:26
 * @Version 1.0
 **/
@Data
public class SubordinationLinkConnectReplyMsg {
    // 验证结果
    private short verifyResult;
}
