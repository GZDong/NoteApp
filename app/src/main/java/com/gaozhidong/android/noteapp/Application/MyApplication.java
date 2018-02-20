package com.gaozhidong.android.noteapp.Application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.StrictMode;

import com.gaozhidong.android.noteapp.Util.LogUtil;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.Calendar;

/**
 * Created by zhidong on 2017/9/19.
 */

public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        LitePal.initialize(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.gaozhidong.android.RING");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

        registerReceiver(receiver,intentFilter);
    }
    public static Context getContext(){
        return sContext;
    }
}
