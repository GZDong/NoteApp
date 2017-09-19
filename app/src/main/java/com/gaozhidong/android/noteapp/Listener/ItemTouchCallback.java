package com.gaozhidong.android.noteapp.Listener;

import android.app.Application;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.gaozhidong.android.noteapp.Adapter.NoteAdaper;
import com.gaozhidong.android.noteapp.Application.MyApplication;
import com.gaozhidong.android.noteapp.R;

import java.util.Collections;

/**
 * Created by zhidong on 2017/9/19.
 */

public class ItemTouchCallback extends ItemTouchHelper.Callback {
    private NoteAdaper mNoteAdaper;
    public ItemTouchCallback(){
        super();
    }
    public ItemTouchCallback(NoteAdaper noteAdaper){
        mNoteAdaper = noteAdaper;
    }
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags,swipeFlags);
        }
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        int fromPostion = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPostion < toPosition){
            for (int i = fromPostion; i < toPosition; i ++){
                Collections.swap(mNoteAdaper.getDataList(),i,i + 1);
            }
        }else {
            for (int i = fromPostion; i > toPosition; i--){
                Collections.swap(mNoteAdaper.getDataList(),i,i - 1);
            }
        }
        mNoteAdaper.notifyItemMoved(fromPostion,toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();
        mNoteAdaper.notifyItemRemoved(adapterPosition);
        mNoteAdaper.getDataList().remove(adapterPosition);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            viewHolder.itemView.setBackground(MyApplication.getContext().getDrawable(R.drawable.gray));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackground(MyApplication.getContext().getDrawable(R.drawable.white));
    }
}
