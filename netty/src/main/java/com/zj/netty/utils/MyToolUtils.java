package com.zj.netty.utils;

/**
 * @ClassName MyToolUtils
 * @Description TODO
 * @Author wzj
 * @Date 2018/9/7 11:26
 * @Version 1.0
 **/
public class MyToolUtils {


    public static float getFloat(float f) {
        int degree = ((int) f) / 100;
        int cent = ((int) f) % 100;
        float sec = (f - degree * 100 - cent) * 60;
        f = degree + cent / 60.0f + sec / 3600.0f;
        f *= 1000000;
        return f;
    }

}
