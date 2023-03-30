package com.paopao.tool.date;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 *
 * @author: ChickenWing
 * @date: 2023/1/15
 */
@Slf4j
public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String MINUTE_FORMAT = "HH:mm";

    public DateUtils() {
    }

    public static Date getStartDate(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndDate(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date addDate(Date date, int day) {
        long millis = date.getTime() + day * 24L * 3600L * 1000L;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public static Date getStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static int weeksOfTwoDates(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int i = 0;
        while (!endDate.before(startDate)) {
            i++;
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            calendar.add(Calendar.WEEK_OF_YEAR, -1);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            endDate = calendar.getTime();
        }
        return i;
    }

    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == 1 ? -6 : 2 - dayOfWeek;
    }

    public static Date getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DAY_OF_MONTH, mondayPlus);
        return currentDate.getTime();
    }

    public static Date getPreviousSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(Calendar.DAY_OF_MONTH, mondayPlus + 6);
        return currentDate.getTime();
    }

    public static Calendar getMinMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, calendar.getActualMinimum(5));
        return calendar;
    }

    public static Calendar getMaxMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    public static Calendar getLastMonday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 1 - dayOfWeek;
        calendar.add(Calendar.DAY_OF_MONTH, offset - 7);
        return calendar;
    }

    public static Calendar getLastSunday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = Calendar.DAY_OF_WEEK - dayOfWeek;
        calendar.add(Calendar.DAY_OF_MONTH, offset - 7);
        return calendar;
    }

    public static Calendar getLastMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    public static Calendar getLastMonthEndDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar;
    }

    public static String getDateStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return date != null ? sdf.format(date) : "";
    }

    public static Date getPreDateStr(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -num);
        Date time = calendar.getTime();
        return time;
    }

    public static Date getMondayStrOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayWeek == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -6);
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -(dayWeek - 2));
        }
        Date time = calendar.getTime();
        return time;
    }

    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = calendar.get(Calendar.DAY_OF_MONTH);
        return now == lastDay;
    }

    public static int daysOfTwo(String startDateStr, String endDateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        return (int) ((endDate.getTime() - startDate.getTime()) / 1000L / 60L / 60L / 24L);
    }

    public static List<String> getContinue12Days() {
        List<String> list = new ArrayList();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -11);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        for (int i = 0; i <= 11; ++i) {
            Date time = calendar.getTime();
            list.add(sdf.format(time));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    public static String getWeeksOfYear(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Date date = sdf.parse(dateStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(Calendar.WEEK_OF_YEAR);
        if (i >= 10) {
            return dateStr.substring(2, 4) + "-" + i;
        } else {
            return dateStr.substring(2, 4) + "-" + 0 + i;
        }
    }

    public static Date getDateByStr(String dateStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(dateStr);
    }

    public static int daysOfTwoDates(Date startDate, Date endDate) {
        return (int) ((endDate.getTime() - startDate.getTime()) / 1000L / 60L / 60L / 24L);
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static final Date getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        return calendar.getTime();
    }

    public static boolean isNow(Date date) {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
        String nowDay = sf.format(now);
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date parse(String str, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(str);
    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static String formatLongToDateStr(Long dateTime, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(dateTime));
    }

}
