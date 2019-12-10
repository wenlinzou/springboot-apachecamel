package com.mq.ftp.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期处理
 *
 * @author wenlinzou
 */
public class DateUtil {
    private static final String YYYYDD = "yyyyMMdd";

    /**
     * 日期转字符串（格式）
     * @param date 日期
     * @param format 输出格式
     * @return
     */
    public static String date2Str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        if (null == date) {
            throw new NullPointerException("the param of data can't be null");
        }
        if (StringUtils.isEmpty(format)) {
            throw new NullPointerException("the param of format can't be null or empty");
        }
        return sdf.format(date);
    }

    public static Date beforeDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, -1);
        Date before = c.getTime();
        return before;
    }

    public static String getYesterDay() {
        return date2Str(beforeDate(), YYYYDD);
    }

    public static boolean isToday(long lastModified) {
        Date lastDate = new Date(lastModified);
        String lastDateStr = date2Str(lastDate, YYYYDD);

        String todayStr = getCurrnetDay();
        return todayStr.equals(lastDateStr);
    }

    public static String getCurrnetDay() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYDD, Locale.CHINESE);
        return sdf.format(date);
    }
}
