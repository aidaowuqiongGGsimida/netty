package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName VehicleAlarmReqMsg
 * @Description 终端报警数据实体类
 * @Author wzj
 * @Date 2018/9/5 18:10
 * @Version 1.0
 **/
@Data
public class VehicleAlarmReqMsg {
    // 报警来源
    private short alarmSrc;
    // 报警类型
    private long alarmType;
    // 报警时间
    private ByteArr alarmTime;
    // 经度
    private long longitude;
    // 纬度
    private long latitude;
    // 速度1
    private int speed1;
    // 速度2
    private int speed2;
    // 司机唯一编码长度
    private short driverIdentifyLength;
    // 司机唯一编码
    private String driverIdentify;
    // 司机姓名长度
    private short driverNameLength;
    // 司机姓名
    private String driverName;
    // 照片地址长度
    private int imgUrlLength;
    // 照片地址
    private String imgUrl;
    // 视频地址长度
    private int videoUrlLength;
    // 视频地址
    private String videoUrl;

    public int getAlarmTimeLength() {
        return 8;
    }

    public void setLongitude(Double longitude) {
        this.longitude = (long) (longitude * 1000000);
    }

    public void setLatitude(Double latitude) {
        this.latitude = (long) (latitude * 1000000);
    }

}
