package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName InferiorPlatformCloseLinkNotifyMsg
 * @Description 下级平台主动关闭主从链路通知
 * @Author wzj
 * @Date 2018/9/7 18:23
 * @Version 1.0
 **/
@Data
public class InferiorPlatformCloseLinkNotifyMsg {
    // 链路关闭原因
    private short linkCloseReason;
}
