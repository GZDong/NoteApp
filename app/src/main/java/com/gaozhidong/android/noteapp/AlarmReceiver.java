package com.gaozhidong.android.noteapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by zhidong on 2017/9/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "test";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.gaozhidong.android.RING")){
            Log.e(TAG, "onReceive: !!!!!!");
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
}
