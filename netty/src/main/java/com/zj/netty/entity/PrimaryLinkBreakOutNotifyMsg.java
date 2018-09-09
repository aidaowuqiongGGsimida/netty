package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName PrimaryLinkBreakOutNotifyMsg
 * @Description 主链路断开通知
 * @Author wzj
 * @Date 2018/9/7 18:20
 * @Version 1.0
 **/
@Data
public class PrimaryLinkBreakOutNotifyMsg {
    //错误代码 0:主链路断开  1：其他原因
    private short errorCode;
}
