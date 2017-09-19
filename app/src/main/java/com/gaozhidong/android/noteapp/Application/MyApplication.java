package com.gaozhidong.android.noteapp.Application;

import android.app.Application;
import android.content.Context;

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
        LitePalApplication.initialize(sContext);
    }
    public static Context getContext(){
        return sContext;
    }
}
