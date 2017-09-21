package com.gaozhidong.android.noteapp.Listener;

import android.app.Application;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.gaozhidong.android.noteapp.Adapter.NoteAdaper;
import com.gaozhidong.android.noteapp.Application.MyApplication;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.R;
import com.gaozhidong.android.noteapp.Util.LogUtil;

import java.util.Collections;

/**
 * Created by zhidong on 2017/9/19.
 */

public class ItemTouchCallback extends ItemTouchHelper.Callback {
    private NoteAdaper mNoteAdaper;
    public ItemTouchCallback(){
        super();
    }

    /**
     * 通过使用RecyclerView的适配器来操作RecyclerView
     * @param noteAdaper
     */
    public ItemTouchCallback(NoteAdaper noteAdaper){
        mNoteAdaper = noteAdaper;
    }

    /**
     * 这个方法用于决定哪些方向的滑动是允许的，或者说是可以引起响应的
     * @param recyclerView
     * @param viewHolder
     * @return  int 返回一个整型，标志是否允许滑动
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //如果是线性的，设置拖拉方向为上和下，侧滑为左和右
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager){
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags,swipeFlags);
        }else{  //如果是表格性的，设置上下左右四个方向
            if (recyclerView.getLayoutManager()instanceof GridLayoutManager){
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                        |ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                int swipeFlags = 0;  //0代表不滑动
                return makeMovementFlags(dragFlags,swipeFlags);
            }
        }
        return 0;
    }

    /**
     * 这个方法响应拖动事件
     * @param recyclerView
     * @param viewHolder  被拖动的viewHolder
     * @param target   位置被代替的viewHolder
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        int fromPostion = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        //如果子项是向下托拉，从拖拉位置开始的地方到目标位置的前一个位置，全部子项向后一个子项交换数据
        if (fromPostion < toPosition){
            for (int i = fromPostion; i < toPosition; i ++){
                Collections.swap(mNoteAdaper.getDataList(),i,i + 1);
            }
        }else { //如果子项是向上拖拉，从拖拉位置开始到目标位置的下一个位置，全部子项向前一个子项交换数据
            for (int i = fromPostion; i > toPosition; i--){
                Collections.swap(mNoteAdaper.getDataList(),i,i - 1);
            }
        }
        //上一步交换完数据，这一步交换视图
        mNoteAdaper.notifyItemMoved(fromPostion,toPosition);
        return true;
    }

    /**
     * 这个方法响应侧滑
     * @param viewHolder  被滑动的子项
     * @param direction  滑动方向，可以根据不同的方向做不同的事情
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int adapterPosition = viewHolder.getAdapterPosition();  //获得被侧滑的子项位置
       // NoteBody noteBody = mNoteAdaper.getDataList().get(adapterPosition);
       // LogUtil.e("test","被移除的noteId" + noteBody.getNoteId());
        mNoteAdaper.notifyItemRemoved(adapterPosition);  //视图清除
        mNoteAdaper.getDataList().remove(adapterPosition);  //数据清除
        NotesLab.get(MyApplication.getContext()).printList();
    }

    /**
     * 响应选中
     * @param viewHolder  被选中的子项
     * @param actionState  子项的选中状态，可以根据子项的选中状态做不同的处理
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){  //ItemTouchHelper.ACTION_STATE_IDLE 空闲状态
            viewHolder.itemView.setBackground(MyApplication.getContext().getDrawable(R.drawable.gray));
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 这个方法用于当拖动、侧滑、选中等等动作消失后，需要做的处理
     * @param recyclerView
     * @param viewHolder  被操作的子项
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackground(MyApplication.getContext().getDrawable(R.drawable.white));
    }
}
