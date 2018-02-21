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

import com.gaozhidong.android.noteapp.Model.CalendarLab;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.Util.LogUtil;

import java.util.Calendar;

/**
 * Created by zhidong on 2017/9/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    public static final String TAG = "test";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.gaozhidong.android.RING")){
            LogUtil.e("test", "onReceive: !!!!!!" );

            int noteId = intent.getIntExtra("noteId",1);
            LogUtil.e("test","在接收器接收到的id为 "+noteId);

            Intent intent2 = new Intent(context,ContentActivity.class);
            intent2.putExtra("noteId",noteId);

            NoteBody noteBody = NotesLab.get(context).queryNote(noteId);
            //发送通知
            Log.e(TAG, "onReceive: " + noteBody.getNoteId() + noteBody.getText());
            PendingIntent pi = PendingIntent.getActivity(context,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(noteBody.getTime())
                    .setContentText(noteBody.getText())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setAutoCancel(true)
                    .setContentIntent(pi);
            Notification notification = builder.build();
            manager.notify(1,notification);

            //发送一条清空闹铃图标的广播
            NotesLab.get(context).updateFlag(noteId,0);
            Intent intent1 = new Intent("com.gaozhidong.android.NoColor");
            intent1.putExtra("noteId",noteId);
            context.sendBroadcast(intent1);
        }
    }
}
