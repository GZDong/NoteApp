package com.gaozhidong.android.noteapp.Listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by zhidong on 2017/9/19.
 */

public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{

    private RecyclerView mRecyclerView;
    private GestureDetectorCompat mGestureDetectorCompat;

    /**
     * 在创建实例的时候，传进指定的RecyclerView实例
     * 同时使用实现了GestureDetector.SimpleOnGestureListener接口的类实例来创建GestureDetector
     * @param recyclerView
     */
    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

    /**
     * 重写下面三个方法，让GestureDetector接收事件
     * @param rv
     * @param e
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    /**
     * 需要调用者，也就是RecyclerView实例实现的抽象方法
     * @param viewHolder
     */
    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);

    /**
     * 实现一个GestureDetector，重写关于单点和长按方法
     */
    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //通过点击事件的坐标，用findChileViewUnder找到被点击的view
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if (childViewUnder != null){
                //通过被点击的view，用getChildViewHolder找到被点击的viewHolder
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder); //回调
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View chileViewUnder = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if (chileViewUnder != null){
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(chileViewUnder);
                onLongClick(childViewHolder);
            }
        }
    }
}
