package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName PrimaryLinkLoginMsg
 * @Description 主链路登录消息
 * @Author wzj
 * @Date 2018/9/5 16:00
 * @Version 1.0
 **/
@Data
public class PrimaryLinkLoginMsg {

    // 用户id
    private long userId;
    // 密码
    private String passWord;
    // 从链路服务端IP
    private String subordinationLinkIp;
    // 从链路服务端口
    private int subordinationLinkPort;

    public int getPassWordLength() {
        return 8;
    }

    public int getSubordinationLinkIpLength() {
        return 32;
    }

}
