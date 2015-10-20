package com.hunter.fastandroid.utils;

import com.hunter.fastandroid.ui.activity.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 日历控件工具类
 */
public class CalendarUtils {
    private static CalendarUtils mInstance;
    int currentMonth = -1;
    int currentYear = -1;

    private CalendarUtils() {
    }

    public static CalendarUtils getInstance() {
        if (mInstance == null) {
            mInstance = new CalendarUtils();
        }

        return mInstance;
    }

    /**
     * 获取本月的数据
     *
     * @return
     */
    public List<Calendar> getCalendarData() {
        return getCalendarData(0);
    }

    /**
     * 根据偏移量获取指定月份的数据
     *
     * @param offset
     * @return
     */
    public List<Calendar> getCalendarData(int offset) {
        List<Calendar> calendarList = new ArrayList<>();

        // 最小日期为本月第一天
        java.util.Calendar minCalendar = java.util.Calendar.getInstance();
        // 如果当前月份已被赋值,则代表已初始化过,所以要设置为已赋值过的月份
        if (currentMonth != -1 && currentYear != -1) {
            minCalendar.set(java.util.Calendar.YEAR, currentYear);
            minCalendar.set(java.util.Calendar.MONTH, currentMonth);
        }
        minCalendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        // 偏移指定的月份
        minCalendar.add(java.util.Calendar.MONTH, offset);
        // 记录当前年份以及月份
        currentYear = minCalendar.get(java.util.Calendar.YEAR);
        currentMonth = minCalendar.get(java.util.Calendar.MONTH);
        // 最大日期为本月最后一天
        java.util.Calendar maxCalendar = java.util.Calendar.getInstance();
        // 如果当前年份及月份已被赋值,则代表已初始化过,所以要设置为已赋值过的年份和月份
        if (currentMonth != -1 && currentYear != -1) {
            maxCalendar.set(java.util.Calendar.YEAR, currentYear);
            maxCalendar.set(java.util.Calendar.MONTH, currentMonth);
        }
        // 偏移指定的月份
        maxCalendar.add(java.util.Calendar.MONTH, 1);
        maxCalendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        maxCalendar.add(java.util.Calendar.DAY_OF_MONTH, -1);
        // 获取第一天是星期几
        int firstDayOnWeek = minCalendar.get(java.util.Calendar.DAY_OF_WEEK);
        // 获取最后一天是星期几
        int lastDayOnWeek = maxCalendar.get(java.util.Calendar.DAY_OF_WEEK);

        // 从星期天一直遍历到本月第一天,填充空数据
        for (int i = 1; i < firstDayOnWeek; i++) {
            calendarList.add(new Calendar(null, null));
        }

        // 开始从最小日期遍历到最大日期
        while (!minCalendar.after(maxCalendar)) {
            Date date = minCalendar.getTime();
            String price = "";
            Calendar calendar = new Calendar(date, price);
            formatCalendar(calendar);
            calendarList.add(calendar);
            // 往后偏移一天
            minCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
        }

        // 从本月最后一天一直遍历到星期天,填充空数据
        for (int i = lastDayOnWeek; i < 7; i++) {
            calendarList.add(new Calendar(null, null));
        }

        return calendarList;
    }

    /**
     * 格式化日历(节假日、休息日等)
     *
     * @param calendar
     * @return
     */
    private void formatCalendar(Calendar calendar) {
        Lunar lunar = new Lunar(calendar.date);
        calendar.lunar = lunar.toString();

        // 判断该日期是否是今天之前,如果是则是仅展示不可用状态
        java.util.Calendar theCalendar = java.util.Calendar.getInstance();
        theCalendar.setTime(calendar.date);
        java.util.Calendar today = java.util.Calendar.getInstance();
        calendar.isUse = !theCalendar.before(today);

        String monthDay = CommonUtils.formatMonthDay.format(calendar.date);

        Map<String, String> holidayMap = new HashMap<>();
        holidayMap.put("1-1", "元旦");
        holidayMap.put("9-3", "胜利日");

        if (holidayMap.containsKey(monthDay)) {
            calendar.holiday = holidayMap.get(monthDay);
        }

        Set<String> restSet = new HashSet<>();
        restSet.add("9-4");
        restSet.add("9-5");

        if (restSet.contains(monthDay)) {
            calendar.rest = true;
        }
    }

    public String getYearAndMonth() {
        return String.valueOf(currentYear + "-" + (currentMonth + 1));
    }
}
