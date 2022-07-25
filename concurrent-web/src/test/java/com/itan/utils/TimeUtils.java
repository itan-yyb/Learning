package com.itan.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/*****************
 @author TianYu
 @date 2019/6/18  
 ******************/
@Slf4j
public class TimeUtils {
    
    /**
     * SimpleDateFormat  线程不安全
     */
    public static final String SHORTSDF = "yyyy-MM-dd";
    public static final String LONGSDF = "yyyy-MM-dd HH:mm:ss";
    public static final String FOREVERDATE = "9999-12-31 23:59:59";
    public static LocalDateTime DateToLDT(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Date LDTtoDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取当前时间离一天结束剩余秒数
     *
     * @return
     */
    public static long getRemainSecondsCurrentDay() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    /**
     * 获取当前时间离本周的结束剩余秒数
     *
     * @return
     */
    public static long getRemainSecondsCurrentWeek() {
        LocalDateTime midnight = LocalDateTime.now().plusWeeks(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }


    /**
     * 获取当前时间离本月的结束剩余秒数
     *
     * @return
     */
    public static long getRemainSecondsCurrentMonth() {
        LocalDateTime midnight = LocalDateTime.now().plusMonths(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }


    /**
     * 获取当前时间离本年的结束剩余秒数
     *
     * @return
     */
    public static long getRemainSecondsCurrentYear() {
        LocalDateTime midnight = LocalDateTime.now().plusYears(1).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }


    /**
     * 获取当前时间离本周期内的结束剩余秒数
     *
     * @return
     */
    public static long getRemainTime(TemporalUnit temporalUnit, ChronoUnit chronoUnit) {
        LocalDateTime midnight = LocalDateTime.now().plus(1, temporalUnit).withHour(0).withMinute(0)
                .withSecond(0).withNano(0);
        return betweenTwoTime(LocalDateTime.now(), midnight, chronoUnit);
    }


    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public static LocalDateTime getCurrentWeekDayStartTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(new SimpleDateFormat(LONGSDF).parse(new SimpleDateFormat(SHORTSDF).format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
        	log.error(e.getLocalizedMessage());
        }
        return DateToLDT(c.getTime());
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public static LocalDateTime getCurrentWeekDayEndTime() {
        Calendar c = Calendar.getInstance();
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(new SimpleDateFormat(LONGSDF).parse(new SimpleDateFormat(SHORTSDF).format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
        	log.error(e.getLocalizedMessage());
        }
        return DateToLDT(c.getTime());
    }


    /**
     * 获得本月的开始时间
     *
     * @return
     */
    public static LocalDateTime getCurrentMonthStartTime() {
    	SimpleDateFormat shortSdf = new SimpleDateFormat(SHORTSDF);
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
        	log.error(e.getLocalizedMessage());
        }
        if (now != null) {
        	return DateToLDT(now);
        }
        return null;
    }

    /**
     * 本月的结束时间
     *
     * @return
     */
    public static LocalDateTime getCurrentMonthEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = new SimpleDateFormat(LONGSDF).parse(new SimpleDateFormat(SHORTSDF).format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
        	log.error(e.getLocalizedMessage());
        }
        if (now != null) {
        	return DateToLDT(now);
        }
        return null;
    }

    /**
     * 当前年的开始时间
     *
     * @return
     */
    public static LocalDateTime getCurrentYearStartTime() {
    	SimpleDateFormat shortSdf = new SimpleDateFormat(SHORTSDF);
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.DATE, 1);
            now = shortSdf.parse(shortSdf.format(c.getTime()));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        if (now != null) {
        	return DateToLDT(now);
        }
        return null;
    }

    /**
     * 当前年的结束时间
     *
     * @return
     */
    public static LocalDateTime getCurrentYearEndTime() {
        Calendar c = Calendar.getInstance();
        Date now = null;
        try {
            c.set(Calendar.MONTH, 11);
            c.set(Calendar.DATE, 31);
            now = new SimpleDateFormat(LONGSDF).parse(new SimpleDateFormat(SHORTSDF).format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
        if (now != null) {
        	return DateToLDT(now);
        }
        return null;
    }

    /**
     * 获取localDateTime当天的开始时间
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getDayStart(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
    }


    /**
     * 获取localDateTime当天的结束时间
     *
     * @param localDateTime
     * @return
     */
    public static LocalDateTime getDayEnd(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
    }

    /**
     * 获取今天是周几
     *
     * @return from 1 (Monday) to 7 (Sunday)
     */
    public static int getDayOfWeek() {
        return LocalDateTime.now().getDayOfWeek().getValue();
    }

    /**
     * 是当前天
     *
     * @param localDateTime
     * @return
     */
    public static boolean isCurrentDay(LocalDateTime localDateTime) {
        return Period.between(localDateTime.toLocalDate(), LocalDateTime.now().toLocalDate()).getDays() == 0;
    }

    /**
     * 获取两个日期的差  field参数为ChronoUnit.*
     *
     * @param startTime
     * @param endTime
     * @param field
     * @return field  单位(年月日时分秒)
     */
    public static long betweenTwoTime(LocalDateTime startTime, LocalDateTime endTime, ChronoUnit field) {
        Period period = Period.between(LocalDate.from(startTime), LocalDate.from(endTime));
        if (field == ChronoUnit.YEARS) {
            return period.getYears();
        }
        if (field == ChronoUnit.MONTHS) {
            return period.getYears() * 12L + period.getMonths();
        }
        return field.between(startTime, endTime);
    }

    /**
     * 获取对应时间时间后{number}{temporalUnit}后的时间差
     *
     * @param startTime
     * @param number
     * @param temporalUnit
     * @param chronoUnit
     * @return
     */
    public static long timePlusBetween(LocalDateTime startTime, long number, TemporalUnit temporalUnit,
                                       ChronoUnit chronoUnit) {
        LocalDateTime endDateTime = startTime.plus(number, temporalUnit);
        return betweenTwoTime(startTime, endDateTime, chronoUnit);
    }

    /**
     * @param startTime
     * @param number
     * @param temporalUnit
     * @return
     */
    public static LocalDateTime timeMinus(LocalDateTime startTime, long number, TemporalUnit temporalUnit) {
        return startTime.minus(number, temporalUnit);
    }

    /**
     * 获取对应时间的秒数
     *
     * @param localDateTime
     * @return
     */
    public static long getSeconds(LocalDateTime localDateTime) {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }


    /**
     * 格式化获取系统当前日期(精确到天)，格式：yyyy-MM-dd
     *
     * @return
     */
    public static String getDateFormatToDay(Date date) {
        return new SimpleDateFormat(LONGSDF).format(date);
    }

}
