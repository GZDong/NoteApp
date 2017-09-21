package com.gaozhidong.android.noteapp;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class AlarmService extends Service {

    private static final String TAG = "test";
    private Calendar mCalendar;
    private AlarmManager mAlarmManager;

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: 服务第一次开启");
       /* IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gaozhidong.android.RING");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.gaozhidong.android.RING")){
                    Log.e(TAG, "onReceive: !!!!!!" + System.currentTimeMillis());
                    Intent intent2 = new Intent(context,ContentActivity.class);
                    PendingIntent pi = PendingIntent.getActivity(context,0,intent2,0);
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                            .setContentTitle("xxx")
                            .setContentText("闹钟响了")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                            .setDefaults(NotificationCompat.DEFAULT_ALL)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setAutoCancel(true)
                            .setContentIntent(pi);
                    Notification notification = builder.build();
                    manager.notify(1,notification);
                }
            }
        };
        registerReceiver(receiver,intentFilter);*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: 服务启动");
        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mCalendar = (Calendar) intent.getSerializableExtra("calendar");
        int noteId = intent.getIntExtra("noteId", 0);
        Intent intent2 = new Intent();
        intent2.setAction("com.gaozhidong.android.RING");
        intent2.putExtra("noteId", noteId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent2, 0);

       /* Intent intent3 = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this,0,intent3,0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setExact(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(),pendingIntent1);*/

       //根据不同的版本使用不同的设置方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
        } else {
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pendingIntent);
        }

        Log.e(TAG, "onStartCommand: 设置闹钟");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: 服务被杀死");
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
