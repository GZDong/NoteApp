package com.gaozhidong.android.noteapp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.Model.Pictures;
import com.gaozhidong.android.noteapp.PicturesFragment;

import java.util.List;

/**
 * Created by zhidong on 2018/2/19.
 */

public class MyFragmentStateAdapter extends FragmentStatePagerAdapter {
    List<Pictures> mList;
    private Context mContext;


    public MyFragmentStateAdapter(FragmentManager fm, Context context){
        super(fm);
        mContext = context;
        this.mList = NotesLab.get(mContext).getPics(1);
    }

    public MyFragmentStateAdapter(FragmentManager fm,Context context,int id){
        super(fm);
        mContext = context;
        this.mList = NotesLab.get(mContext).getPics(id);
    }

    //返回需要展示的fragment


    @Override
    public Fragment getItem(int position) {
        return PicturesFragment.newInstance(mList.get(position));
    }

    //返回fragment总量

    @Override
    public int getCount() {
        return mList.size();
    }
}
