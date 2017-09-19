package com.gaozhidong.android.noteapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


/**
 * Created by zhidong on 2017/9/18.
 */

public class AlarmDialogFragment extends DialogFragment {
    private TextView mYMDText;
    private TextView mHMText;
    private Button mModifyYMDBtn;
    private Button mModifyHMBtn;

    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;

    //private AlarmManager mAlarmManager;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog,null,false);
        mYMDText = (TextView) view.findViewById(R.id.YMDText);
        mHMText = (TextView) view.findViewById(R.id.HMText);
        mModifyYMDBtn = (Button) view.findViewById(R.id.modifyYMD);
        mModifyHMBtn = (Button) view.findViewById(R.id.modifyHM);

        //mAlarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        mYMDText.setText(year + "-" + month + "-" + day);
        mHMText.setText(hour + " : " + minute);

        mModifyYMDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int chooseY, int chooseM, int chooseD) {
                        //设置年月日
                        Calendar calout = Calendar.getInstance();
                        calout.set(Calendar.YEAR,chooseY);
                        calout.set(Calendar.MONTH,chooseM);
                        calout.set(Calendar.DAY_OF_MONTH,chooseD);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        mModifyHMBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int chooseH, int chooseM) {
                        //这里的chooseH和chooseM就是选中的小时和分钟
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY,chooseH);
                        cal.set(Calendar.MINUTE,chooseM);
                        //选择指定时间后开启服务，让服务来设定闹钟，让闹钟到时发送广播
                        Intent intent = new Intent(getActivity(),AlarmService.class);
                        intent.putExtra("calendar",cal);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startService(intent);

                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setTitle("选择时间")
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}
