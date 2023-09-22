package com.paopao.cache.util;


/**
 * 时间工具类
 *
 * @author loser
 * @date 2023/06/20
 */
public class DateConstant {

    /**
     * 仅显示年月日，例如 2015-08-11.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 显示年月日时分秒，例如 2015-08-11 09:51:53.
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 仅显示时分秒，例如 09:51:53.
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 每天的毫秒数 8640000.
     */
    public static final long MILLISECONDS_PER_DAY = 86400000L;

    /**
     * 每周的天数.
     */
    public static final long DAYS_PER_WEEK = 7L;

    /**
     * 每小时毫秒数.
     */
    public static final long MILLISECONDS_PER_HOUR = 3600000L;

    /**
     * 每分钟秒数.
     */
    public static final long SECONDS_PER_MINUTE = 60L;

    /**
     * 每小时秒数.
     */
    public static final long SECONDS_PER_HOUR = 3600L;

    /**
     * 每天秒数.
     */
    public static final long SECONDS_PER_DAY = 86400L;

    /**
     * 每周秒数.
     */
    public static final long SECONDS_PER_WEEK = 7 * 86400L;

    /**
     * 每个月秒数，默认每月30天.
     */
    public static final long SECONDS_PER_MONTH = 2592000L;

    /**
     * 每年秒数，默认每年365天.
     */
    public static final long SECONDS_PER_YEAR = 31536000L;

}
