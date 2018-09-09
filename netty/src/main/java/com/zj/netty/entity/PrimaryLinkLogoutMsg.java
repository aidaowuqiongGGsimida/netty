package com.zj.netty.entity;

/**
 * @ClassName PrimaryLinkLogoutMsg
 * @Description 主链路注销请求数据
 * @Author wzj
 * @Date 2018/9/7 13:39
 * @Version 1.0
 **/
public class PrimaryLinkLogoutMsg {
    // 用户名ID
    private long userId;
    // 用户密码
    private String passWord;
    public short getPassWordLength() {
        return 8;
    }
}
