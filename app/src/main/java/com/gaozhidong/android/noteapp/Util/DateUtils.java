package com.gaozhidong.android.noteapp.Util;

import java.util.Calendar;

/**
 * Created by zhidong on 2017/9/20.
 */

public class DateUtils {
    private static String midStr1 =  "";
    private static String midStr2 =  "";
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
        if (minute<10){
            midStr2 = "0";
        }
        if (hour<10){
            midStr1 = "0";
        }
        return year + "年" + month + "月" + day + "日 " +midStr1 + hour + ":"+ midStr2 + minute;
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
        if (minute<10){
            midStr2 = "0";
        }
        if (hour<10){
            midStr1 = "0";
        }
        return year + "年" + month + "月" + day + "日 " +midStr1 + hour + ":"+ midStr2 + minute;
    }
}
