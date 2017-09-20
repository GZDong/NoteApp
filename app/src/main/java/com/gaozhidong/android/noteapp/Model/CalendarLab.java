package com.gaozhidong.android.noteapp.Model;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by zhidong on 2017/9/19.
 */

public class CalendarLab {
    private static CalendarLab sCalendarLab;
    private static Context sContext;
    private Calendar mCalendar;
    private CalendarLab(Context context){
        sContext = context;
        mCalendar = Calendar.getInstance();//创建map，根据对应的便签id获得对象
    }
    public static CalendarLab get(Context context){
        if (sCalendarLab == null){
            synchronized (CalendarLab.class){
                if (sCalendarLab == null){
                    sCalendarLab = new CalendarLab(context);
                }
            }
        }
        return  sCalendarLab;
    }
    public void setCalendarYMD(int year,int month,int day){
        mCalendar.set(Calendar.YEAR,year);
        mCalendar.set(Calendar.MONTH,month);
        mCalendar.set(Calendar.DAY_OF_MONTH,day);
    }
    public void setCalendarHM(int hour, int minute){
        mCalendar.set(Calendar.HOUR_OF_DAY,hour);
        mCalendar.set(Calendar.MINUTE,minute);
    }
    public Calendar getCalendar(){
        return mCalendar;
    }
}
