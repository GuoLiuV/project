package com.glv.music.system.utils;

import com.glv.music.system.enums.TimeUnitEnum;
import com.glv.music.system.modules.exception.StriveException;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHOUXIANG
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat FORMAT_DATE_SHOW = new SimpleDateFormat("yyyy年MM月dd日");

    private static final SimpleDateFormat FORMAT_DATE_TIME_SHOW = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    private static final int DAYS = 0;

    private static final int HOURS = 1;

    private static final int MINUTES = 2;

    private static final int SECONDS = 3;

    /**
     * 日期时间格式化
     */
    public synchronized static String formatDatetime(Date date) {
        if (date == null) {
            return null;
        }
        return FORMAT.format(date);
    }

    /**
     * 日期格式化
     */
    public synchronized static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return FORMAT_DATE.format(date);
    }

    /**
     * 将当前日期转化成可读日期
     */
    public synchronized static String formatDateShow(Date date) {
        if (ObjectUtils.isNull(date)) {
            return "";
        }
        return FORMAT_DATE_SHOW.format(date);
    }

    /**
     * 将当前日期时间 转化成可读日期时间
     */
    public synchronized static String formatDateTimeShow(Date date) {
        if (ObjectUtils.isNull(date)) {
            return "";
        }
        return FORMAT_DATE_TIME_SHOW.format(date);
    }

    /**
     * 显示周几
     *
     * @param date 当前日期
     * @return 周几
     */
    public static String getWeekShow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String week = "";
        switch (day) {
            case Calendar.MONDAY:
                week = "周一";
                break;
            case Calendar.TUESDAY:
                week = "周二";
                break;
            case Calendar.WEDNESDAY:
                week = "周三";
                break;
            case Calendar.THURSDAY:
                week = "周四";
                break;
            case Calendar.FRIDAY:
                week = "周五";
                break;
            case Calendar.SATURDAY:
                week = "周六";
                break;
            case Calendar.SUNDAY:
                week = "周日";
                break;
            default:
        }
        return week;
    }

    /**
     * 解析日期字符串
     */
    public synchronized static Date parseDatetime(String dateStr) {
        Date dateTime = null;
        try {
            dateTime = FORMAT.parse(dateStr);
        } catch (Exception e) {
            log.error("日期转换失败", e);
        }
        return dateTime;
    }

    /**
     * 判断给定两个日期之间的差值（秒，分，时，日，月，年）
     */
    public static Long greaterThan(Date start, Date end, TimeUnitEnum unit) {
        Long count = null;
        switch (unit) {
            case SECOND:
                count = ChronoUnit.SECONDS.between(date2LocalDateTime(start), date2LocalDateTime(end));
                break;
            case MINUTE:
                count = ChronoUnit.MINUTES.between(date2LocalDateTime(start), date2LocalDateTime(end));
                break;
            case HOUR:
                count = ChronoUnit.HOURS.between(date2LocalDateTime(start), date2LocalDateTime(end));
                break;
            case DAY:
                count = ChronoUnit.DAYS.between(date2LocalDate(start), date2LocalDate(end));
                break;
            case MONTH:
                count = ChronoUnit.MONTHS.between(date2LocalDate(start), date2LocalDate(end));
                break;
            case YEAR:
                count = ChronoUnit.YEARS.between(date2LocalDate(start), date2LocalDate(end));
                break;
            default:
                break;
        }
        return count;
    }

    /**
     * 获取今天是今年本月中第几周
     *
     * @return 第几周
     */
    public static int getCurrentWeekOfMonth() {
        // 与mysql保存一致，第1周中有超过3天（不包含）是在今年，才算第1周
        WeekFields weekFields =
                WeekFields.of(DayOfWeek.MONDAY, 1);
        return LocalDate.now().get(weekFields.weekOfMonth());
    }

    /**
     * 获取今天是今年中第几周
     *
     * @return 第几周
     */
    public static int getCurrentWeekOfYear() {
        return getWeekOfYear(new Date());
    }

    /**
     * 获取当前年份
     */
    public static int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 获取当前月份
     */
    public static int getCurrentMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * 获取当前小时
     *
     * @return 几点
     */
    public static int getCurrentHour() {
        return LocalDateTime.now().getHour();
    }

    /**
     * 判断给定日期是否是今天
     *
     * @param date 给定日期
     * @return 是否是今天
     */
    public static boolean isDateToday(Date date) {
        return isSameDay(date, new Date());
    }

    /**
     * 判断日期是否为同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否为同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        String d1 = formatDate(date1);
        String d2 = formatDate(date2);
        return StringUtils.equalsIgnoreCase(d1, d2);
    }

    /**
     * 获取日期信息
     */
    public static Map<TimeUnitEnum, Integer> getInfoFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Map<TimeUnitEnum, Integer> map = new HashMap<>(7);
        map.put(TimeUnitEnum.YEAR, calendar.get(Calendar.YEAR));
        map.put(TimeUnitEnum.MONTH, calendar.get(Calendar.MONTH) + 1);
        map.put(TimeUnitEnum.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        map.put(TimeUnitEnum.HOUR, calendar.get(Calendar.HOUR_OF_DAY));
        map.put(TimeUnitEnum.MINUTE, calendar.get(Calendar.MINUTE));
        map.put(TimeUnitEnum.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK));
        return map;
    }

    /**
     * 从日期字符串中获取日期信息
     */
    public static Map<TimeUnitEnum, Integer> getInfoFromDateString(String str) {
        try {
            Date date = FORMAT_DATE.parse(str);
            return getInfoFromDate(date);
        } catch (ParseException e) {
            throw new StriveException("日期字符串转换失败");
        }
    }

    /**
     * localDate转Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * localDateTime 转 date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取月中的几号
     */
    public static Integer getDayOfMonth(Date date) {
        LocalDate localDate = date2LocalDate(date);
        return localDate.getDayOfMonth();
    }

    /**
     * Date 转 localDate
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    /**
     * date 转 localdatetime
     */
    public static LocalDateTime date2LocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 获取月第一天
     */
    public static Date getStartDayOfMonth(Date date) {
        LocalDate localDate = date2LocalDate(date);
        return getStartDayOfMonth(localDate);
    }

    /**
     * 获取月第一天
     */
    public static Date getStartDayOfMonth(String date) {
        LocalDate now = LocalDate.parse(date);
        return getStartDayOfMonth(now);
    }

    /**
     * 获取月第一天
     */
    public static Date getStartDayOfMonth(LocalDate date) {
        LocalDate now = date.with(TemporalAdjusters.firstDayOfMonth());
        return localDate2Date(now);
    }

    /**
     * 获取当月第一天
     */
    public static Date getStartDayOfMonth() {
        return getStartDayOfMonth(LocalDate.now());
    }


    /**
     * 获取月最后一天
     */
    public static Date getEndDayOfMonth(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getEndDayOfMonth(localDate);
    }

    /**
     * 获取月最后一天
     */
    public static Date getEndDayOfMonth(Date date) {
        return getEndDayOfMonth(date2LocalDate(date));
    }

    /**
     * 获取月最后一天
     */
    public static Date getEndDayOfMonth(LocalDate date) {
        LocalDate now = date.with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(now.atStartOfDay(ZoneId.systemDefault())
                .plusDays(1L).minusNanos(1L).toInstant());
    }

    /**
     * 获取本月最后一天
     */
    public static Date getEndDayOfMonth() {
        return getEndDayOfMonth(LocalDate.now());
    }

    /**
     * 获取上个月第一天日期
     *
     * @param date 当天日期
     * @return 上个月第一天日期
     */
    public static Date getStartDayOfLastMonth(Date date) {
        LocalDate localDate = date2LocalDate(date);
        localDate = localDate.with(TemporalAdjusters.firstDayOfMonth())
                .minus(1, ChronoUnit.MONTHS);
        return localDate2Date(localDate);
    }

    /**
     * 获取上个月最后一天日期
     *
     * @param date 当天日期
     * @return 上个月最后一天日期
     */
    public static Date getEndDayOfLastMonth(Date date) {
        LocalDate localDate = date2LocalDate(date);
        localDate = localDate.with(TemporalAdjusters.lastDayOfMonth())
                .minus(1, ChronoUnit.MONTHS);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
                .plusDays(1L).minusNanos(1L).toInstant());
    }

    /**
     * 获取周第一天
     */
    public static Date getStartDayOfWeek() {
        LocalDate localDate = LocalDate.now();
        return getStartDayOfWeek(localDate);
    }

    /**
     * 获取周第一天
     */
    public static Date getStartDayOfWeek(Date date) {
        LocalDate localDate = date2LocalDate(date);
        return getStartDayOfWeek(localDate);
    }

    /**
     * 获取周第一天
     */
    public static Date getStartDayOfWeek(String date) {
        LocalDate now = LocalDate.parse(date);
        return getStartDayOfWeek(now);
    }

    /**
     * 获取周第一天
     */
    public static Date getStartDayOfWeek(TemporalAccessor date) {
        TemporalField fieldIso = WeekFields.of(
                DayOfWeek.MONDAY, 4).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldIso, 1);
        return localDate2Date(localDate);
    }

    /**
     * 获取周最后一天
     */
    public static Date getEndDayOfWeek() {
        LocalDate localDate = LocalDate.now();
        return getEndDayOfWeek(localDate);
    }

    /**
     * 获取周最后一天
     */
    public static Date getEndDayOfWeek(Date date) {
        LocalDate localDate = date2LocalDate(date);
        return getEndDayOfWeek(localDate);
    }

    /**
     * 获取周最后一天
     */
    public static Date getEndDayOfWeek(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return getEndDayOfWeek(localDate);
    }

    /**
     * 获取周最后一天
     */
    public static Date getEndDayOfWeek(TemporalAccessor date) {
        TemporalField fieldIso = WeekFields.of(
                DayOfWeek.MONDAY, 4).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldIso, 7);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault())
                .plusDays(1L).minusNanos(1L).toInstant());
    }

    /**
     * 获取上周的第一天
     *
     * @param date 当前日期
     * @return 上周第一天日期
     */
    public static Date getStartDayOfLastWeek(Date date) {
        LocalDate localDate = date2LocalDate(date);
        localDate = localDate.minusDays(7);
        return getStartDayOfWeek(localDate);
    }

    /**
     * 获取上周最后一天
     *
     * @param date 当前日期
     * @return 上周最后一天日期
     */
    public static Date getEndDayOfLastWeek(Date date) {
        LocalDate localDate = date2LocalDate(date);
        localDate = localDate.minusDays(7);
        return getEndDayOfWeek(localDate);
    }

    /**
     * 一天的开始
     */
    public static LocalDateTime getStartOfDay(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    /**
     * 一天的开始
     */
    public static LocalDateTime getStartOfDay() {
        return getStartOfDay(LocalDate.now());
    }

    /**
     * 一天的结束
     */
    public static LocalDateTime getEndOfDay(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    /**
     * 一天的结束
     */
    public static LocalDateTime getEndOfDay() {
        return getEndOfDay(LocalDate.now());
    }

    /**
     * 获取当前季度
     *
     * @return 季度
     */
    public static int getCurrentQuarter() {
        LocalDate localDate = LocalDate.now();
        return getQuarterByLocalDate(localDate);
    }

    /**
     * 获取某日期所在季度
     *
     * @return 季度
     */
    public static int getQuarterByLocalDate(LocalDate localDate) {
        int month = localDate.getMonthValue();
        if (month <= 3) {
            return 1;
        } else if (month <= 6) {
            return 2;
        } else if (month <= 9) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * 获取当前周
     *
     * @return 第几周
     */
    public static int getWeekOfYear(Date date) {
        // minimalDaysInFirstWeek == 4 与 mysql 保持一致，超过3天才算一周
        WeekFields weekFields =
                WeekFields.of(DayOfWeek.MONDAY, 4);
        return date2LocalDate(date).get(weekFields.weekOfYear());
    }

}
