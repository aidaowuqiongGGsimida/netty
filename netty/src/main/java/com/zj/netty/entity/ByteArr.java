package com.zj.netty.entity;

import lombok.Data;

/**
 * @ClassName ByteArr
 * @Description TODO
 * @Author wzj
 * @Date 2018/9/5 10:24
 * @Version 1.0
 **/
@Data
public class ByteArr {

    private byte[] bs;
    private String value;

    public ByteArr(byte[] bs, String value) {
        this.bs = bs;
        this.value = value;
    }

    public ByteArr(byte[] bs) {
        this.bs = bs;
    }
}
