package com.zj.netty.entity;

import com.zj.netty.utils.Constants;
import lombok.Data;

/**
 * @ClassName VehicleLocationReqMsg
 * @Description 车辆位置实体类
 * @Author wzj
 * @Date 2018/9/4 16:27
 * @Version 1.0
 **/
@Data
public class VehicleLocationReqMsg {

    // 加密标志  默认0
    private short encryptFlag = Constants.UPLOAD_LOCATION_ENCRYPT_FLAG;
    // 日期 byte[4]
    private ByteArr date;
    // 时间 byte[3]
    private ByteArr time;
    // 经度
    private long longitude;
    // 纬度
    private long latitude;
    // 速度1
    private int speed1;
    // 速度2
    private int speed2;
    //里程
    private long mileage;
    // 方向
    private int direction;
    // 海拔
    private int elevation;
    // 车辆状态
    private long vehicleStatus;
    // 报警状态
    private long alarmStatus;

    public int getDateLength() {
        return 4;
    }

    public int getTimeLength() {
        return 3;
    }

    public void setLongitude(Double longitude) {
        this.longitude = (long) (longitude * 1000000);
    }

    public void setLatitude(Double latitude) {
        this.latitude = (long) (latitude * 1000000);
    }
}
