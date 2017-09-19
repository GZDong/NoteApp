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

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),new ItemTouchHelperGestureListener());
    }

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

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(),e.getY());
            if (childViewUnder != null){
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder);
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
