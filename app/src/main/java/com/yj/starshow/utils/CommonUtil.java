package com.yj.starshow.utils;

import java.util.Calendar;

public class CommonUtil {
    // 获取今天是星期几
    /**
     * 代表星期几，0=星期日，1=星期一，2=星期二，3=星期三，4=星期四，5=星期五，6=星期六
     * @return
     */
    public static int getNowWeek() {
        Calendar calendar;
        calendar = Calendar.getInstance();
        String week;
        week = calendar.get(calendar.DAY_OF_WEEK) - 1 + "";
        return Integer.parseInt(week);
    }
}
