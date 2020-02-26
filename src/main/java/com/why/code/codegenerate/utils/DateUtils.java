package com.why.code.codegenerate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * @project: server
 * @date: 2018/4/16
 * @author: BRUCE
 */
public class DateUtils {

    public static final String DD_FORMAT = "yyyy-MM-dd";
    public static final String HHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String HHMMSFORMAT="yyyyMMddHHmmss";
    public static final String YMDHM="yyyy-MM-dd HH:mm";
    public static final String HHMM="yyyy-MM";
    public static final String LOCAL_HHMMSS_FORMAT = "HH:mm:ss";
    public static final Long DAYMS = 1 * 24 * 60 * 60 * 1000L;

    /**
     * 解析时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取指定天的时间
     *
     * @param date
     * @param day
     * @return
     */
    public static Date addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }

    /**
     * 在当前日期上面增加分钟数
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 获取今天最后23:59:59
     *
     * @param date
     * @return
     */
    public static Date getEndTimeOfNDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取今天最开始的时间00:00:00
     *
     * @param date
     * @return
     */
    public static Date getStartTimeOfNDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date convertDayToHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date convertTimeToDate(Long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        return calendar.getTime();

    }

    public static String convertDateToStr(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String str = sdf.format(date);
        return str;
    }

    /**
     * 根据时间字符串和时间格式，转为指定的date类型
     *
     * @param dateStr   指定类型的时间字符串
     * @param formatStr 传入时间的格式
     * @return 时间类型
     */
    public static Date convertStrToDate(String dateStr, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            System.out.println(dateStr + "时间转化错误，格式为：" + formatStr);
        }
        return date;
    }

    /**
     * 计算两个日期之间相差的秒数
     *
     * @param smallDate 较小的时间
     * @param bigDate   较大的时间
     * @return 相差秒数
     */
    public static int secondsBetween(final Date smallDate, final Date bigDate) {
        long time1 = smallDate.getTime();
        long time2 = bigDate.getTime();
        long betweenSeconds = (time2 - time1) / (1000);
        return (int) (betweenSeconds);
    }

    /**
     * 日期之间相差的天数
     *
     * @param smallDate
     * @param bigDate
     * @return
     */
    public static int daysBetweenByDate(final Date smallDate, final Date bigDate) {
        LocalDate smallLocalDate = dateToLocalDate(smallDate);
        LocalDate bigLocalDate = dateToLocalDate(bigDate);
        long betweenDays = bigLocalDate.toEpochDay() - smallLocalDate.toEpochDay();
        return (int) (betweenDays);
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }
}
