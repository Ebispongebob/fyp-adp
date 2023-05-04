package com.fyp.adp.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtils {

    /**
     * 标准格式
     */
    public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准格式到毫秒
     */
    public static final String MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 计算给定的日期加上给定的周数.
     *
     * @param when   给定的日期
     * @param amount 给定的月数
     * @return 计算后的日期
     */
    public static Date addWeeks(Date when, int amount) {
        return add(when, Calendar.WEEK_OF_MONTH, amount);
    }

    /**
     * 计算给定的日期加上给定的月数。.
     *
     * @param when   给定的日期
     * @param amount 给定的月数
     * @return 计算后的日期
     */
    public static Date addMonths(Date when, int amount) {
        return add(when, Calendar.MONTH, amount);
    }

    public static Date addDays(Date when, int amount) {
        return add(when, Calendar.DAY_OF_YEAR, amount);
    }

    public static Date addMins(Date when, int amount) {
        return add(when, Calendar.MINUTE, amount);
    }

    public static Date add(Date when, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(when);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    public static Date getLastMoment(Date when) {
        LocalDate startDate  = when.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Instant   endInstant = LocalDateTime.of(startDate, LocalTime.of(23, 59, 59)).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(endInstant);
    }

    public static Date getFirstMoment(Date when) {
        LocalDate startDate  = when.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Instant   endInstant = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0)).atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(endInstant);
    }

    public static String getStringDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static String getStringDateTime(Date date) {
        return new SimpleDateFormat(FORMAT).format(date);
    }

    public static Date dateString2Date(String dateString) {
        return dateString2Date(dateString, "yyyy-MM-dd");
    }

    public static Date dateTimeString2Date(String dateString) {
        return dateString2Date(dateString, FORMAT);
    }

    /**
     * 根据字符串长度选择格式
     * @param dateTime 字符串
     * @return 日期
     */
    public static Date format(String dateTime) {
        return StringUtils.length(dateTime) > 10 ? dateTimeString2Date(dateTime) : dateString2Date(dateTime);
    }

    public static Date dateString2Date(String dateString, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static LocalDateTime date2LocalDateTime(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        if (null == localDateTime) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Optional<Date> nextOrSameDayOfMonth(Date time, List<Integer> dayOfMonth, Date end) {
        LocalDateTime localDateTime    = date2LocalDateTime(time);
        LocalDateTime endLocalDateTime = date2LocalDateTime(end);
        // 没有指定号数
        if (CollectionUtils.isEmpty(dayOfMonth)) {
            return Optional.empty();
        }
        // 有指定结束日期并且在结束日期之后
        if (null != endLocalDateTime && endLocalDateTime.isBefore(localDateTime)) {
            return Optional.empty();
        }
        // 当前是几号
        int nowDayOfMonth = localDateTime.getDayOfMonth();
        // 当前在指定列表中 就直接返回
        if (dayOfMonth.contains(nowDayOfMonth)) {
            return Optional.of(localDateTime2Date(localDateTime));
        }
        // 当前月有多少天
        int daysOfMonth = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
        for (Integer d : dayOfMonth) {
            // 当月没有指定号数
            if (d > daysOfMonth) {
                continue;
            }
            // 指定号数在之前
            if (nowDayOfMonth > d) {
                continue;
            }
            return Optional.of(localDateTime2Date(localDateTime.withDayOfMonth(d)));
        }
        // 没有满足就去下月找,从1号开始
        return nextOrSameDayOfMonth(localDateTime2Date(localDateTime.plusMonths(1).withDayOfMonth(1)), dayOfMonth, end);
    }

    public static Optional<Date> nextOrSameDayOfWeek(Date time, List<Integer> dayOfWeek) {
        LocalDateTime localDateTime = date2LocalDateTime(time);
        if (CollectionUtils.isEmpty(dayOfWeek)) {
            return Optional.empty();
        }
        int defaultDayOfWeek = localDateTime.getDayOfWeek().getValue();
        for (int i = 0; i < DayOfWeek.values().length; i++) {
            int nowDayOfWeek = (defaultDayOfWeek + i) % DayOfWeek.values().length;
            if (dayOfWeek.contains(nowDayOfWeek)) {
                return Optional.of(localDateTime2Date(localDateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.of(nowDayOfWeek)))));
            }
        }
        return Optional.empty();
    }

    /**
     * 日期转紧凑格式字符串
     *
     * @param date 日期
     * @return yyyyMMddHHmmss
     */
    public static String dateToSSString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(date);
    }

    public static String getDetailStringDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static Long getLongByDate(String date) {
        return Objects.requireNonNull(dateString2Date(date, "yyyy-MM-dd HH:mm:ss")).getTime();
    }

    public static boolean isToday(Date date) {
        return StringUtils.equals(getStringDate(date), getStringDate(new Date()));
    }

    public static boolean isTomorrow(Date date) {
        return isToday(addDays(date, -1));
    }

    /**
     * 获取本周第一天
     *
     * @return
     */
    public static String getThisWeekFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 2);
        Date time = cal.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(time);
    }

    /**
     * 获取本月第一天
     *
     * @return
     */
    public static String getThisMonthFirstDay() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM").format(date) + "-01";
    }

    public static Long getDurationTomorrowDawn() {
        LocalDate     tomorrow  = LocalDate.now().plusDays(1);
        LocalDateTime dateTime  = LocalDateTime.of(tomorrow.getYear(), tomorrow.getMonth(), tomorrow.getDayOfMonth(), 1, 0, 0);
        long          timestamp = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return timestamp - System.currentTimeMillis();
    }

    /**
     * 获取日期偏移
     * @param offset
     * @return 日期
     */
    public static String getDateStr(int offset) {
        return LocalDate.now().plusDays(offset).toString();
    }

    public static String currentDateTime() {
        return format(new Date());
    }

    public static String format(Date date) {
        return formatBy(FORMAT, date);
    }

    public static String formatBy(String format, Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Long getDurationNextMondayDawn() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time       = cal.getTime();
        Date nextMonday = addDays(time, 7);
        System.out.println(nextMonday.getTime());
        return nextMonday.getTime() - System.currentTimeMillis();
    }

    public static String getTodayOClock(int clock) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, clock);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time = cal.getTime();
        return new SimpleDateFormat("HH:mm:ss").format(time);
    }

    /**
     * 获取时分秒字符
     * @param date 日期时间
     * @return 时分秒
     */
    public static String getHms(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * 获取时分字符
     * @param date 日期时间
     * @return 时分
     */
    public static String getHm(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    /**
     * 指定时、分、秒，创建当日的date
     * @param hour 小时
     * @param minutes 分钟
     * @param seconds 秒
     * @return 当日的date
     */
    public static Date getDateByHMS(int hour, int minutes, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, seconds);
        // 毫秒设置为0
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getMonthOffset(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, offset);
        Date time = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM").format(time);
    }

    public static Date getDate(long stamp) {
        return new Date(stamp);
    }

    public static long getStampByYMD(Date date) {
        return getStampByYMD(getYear(date), getMonth(date), getDay(date));
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static long getStampByYMD(int year, int month, int day) {
        return getDateByYMD(year, month, day).getTime();
    }

    public static Date getDateByYMD(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getDateOffset(int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, offset);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static String getCurrentMillisSecond() {
        return formatBy(MILLIS_FORMAT, new Date());
    }

    /**
     * 判断当前是否是上午，是返回true，否则返回false
     * @return 是否是上午
     */
    public static boolean isAm() {
        Calendar calendar = Calendar.getInstance();
        return Calendar.AM == calendar.get(Calendar.AM_PM);
    }

    /**
     * 获取过去一周
     */
    public static List<String> getLastWeekDates() {
        List<String>      lastWeekDates = new ArrayList<>();
        DateTimeFormatter formatter     = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate         today         = LocalDate.now();
        for (int i = 6; i >=0; i--) {
            LocalDate date          = today.minusDays(i);
            String    formattedDate = date.format(formatter);
            lastWeekDates.add(formattedDate);
        }
        return lastWeekDates;
    }

    /**
     * timestamp2String
     */
    public static String timestamp2format(Long timestamp) {
        return new SimpleDateFormat(FORMAT).format(timestamp);
    }
}
