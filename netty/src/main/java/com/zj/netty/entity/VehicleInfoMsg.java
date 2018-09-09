package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName VehicleInfoMsg
 * @Description 车辆基础信息
 * @Author wzj
 * @Date 2018/9/4 18:07
 * @Version 1.0
 **/
@Data
public class VehicleInfoMsg {

    // 车牌号 21个字节限定
    private String numberPlate = "";
    // 车辆颜色 1-蓝色；2-黄色；3-黑色；4-白色；9-其他
    private short vehicleColor = 0;
    // 子业务消息ID
    private int childBusinessMsgId = 0;
    // 后续数据长度
    private long dataLength = 0;

    public int getNumberPlateLength() {
        return 21;
    }
}
