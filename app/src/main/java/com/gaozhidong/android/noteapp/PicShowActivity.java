package com.gaozhidong.android.noteapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gaozhidong.android.noteapp.Adapter.MyFragmentStateAdapter;

public class PicShowActivity extends AppCompatActivity {

    protected boolean useThemestatusBarColor = false;//是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值

    private MyFragmentStateAdapter mAdapter;

    private ViewPager mPager;

    public static Intent newInstance(Context context, int position){
        Intent intent = new Intent(context, PicShowActivity.class);
        intent.putExtra("position",position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pic_show);
        setStatusBar();
        initViews();
    }

    private void initViews(){
        mPager = (ViewPager) findViewById(R.id.ivew_pager);
        int noteId = getIntent().getIntExtra("NoteId",1);
        if (noteId == 0){
            mAdapter = new MyFragmentStateAdapter(getSupportFragmentManager(),this);
        }else {
            mAdapter = new MyFragmentStateAdapter(getSupportFragmentManager(),this,noteId);
        }
        mPager.setAdapter(mAdapter);
        int position = getIntent().getIntExtra("position",1);
        mPager.setCurrentItem(position);
    }
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.black));
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
