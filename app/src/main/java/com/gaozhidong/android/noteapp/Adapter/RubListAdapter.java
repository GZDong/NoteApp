package com.gaozhidong.android.noteapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaozhidong.android.noteapp.Model.NoteBody;
import com.gaozhidong.android.noteapp.Model.NotesLab;
import com.gaozhidong.android.noteapp.MyView.NineGridImageView;
import com.gaozhidong.android.noteapp.R;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * Created by zhidong on 2018/2/25.
 */

public class RubListAdapter extends RecyclerView.Adapter<RubListAdapter.MyViewHolder> {
    private List<NoteBody> mList;
    private Context mContext;
    public RubListAdapter(Context context, List<NoteBody> list){
        mContext = context;
        mList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHodler, int position) {
        NoteBody noteBody = mList.get(position);
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

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textBody;
        private TextView timeBody;
        private ImageView tagImgView;
        public int noteId;
        private NineGridImageView mNineGridImageView;
        public MyViewHolder(View view){
            super(view);
            textBody = (TextView) view.findViewById(R.id.text_body);
            timeBody = (TextView) view.findViewById(R.id.text_time);
            tagImgView = (ImageView) view.findViewById(R.id.ring_img);
            mNineGridImageView = (NineGridImageView) view.findViewById(R.id.pics_iv);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("请选择操作")
                            .setPositiveButton("恢复便签", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NoteBody body = NotesLab.get(mContext).getNoteFromRList(noteId);
                                    NotesLab.get(mContext).addNote(body);
                                    notifyDataSetChanged();
                                }
                            }).setNegativeButton("彻底删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NotesLab.get(mContext).deleteRList(noteId);
                            notifyDataSetChanged();
                        }
                    }).setCancelable(true).show();
                }
            });
        }

        public void setNoteId(int noteId) {
            this.noteId = noteId;
        }
    }
}
