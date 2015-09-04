package net.datafans.android.common.helper;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhonganyun on 15/8/6.
 */

@SuppressLint("SimpleDateFormat")
public class TimeHelper {

    private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("MM-dd HH:mm");
    private static SimpleDateFormat lastDayFormat = new SimpleDateFormat("昨天 HH:mm");
    private static SimpleDateFormat monthDayFormat = new SimpleDateFormat("M月d日");


    public static String getMonthDayFormat(long time) {
        return monthDayFormat.format(new Date(time));
    }

    public static String prettyTime(long targetTime) {

        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(now));
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        System.err.println(nowYear + " " + nowMonth + "  " + nowDay);

        Date targetDate = new Date(targetTime);
        calendar.setTime(targetDate);
        int targetYear = calendar.get(Calendar.YEAR);
        int targetMonth = calendar.get(Calendar.MONTH) + 1;
        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
        System.err.println(targetYear + " " + targetMonth + "  " + targetDay);

        if (nowYear == targetYear) {

            if (nowMonth == targetMonth) {

                if (nowDay == targetDay) {

                    long interval = now - targetTime;
                    System.err.println(interval);
                    if (interval < 60 * 1000) {
                        return "刚刚";
                    } else if (interval < 60 * 60 * 1000) {
                        return interval / 60 / 1000 + "分钟前";
                    } else {
                        return interval / 60 / 60 / 1000 + "小时前";
                    }

                } else if (nowDay - targetDay == 1) {
                    return lastDayFormat.format(targetDate);
                } else {
                    return monthFormat.format(targetDate);
                }

            } else {
                return monthFormat.format(targetDate);
            }
        } else {

            return yearFormat.format(targetDate);
        }
    }
}
