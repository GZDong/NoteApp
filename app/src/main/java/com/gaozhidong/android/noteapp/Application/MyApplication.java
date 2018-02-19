package com.gaozhidong.android.noteapp.Application;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

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
    }
    public static Context getContext(){
        return sContext;
    }
}
