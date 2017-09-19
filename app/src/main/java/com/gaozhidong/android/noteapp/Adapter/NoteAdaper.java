package com.gaozhidong.android.noteapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.R;

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
        viewHodler.textBody.setText(noteBody.getText());
        viewHodler.timeBody.setText(noteBody.getTime());
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

        public NoteViewHodler(View view){
            super(view);
            textBody = (TextView) view.findViewById(R.id.text_body);
            timeBody = (TextView) view.findViewById(R.id.text_time);
        }
    }
}
