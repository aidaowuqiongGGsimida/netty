package com.zj.netty.utils;

/**
 * @ClassName MsgIdConstants
 * @Description 消息id常量
 * @Author wzj
 * @Date 2018/9/4 17:32
 * @Version 1.0
 **/
public class MsgIdConstants {

    //开始标识位
    public static final byte START_IDENTIFY = 0x5b;
    //结束标识位
    public static final byte END_IDENTIFY = 0x5d;

    //登录id
    public static final int LOGIN_MSG_ID = 0x1001;
    //主链路登录应答
    public static final int LOGIN_REPLY_MSG_ID = 0x1002;
    //主链路注销
    public static final int LOGOUT_MSG_ID = 0x1003;
    //主链路注销应答
    public static final int LOGOUT_REPLY_MSG_ID = 0x1004;
    //主链路连接保持
    public static final int KEEP_LIVE_MSG_ID = 0x1005;
    //主链路连接保持应答消息id
    public static final int KEEP_LIVE_REPLY_MSG_ID = 0x1006;
    //主链路断开通知消息id
    public static final int BREAK_OUT_NOTIFY_MSG_ID = 0x1007;
    //下级平台主动关闭主链路通知消息id
    public static final int INFERIOR_PLATFORM_CLOSE_LINK_NOTIFY_MSG_ID = 0x1008;
    //从链路连接请求数据消息id
    public static final int SUBORDINATION_LINK_CONNECT_MSG_ID = 0x9001;
    // 从链路连接应答消息id
    public static final int SUBORDINATION_LINK_CONNECT_REPLY_MSG_ID = 0x9002;
    // 从链路注销消息id
    public static final int SUBORDINATION_LINK_LOGOUT_MSG_ID = 0x9003;
    // 从链路注销应答消息id
    public static final int SUBORDINATION_LINK_LOGOUT_REPLY_MSG_ID = 0x9004;
    // 从链路连接保持答消息id
    public static final int SUBORDINATION_LINK_KEEP_LIVE_MSG_ID = 0x9005;
    // 从链路连接保持应答消息id
    public static final int SUBORDINATION_LINK_KEEP_LIVE_REPLY_MSG_ID = 0x9006;
    // 从链路断开通知消息id
    public static final int SUBORDINATION_LINK_BREAK_OUT_NOTIFY_MSG_ID = 0x9007;
    // 上级平台主动关闭主从链路通知消息id
    public static final int SUPERIOR_PLATFORM_CLOSE_LINK_NOTIFY_MSG_ID = 0x9008;

    //车辆动态信息类
    public static final int VEHICLE_LOCATION_MSG_ID = 0x1200;
    //实时上传车辆定位信息
    public static final int VEHICLE_LOCATION_SON_MSG_ID = 0x1202;
    //车辆报警信息交互业务类
    public static final int TERMINAL_ALARM_DATA_MSG_ID = 0x1400;
    //终端报警数据 （子业务id）
    public static final int TERMINAL_ALARM_DATA_SON_MSG_ID = 0x1402;
}
