package com.zj.netty.entity;


import lombok.Data;

/**
 * @ClassName Bcd
 * @Description bcd类型，一个字节有8位，前4位存放一个0-9的数值，后4位存放一个0-9的数值
 * @Author wzj
 * @Date 2018/9/4 22:31
 * @Version 1.0
 **/
@Data
public class Bcd {

    private byte[] bs;
    private String value;

}
