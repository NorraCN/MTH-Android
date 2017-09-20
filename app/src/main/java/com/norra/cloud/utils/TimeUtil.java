package com.norra.cloud.utils;

/**
 * Created by liurenhui on 15/1/26.
 */

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressLint("UseSparseArrays")
public class TimeUtil {

    public static Map<Integer, String> WEEK_DAY_MAP = new HashMap<Integer, String>();
    public static Map<Integer, String> MONTH_CN_MAP = new HashMap<Integer, String>();

    static {
        WEEK_DAY_MAP.put(1, "星期日");
        WEEK_DAY_MAP.put(2, "星期一");
        WEEK_DAY_MAP.put(3, "星期二");
        WEEK_DAY_MAP.put(4, "星期三");
        WEEK_DAY_MAP.put(5, "星期四");
        WEEK_DAY_MAP.put(6, "星期五");
        WEEK_DAY_MAP.put(7, "星期六");

        MONTH_CN_MAP.put(0, "一月");
        MONTH_CN_MAP.put(1, "二月");
        MONTH_CN_MAP.put(2, "三月");
        MONTH_CN_MAP.put(3, "四月");
        MONTH_CN_MAP.put(4, "五月");
        MONTH_CN_MAP.put(5, "六月");
        MONTH_CN_MAP.put(6, "七月");
        MONTH_CN_MAP.put(7, "八月");
        MONTH_CN_MAP.put(8, "九月");
        MONTH_CN_MAP.put(9, "十月");
        MONTH_CN_MAP.put(10, "十一月");
        MONTH_CN_MAP.put(11, "十二月");
    }

    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    private final static SimpleDateFormat mFormatter = new SimpleDateFormat(yyyy_MM_dd, Locale.CHINESE);

    public static String getNowString() {
        return format(new Date(), yyyy_MM_dd_HH_mm_ss);
    }

    public static synchronized int dayForWeek(String strTime, String timeFormat) {
        Date tmpDate = null;
        try {
            mFormatter.applyPattern(timeFormat);
            tmpDate = mFormatter.parse(strTime);
        } catch (ParseException e) {
            return -1;
        }
        Calendar cal = new GregorianCalendar();

        cal.setTime(tmpDate);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String dayForWeekInChinese(String strTime, String timeFormat) {
        return WEEK_DAY_MAP.get(dayForWeek(strTime, timeFormat));
    }

    public static int dayForWeek(Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * @param date
     * @return 星期一 星期二
     */
    public static String dayForWeekInChinese(Date date) {
        return WEEK_DAY_MAP.get(dayForWeek(date));
    }

    public static synchronized String format(String inTime, String inTimeFormat, String outTimeFormat) {

        mFormatter.applyPattern(inTimeFormat);
        Date tmpDate;
        try {
            tmpDate = mFormatter.parse(inTime);
            mFormatter.applyPattern(outTimeFormat);
            return mFormatter.format(tmpDate);
        } catch (Exception e) {
            // change catch ParseException - Exception
            // 这里 fromat 可能会抛出其它异常,如：　StringIndexOutOfBound
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized String format(Date date) {
        mFormatter.applyPattern("yyyy-MM-dd HH:mm:ss");
        return mFormatter.format(date);
    }

    public static synchronized String format(Date date, String pattern) {
        mFormatter.applyPattern(pattern);
        return mFormatter.format(date);
    }

    public static synchronized Date parse(String time, String pattern) {

        mFormatter.applyPattern(pattern);
        try {
            return mFormatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static void resetDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static Date changeDate(Date date, int field, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, num);
        return calendar.getTime();

    }

    /**
     * 求两年的　相差月份
     *
     * @param aftDate
     * @param befDate
     * @return
     */
    public static int[] getDiffersYearMonthDay(Date befDate, Date aftDate) {
        int[] date = new int[3];
        // 不能使用全局变量，多线程访问会有问题
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aftDate);
        int aftYear = calendar.get(Calendar.YEAR);
        int aftMonth = calendar.get(Calendar.MONTH);
        int aftMonthDay = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(befDate);
        int befYear = calendar.get(Calendar.YEAR);
        int befMonth = calendar.get(Calendar.MONTH);
        int befMonthOfDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int befMonthDay = calendar.get(Calendar.DAY_OF_MONTH);

        int cYear = 0;
        int cMonth = 0;
        int cMonthDay = aftMonthDay - befMonthDay;

        if (cMonthDay < 0) {
            cMonthDay = befMonthOfDay - befMonthDay + aftMonthDay;

            calendar.setTime(aftDate);
            calendar.add(Calendar.DATE, -cMonthDay);

            aftYear = calendar.get(Calendar.YEAR);
            aftMonth = calendar.get(Calendar.MONTH);
            aftMonthDay = calendar.get(Calendar.DAY_OF_MONTH);

            cYear = aftYear - befYear;
            cMonth = aftMonth - befMonth;
        } else {
            cYear = aftYear - befYear;
            cMonth = aftMonth - befMonth;
        }
        date[0] = cYear;
        date[1] = cMonth;
        date[2] = cMonthDay;
        return date;
    }

    /**
     * 计算成长年龄
     *
     * @param befDate
     * @param aftDate
     * @return
     */
    public static String getDiffersYearMonthDayStr(Date befDate, Date aftDate) {

        StringBuffer age = new StringBuffer();

        String unKownLabel = "未知";
        String birthLabel = "出生";
        String dayLabel = "天";
        String weekLabel = "周";
        String monthLabel = "个月";
        String ageLabel = "岁";

        int[] ymd = getDiffersYearMonthDay(befDate, aftDate);

        int cYear = ymd[0];
        int cMonth = ymd[1];
        int cMonthDay = ymd[2];
        int countM = (cYear * 12 + cMonth);

        if (countM >= 0) {

            int gYear = countM / 12;
            int gMonth = countM % 12;

            if (gYear > 0) {
                age.append(gYear);
                age.append(ageLabel);
            }

            if (gMonth > 0) {
                age.append(gMonth);
                age.append(monthLabel);
                if (cMonthDay > 0) {
                    age.append(cMonthDay);
                    age.append(dayLabel);
                } else {

                }

            } else if (gMonth == 0 && gYear == 0) {// 出生

                if (cMonthDay > 0) {
                    int week = cMonthDay / 7;
                    if (week > 0) {
                        age.append(week);
                        age.append(weekLabel);
                        int dd = cMonthDay % 7;
                        if (dd > 0) {
                            age.append(dd);
                            age.append(dayLabel);
                        }
                    } else {
                        age.append(cMonthDay);
                        age.append(dayLabel);
                    }

                } else if (cMonthDay == 0) {
                    age.append(birthLabel);
                } else if (cMonthDay < 0) {
                    age.append(unKownLabel);
                }

            }

        } else {
            age.append(unKownLabel);
        }

        return age.toString();
    }

    @SuppressWarnings("deprecation")
    public static boolean isSameDay(Date day1, Date day2) {
        return day1.getYear() == day2.getYear() && day1.getMonth() == day2.getMonth() && day1.getDate() == day2.getDate();
    }


    public static int monthsBetWeen(Date start, Date end) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(end);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(start);
        int c = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH)
                - cal2.get(Calendar.MONTH);
        return c;
    }

    /**
     * 在日期上添加多几个月，并返回日期.
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonth(Date date, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, amount);
        return cal.getTime();
    }

    /**
     * Count the days between 2 date,if the first date argument is before the
     * second one, a negative number wiil be returned
     *
     * @param date1 A <code>java.util.Date</code> object
     * @param date2 A <code>java.util.Date</code> object
     * @return Day counts between the two input day If the first date argument
     * is before the seconde one, a negative number will be returned.
     */
    public static int daysBetween(Date date1, Date date2) {
        if (date1 == null || date2 == null)
            return 0;
        GregorianCalendar cal1 = new GregorianCalendar();
        cal1.setTime(date1);
        GregorianCalendar cal2 = new GregorianCalendar();
        cal2.setTime(date2);
        int before = cal1.compareTo(cal2);
        // make the later one always in cal1
        if (before < 0) {
            cal1.setTime(date2);
            cal2.setTime(date1);

        }
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        int yearBetween = year1 - year2;
        for (int i = 0; i < yearBetween; i++) {
            cal2.set(Calendar.YEAR, year2 + i);
            day1 = day1 + cal2.getActualMaximum(Calendar.DAY_OF_YEAR);
        }
        return (day1 - day2) * before;
    }

    public static String getCNMonth(Date date) {
        int month = date.getMonth();
        return MONTH_CN_MAP.get(month);
    }
}
