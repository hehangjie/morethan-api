package com.morethan.game.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间工具类
 *
 * @Description: 时间工具类
 * @Author: Anthony
 * @CreateDate: 06/04/2018
 * @UpdateUser: Anthony
 * @UpdateDate: 06/04/2018 8:15 PM
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
public class DateUtil {

    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT = "yyyy-MM-dd";
    public static final String FORMAT_yyyyMMdd = "yyyyMMdd";
    public static final String FORMAT_yyyyMM = "yyyyMM";

    /**
     * yyyy-MM-dd
     *
     * @return
     */
    public static Integer getYyyyMMdd() {
        String yyyyMMdd = DateUtil.convert2String(new Date(), FORMAT_yyyyMMdd);
        return Integer.parseInt(yyyyMMdd);
    }

    /**
     * yyyyMMdd
     *
     * @param date
     * @return
     */
    public static Integer getYyyyMMdd(Date date) {
        String yyyyMMdd = DateUtil.convert2String(date, FORMAT_yyyyMMdd);
        return Integer.parseInt(yyyyMMdd);
    }

    /**
     * yyyyMM
     *
     * @return
     */
    public static Integer getYyyyMM() {
        String yyyyMM = DateUtil.convert2String(new Date(), FORMAT_yyyyMM);
        return Integer.parseInt(yyyyMM);
    }

    /**
     * 把日期字符串格式化成日期类型
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date convert2Date(String dateStr, String format) {
        SimpleDateFormat simple = new SimpleDateFormat(format);
        try {
            simple.setLenient(false);
            return simple.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把日期类型格式化成字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String convert2String(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            return formater.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 16进制时间戳，用于订单号
     * （低于毫秒级的并发）
     * @return
     */
    public static String getHexTimestamp(){
        return Long.toHexString(getTimestamp());
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        DateFormat format = new SimpleDateFormat(FORMAT_FULL);
        return format.format(new Date());
    }

    /**
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentDate() {
        DateFormat format = new SimpleDateFormat(FORMAT);
        return format.format(new Date());
    }

    /**
     * 返回当前时间-历史时间-间隔时间
     *
     * @param historyTime  历史时间
     * @param intervalTime 间隔时间
     * @return
     */
    public static int timeout(long historyTime, int intervalTime) {
        long now = DateUtil.getTimestamp();
        int r = intervalTime - (int) (now - historyTime) / 1000;
        return r;
    }

    /**
     * 把秒数转成 mm:ss
     * @param time
     * @return
     */
    public static String changeToStr(int time) {
        int min = time / 60;
        int sec = time - (min * 60);
        String strMin = min < 10 ? "0" + min : min + "";
        String strSec = sec < 10 ? "0" + sec : sec + "";
        return String.format("%s:%s", strMin, strSec);
    }

    public static long difference(String strTime){
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = sf.parse(strTime);
            Date nowDate = new Date();
            long diff = (nowDate.getTime() - date.getTime())/1000;
            return diff;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;

    }


}
