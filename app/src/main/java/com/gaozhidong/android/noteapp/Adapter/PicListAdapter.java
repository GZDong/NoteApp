package com.gaozhidong.android.noteapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gaozhidong.android.noteapp.R;

import java.util.List;

/**
 * Created by zhidong on 2018/2/19.
 */

public class PicListAdapter extends RecyclerView.Adapter<PicListAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mPicList;

    public PicListAdapter(Context context,List<String> list){
        mContext = context;
        mPicList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_view_picture,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String path = mPicList.get(position);
        if (!TextUtils.isEmpty(path)){
            Glide.with(mContext).load(path).asBitmap().centerCrop().into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mPicList.size();
    }

    public void updateList(List<String> list){
        mPicList = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public MyViewHolder(View view){
            super(view);
            mImageView = view.findViewById(R.id.picture_took);
        }
    }
}
