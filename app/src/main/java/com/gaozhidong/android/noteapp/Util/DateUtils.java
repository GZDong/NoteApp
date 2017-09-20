package com.gaozhidong.android.noteapp.Util;

import java.util.Calendar;

/**
 * Created by zhidong on 2017/9/20.
 */

public class DateUtils {
    public static String getStrDate(Calendar calendar){
        int year;
        int month;
        int day;
        int hour;
        int minute;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        return year + "年" + month + "月" + day + "日 " + hour + ":" + minute;
    }
    public static String getNowStrDate(){
        Calendar calendar = Calendar.getInstance();
        int year;
        int month;
        int day;
        int hour;
        int minute;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        return year + "年" + month + "月" + day + "日 " + hour + ":" + minute;
    }
}
