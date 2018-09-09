package com.zj.netty.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName StrUtils
 * @Description 字符串工具类
 * @Author wzj
 * @Date 2018/9/6 20:34
 * @Version 1.0
 **/
public class StrUtils {

    /**
     * @param obj
     * @param fieldName
     * @return
     * @Description 获取特殊值的长度（BCD[n] 、byte[n]）
     * @Date 2018/9/5 13:52
     * @CreateBy wzj
     **/
    public static int getSpecialValueLength(Object obj, String fieldName) throws Exception {
        Method m = (Method) obj.getClass().getMethod("get" + makeUpperCase(fieldName) + "Length");
        Object result = m.invoke(obj);
        if (result instanceof Integer) {
            return (int) result;
        } else if (result instanceof Short) {
            return (short) result;
        } else if (result instanceof Byte) {
            return (byte) result;
        }
        throw new Exception("not match type!");
    }

    /**
     * @param name
     * @return
     * @Description 首字母大写
     * @Date 2018/9/6 20:34
     * @CreateBy wzj
     **/
    public static String makeUpperCase(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

}
