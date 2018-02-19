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

import com.bumptech.glide.Glide;
import com.gaozhidong.android.noteapp.ContentActivity;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.MyView.MarqueeTextView;
import com.gaozhidong.android.noteapp.MyView.NineGridImageView;
import com.gaozhidong.android.noteapp.NoteListActivity;
import com.gaozhidong.android.noteapp.PicShowActivity;
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
    public void onBindViewHolder(NoteViewHodler viewHodler,final int position) {
        NoteBody noteBody = mList.get(position);
      //  LogUtil.e("test","postion : " + position);
        if (noteBody.getFlag() == 1){
            viewHodler.tagImgView.setVisibility(View.VISIBLE);
        }else {
            viewHodler.tagImgView.setVisibility(View.GONE );
        }
        viewHodler.textBody.setText(noteBody.getText());
        viewHodler.timeBody.setText(noteBody.getTime());
        viewHodler.setNoteId(noteBody.getNoteId());

        List<String> mPathList = NotesLab.get(mContext).getPicPaths(mList.get(position).getNoteId());
        NineGridImageViewAdapter<String> adapter = new NineGridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String s) {
                Glide.with(mContext).load(s).asBitmap().centerCrop().into(imageView);
            }

            @Override
            public ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }
        };
        viewHodler.mNineGridImageView.setAdapter(adapter);
        viewHodler.mNineGridImageView.setImagesData(mPathList);
        viewHodler.mNineGridImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PicShowActivity.class);
                intent.putExtra("position",0);
                intent.putExtra("NoteId",mList.get(position).getNoteId());
                mContext.startActivity(intent);
            }
        });
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
        public int noteId;
        private NineGridImageView mNineGridImageView;

        public NoteViewHodler(View view){
            super(view);
            textBody = (TextView) view.findViewById(R.id.text_body);
            timeBody = (TextView) view.findViewById(R.id.text_time);
            tagImgView = (ImageView) view.findViewById(R.id.ring_img);
            mNineGridImageView = (NineGridImageView) view.findViewById(R.id.pics_iv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = ContentActivity.newInstance(mContext);
                    // LogUtil.e("test","put " + noteId);
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
