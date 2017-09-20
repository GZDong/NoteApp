package com.gaozhidong.android.noteapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaozhidong.android.noteapp.ContentActivity;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.MyView.MarqueeTextView;
import com.gaozhidong.android.noteapp.NoteListActivity;
import com.gaozhidong.android.noteapp.R;
import com.gaozhidong.android.noteapp.Util.LogUtil;

import java.util.List;

/**
 * Created by zhidong on 2017/9/19.
 */

public class NoteAdaper extends RecyclerView.Adapter<NoteAdaper.NoteViewHodler> {

    private Context mContext;
    public List<NoteBody> mList;

    public NoteAdaper(Context context, List<NoteBody> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public NoteViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note,parent,false);
        return new NoteViewHodler(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHodler viewHodler, int position) {
        NoteBody noteBody = mList.get(position);
        if (noteBody.getFlag() == 1){
            viewHodler.tagImgView.setVisibility(View.VISIBLE);
        }else {
            viewHodler.tagImgView.setVisibility(View.GONE );
        }
        viewHodler.textBody.setText(noteBody.getText());
        viewHodler.timeBody.setText(noteBody.getTime());
        viewHodler.setNoteId(noteBody.getNoteId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(List<NoteBody> list){
        this.mList = list;
        notifyDataSetChanged();
    }
    public List<NoteBody> getDataList(){
        return mList;
    }

    public class NoteViewHodler extends RecyclerView.ViewHolder{
        private TextView textBody;
        private TextView timeBody;
        private ImageView tagImgView;
        private int noteId;

        public NoteViewHodler(View view){
            super(view);
            textBody = (TextView) view.findViewById(R.id.text_body);
            timeBody = (TextView) view.findViewById(R.id.text_time);
            tagImgView = (ImageView) view.findViewById(R.id.ring_img);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ContentActivity.newInstance(mContext);
                    LogUtil.e("test","put " + noteId);
                    intent.putExtra("noteId",noteId);
                    NoteListActivity noteListActivity = (NoteListActivity) mContext;
                    noteListActivity.startActivityForResult(intent,1);
                }
            });
        }
        public void setNoteId(int id) {
            noteId = id;
        }

        public int getNoteId() {
            return noteId;
        }
    }
}
