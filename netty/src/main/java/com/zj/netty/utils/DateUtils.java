package com.zj.netty.utils;


import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName DateUtils
 * @Description 日期工具类
 * @Author wzj
 * @Date 2018/9/5 14:34
 * @Version 1.0
 **/
public class DateUtils {

    private static final String DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";
    private static final String UTC_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    /**
     * @param time
     * @return
     * @Description 日期转协议格式日期
     * @Date 2018/9/5 14:49
     * @CreateBy wzj
     **/
    private static Date getFormatTime(String time) throws ParseException {
        SimpleDateFormat date_formatter = new SimpleDateFormat(DATE_FORMATTER);
        return date_formatter.parse(time);
    }

    /**
     * @param time
     * @return
     * @Description 日期转协议格式(dmyy)日期
     * @Date 2018/9/5 14:46
     * @CreateBy wzj
     **/
    public static byte[] date2ProtocolDate(String time) {

        try {
            Date date = getFormatTime(time);
            return parseDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param date
     * @return
     * @Description 日期转协议格式(dmyy)日期
     * @Date 2018/9/5 14:48
     * @CreateBy wzj
     **/
    public static byte[] date2ProtocolDate(Date date) {
        return parseDate(date);
    }

    /**
     * @param date
     * @return
     * @Description 日期转化
     * @Date 2018/9/5 14:48
     * @CreateBy wzj
     **/
    private static byte[] parseDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        byte month = (byte) (calendar.get(Calendar.MONTH) + 1);
        byte day = (byte) calendar.get(Calendar.DAY_OF_MONTH);

        byte[] yearsBytes = DataTypeParseUtils.unsignedWord2Bytes(year);

        return new byte[]{day, month, yearsBytes[0], yearsBytes[1]};
    }

    public static byte[] time2ProtocolTime(String time) {
        try {
            Date date = getFormatTime(time);
            return parseTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] time2ProtocolTime(Date date) {
        return parseTime(date);
    }

    private static byte[] parseTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 获取时
//        byte hour = (byte) calendar.get(Calendar.HOUR); //12小时标识
        byte hour = (byte) calendar.get(Calendar.HOUR_OF_DAY); // 24小时表示

        // 获取分
        byte minute = (byte) calendar.get(Calendar.MINUTE);

        // 获取秒
        byte second = (byte) calendar.get(Calendar.SECOND);
        return new byte[]{hour, minute, second};
    }

    /**
     * @param time
     * @return
     * @Description 转化为UTC时间
     * @Date 2018/9/6 19:36
     * @CreateBy wzj
     **/
    public static String time2UTC(String time) {
        try {
            Date date = getFormatTime(time);
            SimpleDateFormat sdf = new SimpleDateFormat(UTC_FORMATTER);
            return sdf.format(date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param date
     * @return
     * @Description 转化为UTC时间
     * @Date 2018/9/6 19:38
     * @CreateBy wzj
     **/
    public static String time2UTC(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(UTC_FORMATTER);
        return sdf.format(date).toString();
    }
}
