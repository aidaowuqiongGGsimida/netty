package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName PrimaryLinkLoginReplyMsg
 * @Description 主链路登录应答消息
 * @Author wzj
 * @Date 2018/9/6 17:00
 * @Version 1.0
 **/
@Data
public class PrimaryLinkLoginReplyMsg {

    //登录结果
    private short result;
    //校验码
    private long verify;
}
